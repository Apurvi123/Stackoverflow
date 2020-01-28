package com.app.stackoverflow.repository.localsource.dao

import androidx.room.*
import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity

@Dao
internal interface SODao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsertQuestions(questionList: List<QuestionEntity>)

    @Query("SELECT * FROM Questions")
    fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM QUESTIONS WHERE Question_Id = :questionId")
    fun getQuestionEntity(questionId: Long): QuestionEntity

    @Query("UPDATE Questions SET Is_Guessed = :isGuessed WHERE Question_Id= :questionId")
    fun updateGuessedQuestion(questionId: Long, isGuessed: Boolean)

    @Query("SELECT * FROM QUESTIONS WHERE Is_Guessed = 1")
    fun getGuessedQuestions(): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsertAnswers(answerList: List<AnswerEntity>)

    @Query("SELECT * FROM Answers WHERE Question_Id = :questionId")
    fun getAllAnswers(questionId: Long): List<AnswerEntity>

    @Query("DELETE FROM Questions")
    fun deleteAllQuestions()

    @Query("DELETE FROM Answers")
    fun deleteAllAnswers()

    @Transaction
    suspend fun deleteAndInsertQuestions(questionList: List<QuestionEntity>) {
        deleteAllQuestions()
        bulkInsertQuestions(questionList)
    }

    @Transaction
    suspend fun deleteAndInsertAnswers(answerList: List<AnswerEntity>) {
        deleteAllAnswers()
        bulkInsertAnswers(answerList)
    }

    @Transaction
    suspend fun deleteEntities() {
        deleteAllQuestions()
        deleteAllAnswers()
    }
}