package com.app.stackoverflow.repository.remotesource.models.answer

import com.google.gson.annotations.SerializedName

/**
 * Data model for Answer response
 */
data class AnswerResponse(
    val items: List<AnswerItem>,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)