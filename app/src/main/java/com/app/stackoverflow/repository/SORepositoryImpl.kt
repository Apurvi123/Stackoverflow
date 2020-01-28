package com.app.stackoverflow.repository

import android.util.Log
import com.app.stackoverflow.repository.localsource.SOLocalDataSource
import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity
import com.app.stackoverflow.repository.remotesource.SORemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

/**
 * This class is responsible for communicating with both remote and local data sources.
 * It requests the api for new data. Once new data is received, the repo should update the local source,
 * keeping our local persistence as single source of truth.
 */
class SORepositoryImpl internal constructor(
    private val soRemoteDataSource: SORemoteDataSource,
    private val soLocalDataSource: SOLocalDataSource
) : SORepository {

    private val allQuestions = mutableListOf<QuestionEntity>()
    private val allAnswersForQuestion = mutableMapOf<Long, List<AnswerEntity>>()
    private val guessedQuestions = mutableListOf<QuestionEntity>()
    private val lock = Any()

    override fun fetchQuestions(exceptionHandler: (Exception) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = soRemoteDataSource.getQuestions().await()
                if (!response.isSuccessful) {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    exceptionHandler(Exception("SO API request failed with - ${errorBody.getString("message")}"))
                }

                response.body()?.run {
                    items.let {
                        Log.d(TAG, "SO API network request is success = ${response.code()}")
                        soLocalDataSource.insertQuestions(it)
                        // Fetch the questions and create a local copy in memory
                        val questions = soLocalDataSource.getQuestions()
                        synchronized(lock) {
                            allQuestions.addAll(questions)
                        }
                    }
                }

            } catch (exception: Exception) {
                // Possible exceptions like certification path not found, CertPathValidatorException
                exceptionHandler(Exception("Couldn't process the AB API request - ${exception.message}"))
            }
        }
    }

    override fun getAllQuestions(): List<QuestionEntity> = allQuestions.toList()

    override fun fetchAnswersForQuestion(
        questionId: Long,
        exceptionHandler: (Exception) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = soRemoteDataSource.getAnswersForQuestion(questionId).await()
                if (!response.isSuccessful) {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    exceptionHandler(Exception("SO API request failed with - ${errorBody.getString("message")}"))
                }

                response.body()?.run {
                    items.let {
                        Log.d(TAG, "SO API network request is success = ${response.code()}")
                        soLocalDataSource.insertAnswers(it)
                        // Fetch the answers and create a local copy in memory
                        val answers: List<AnswerEntity> = soLocalDataSource.getAnswers(questionId)
                        synchronized(lock) {
                            allAnswersForQuestion.put(questionId, answers)
                        }
                    }
                }

            } catch (exception: Exception) {
                // Possible exceptions like certification path not found, CertPathValidatorException
                exceptionHandler(Exception("Couldn't process the AB API request - ${exception.message}"))
            }
        }
    }

    override fun getAnswersForQuestion(questionId: Long): List<AnswerEntity>? =
        allAnswersForQuestion[questionId]

    override fun updateGuessedQuestion(questionId: Long, isGuessed: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            soLocalDataSource.updateGuessedQuestion(questionId, isGuessed)
        }
    }

    override fun fetchGuessedQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            val questions: List<QuestionEntity>? = soLocalDataSource.getGuessedQuestions()
            guessedQuestions.clear()
            if (!questions.isNullOrEmpty()) {
                guessedQuestions.addAll(questions)
            }
        }
    }

    override fun getGuessedQuestions(): List<QuestionEntity> = guessedQuestions

    override fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            soLocalDataSource.clearEntities()
        }
        synchronized(lock) {
            allQuestions.clear()
            allAnswersForQuestion.clear()
            guessedQuestions.clear()
        }
    }

}

private const val TAG = "SORepositoryImpl"