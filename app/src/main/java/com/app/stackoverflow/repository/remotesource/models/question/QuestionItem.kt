package com.app.stackoverflow.repository.remotesource.models.question

import com.google.gson.annotations.SerializedName

/**
 * Data model for Question items
 */
data class QuestionItem (
    val owner: Owner,
    @SerializedName("is_answered")
    val isAnswered: Boolean,
    @SerializedName("view_count")
    val viewCount: Int,
    @SerializedName("answer_count")
    val answerCount: Int,
    val score: Int,
    @SerializedName("last_activity_date")
    val lastActivityDate: Long,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("question_id")
    val questionId: Long,
    val link: String,
    val title: String
)