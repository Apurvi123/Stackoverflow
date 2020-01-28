package com.app.stackoverflow.repository.remotesource.models.question

import com.google.gson.annotations.SerializedName

/**
 * Data model for question response
 */
data class QuestionResponse (
    val items: List<QuestionItem>,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)