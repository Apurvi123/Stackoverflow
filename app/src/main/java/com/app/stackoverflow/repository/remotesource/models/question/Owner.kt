package com.app.stackoverflow.repository.remotesource.models.question

import com.google.gson.annotations.SerializedName

/**
 * Data model for owner
 */
data class Owner (
    @SerializedName("user_id")
    val userId: Long? = 0,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("profile_image")
    val profileImage: String? = "",
    @SerializedName("display_name")
    val displayName: String,
    val link: String? = ""
)