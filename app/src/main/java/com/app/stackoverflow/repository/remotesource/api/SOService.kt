package com.app.stackoverflow.repository.remotesource.api

import com.app.stackoverflow.repository.remotesource.models.answer.AnswerResponse
import com.app.stackoverflow.repository.remotesource.models.question.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * APIs for stack overflow
 */
interface SOService {

    // Considering default values of order, sort, site for simplicity of the assignment
    @GET("questions?order=desc&sort=activity&site=stackoverflow")
    fun getQuestions(): Deferred<Response<QuestionResponse>>

    // Considering default values of order, sort, site for simplicity of the assignment
    @GET("questions/{questionId}/answers?order=desc&sort=activity&site=stackoverflow")
    fun getAnswersForQuestion(@Path("questionId") questionId: Long): Deferred<Response<AnswerResponse>>
}