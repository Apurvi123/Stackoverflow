package com.app.stackoverflow.repository.localsource

import com.app.stackoverflow.repository.localsource.dao.SODao
import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity
import com.app.stackoverflow.repository.remotesource.models.answer.AnswerItem
import com.app.stackoverflow.repository.remotesource.models.question.QuestionItem

class SOLocalDataSourceImpl internal
    constructor(private val soDao: SODao): SOLocalDataSource {

    override suspend fun insertQuestions(questionsList: List<QuestionItem>?) {
        if (!questionsList.isNullOrEmpty()) {
            val questions = mutableListOf<QuestionEntity>()
            questionsList.forEach {
                val questionEntity = QuestionEntity(
                    it.questionId,
                    it.owner.userId ?: 0,
                    it.owner.displayName,
                    it.owner.profileImage ?: "",
                    it.isAnswered,
                    it.answerCount,
                    it.lastActivityDate,
                    it.creationDate,
                    it.link,
                    it.title,
                    false
                )
                questions.add(questionEntity)
            }
            soDao.deleteAndInsertQuestions(questions)
        }
    }

    override suspend fun insertAnswers(answerList: List<AnswerItem>?) {
        if (!answerList.isNullOrEmpty()) {
            val answers = mutableListOf<AnswerEntity>()
            answerList.forEach {
                val answerEntity = AnswerEntity(
                    it.questionId,
                    it.answerId,
                    it.owner.userId ?: 0,
                    it.owner.displayName,
                    it.owner.profileImage ?: "",
                    it.isAccepted,
                    it.lastActivityDate,
                    it.creationDate
                )
                answers.add(answerEntity)
            }
            soDao.deleteAndInsertAnswers(answers)
        }
    }

    override fun getQuestions(): List<QuestionEntity> = soDao.getAllQuestions()

    override fun getQuestionEntity(questionId: Long): QuestionEntity = soDao.getQuestionEntity(questionId)

    override fun getAnswers(questionId: Long): List<AnswerEntity> = soDao.getAllAnswers(questionId)

    override suspend fun updateGuessedQuestion(questionId: Long, isGuessed: Boolean) = soDao.updateGuessedQuestion(questionId, isGuessed)

    override fun getGuessedQuestions(): List<QuestionEntity>? = soDao.getGuessedQuestions()

    override suspend fun clearEntities() {
        soDao.deleteEntities()
    }
}