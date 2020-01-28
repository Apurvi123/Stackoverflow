package com.app.stackoverflow.repository

import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity
import java.lang.Exception

/**
 * API to communicate to both the data sources and give the most recent data
 */
interface SORepository {

    /**
     * Method to fetch the questions from the api and update the local entity
     */
    fun fetchQuestions(exceptionHandler: (Exception) -> Unit)

    /**
     * Method to get all questions from the local entity
     */
    fun getAllQuestions(): List<QuestionEntity>

    /**
     * Method to fetch the answers from the api and update the local entity
     */
    fun fetchAnswersForQuestion(questionId: Long, exceptionHandler: (Exception) -> Unit)

    /**
     * Method to get all answers from the local entity
     */
    fun getAnswersForQuestion(questionId: Long): List<AnswerEntity>?

    /**
     * Method to update the local guessed question entity
     */
    fun updateGuessedQuestion(questionId: Long, isGuessed: Boolean)

    /**
     * Method to fetch all the guessed questions from the local entity
     */
    fun fetchGuessedQuestions()

    /**
     * Method to get all the guessed questions from the local entity
     */
    fun getGuessedQuestions(): List<QuestionEntity>

    /**
     * Method to clear all entities
     */
    fun clearAll()
}