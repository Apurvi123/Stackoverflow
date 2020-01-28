package com.app.stackoverflow.repository.localsource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Question Entity
 */
@Entity(tableName = "Questions")
data class QuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = "Question_Id") val questionId: Long,
    @ColumnInfo(name = "User_Id") val userId: Long,
    @ColumnInfo(name = "Display_Name") val displayName: String,
    @ColumnInfo(name = "Profile_Image") val profileImage: String,
    @ColumnInfo(name = "Is_Answered") val isAnswered: Boolean,
    @ColumnInfo(name = "Answer_Count") val answerCount: Int,
    @ColumnInfo(name = "Last_Activity_Date") val lastActivityDate: Long,
    @ColumnInfo(name = "Creation_Date") val creationDate: Long,
    @ColumnInfo(name = "Web_Link") val webLink: String,
    @ColumnInfo(name = "title") val questionTitle: String,
    @ColumnInfo(name= "Is_Guessed") val isGuessed: Boolean
)