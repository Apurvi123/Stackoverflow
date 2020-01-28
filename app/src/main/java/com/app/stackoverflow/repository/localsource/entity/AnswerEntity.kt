package com.app.stackoverflow.repository.localsource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Answer Entity
 */
@Entity(tableName = "Answers")
data class AnswerEntity (
    @ColumnInfo(name = "Question_Id") val questionId: Long,
    @ColumnInfo(name = "Answer_Id") val answerId: Long,
    @ColumnInfo(name = "User_Id") val userId: Long,
    @ColumnInfo(name = "Display_Name") val displayName: String,
    @ColumnInfo(name = "Profile_Image") val profileImage: String,
    @ColumnInfo(name = "Is_Accepted") val isAccepted: Boolean,
    @ColumnInfo(name = "Last_Activity_Date") val lastActivityDate: Long,
    @ColumnInfo(name = "Creation_Date") val creationDate: Long
){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Auto_Id") var id: Long = 0
}
