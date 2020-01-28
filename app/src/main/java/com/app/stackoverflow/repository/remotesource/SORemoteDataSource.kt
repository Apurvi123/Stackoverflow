package com.app.stackoverflow.repository.remotesource

import com.app.stackoverflow.repository.remotesource.models.answer.AnswerResponse
import com.app.stackoverflow.repository.remotesource.models.question.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

/**
 * SORemoteDataSource is an abstraction of remote api
 */
interface SORemoteDataSource {

    /**
     * MEthod to get all the recent questions
     */
    suspend fun getQuestions(): Deferred<Response<QuestionResponse>>

    /**
     * MEthod to get answers for each question requested
     */
    suspend fun getAnswersForQuestion(questionId: Long): Deferred<Response<AnswerResponse>>
}