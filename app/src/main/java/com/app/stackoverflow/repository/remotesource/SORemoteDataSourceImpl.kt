package com.app.stackoverflow.repository.remotesource

import android.annotation.SuppressLint
import com.app.stackoverflow.repository.remotesource.api.SOService
import com.app.stackoverflow.repository.remotesource.models.answer.AnswerResponse
import com.app.stackoverflow.repository.remotesource.models.question.QuestionResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SORemoteDataSourceImpl(private val stackOverflowService: SOService): SORemoteDataSource {

    companion object {

        fun create(baseURL: String): SORemoteDataSource {
            // Initialization of Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            val soService = retrofit.create(SOService::class.java)
            return SORemoteDataSourceImpl(soService)
        }
    }

    override suspend fun getQuestions(): Deferred<Response<QuestionResponse>> = stackOverflowService.getQuestions()

    override suspend fun getAnswersForQuestion(questionId: Long): Deferred<Response<AnswerResponse>> =
        stackOverflowService.getAnswersForQuestion(questionId)
}