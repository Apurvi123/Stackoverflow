package com.app.stackoverflow.repository.remotesource.models.answer

import com.app.stackoverflow.repository.remotesource.models.question.Owner
import com.google.gson.annotations.SerializedName

/**
 * Data model for Answer items
 */
data class AnswerItem (
    val owner: Owner,
    @SerializedName("is_accepted")
    val isAccepted: Boolean,
    val score: Int,
    @SerializedName("last_activity_date")
    val lastActivityDate: Long,
    @SerializedName("last_edit_date")
    val lastEditDate: Long?,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("question_id")
    val questionId: Long,
    @SerializedName("answer_id")
    val answerId: Long
)