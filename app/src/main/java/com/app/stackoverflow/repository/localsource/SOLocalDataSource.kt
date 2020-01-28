package com.app.stackoverflow.repository.localsource

import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity
import com.app.stackoverflow.repository.remotesource.models.answer.AnswerItem
import com.app.stackoverflow.repository.remotesource.models.question.QuestionItem

/**
 * API for local data source that communicates to Room DB
 */
interface SOLocalDataSource {

    /**
     * Method to insert questions into Question Entity
     */
    suspend fun insertQuestions(questionsList: List<QuestionItem>?)

    /**
     * Method to get the list of questions from database
     */
    fun getQuestions(): List<QuestionEntity>

    /**
     * Method to get single question record
     */
    fun getQuestionEntity(questionId: Long): QuestionEntity

    /**
     * Method to insert answers into Answer Entity
     */
    suspend fun insertAnswers(answerList: List<AnswerItem>?)

    /**
     * Method to get the list of answers from database
     */
    fun getAnswers(questionId: Long): List<AnswerEntity>

    /**
     * Method to update the guessed question
     */
    suspend fun updateGuessedQuestion(questionId: Long, isGuessed: Boolean)

    /**
     * Method to get all guessed questions
     */
    fun getGuessedQuestions(): List<QuestionEntity>?

    /**
     * Method to clear all entities from db
     */
    suspend fun clearEntities()
}