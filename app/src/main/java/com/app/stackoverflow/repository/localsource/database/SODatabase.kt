package com.app.stackoverflow.repository.localsource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.stackoverflow.repository.Constants
import com.app.stackoverflow.repository.localsource.dao.SODao
import com.app.stackoverflow.repository.localsource.entity.AnswerEntity
import com.app.stackoverflow.repository.localsource.entity.QuestionEntity

/**
 * Database holder consisting the entities and db version
 */
@Database(
    entities = [QuestionEntity::class,
        AnswerEntity::class],
    version = Constants.DB_VERSION,
    exportSchema = false
)
internal abstract class SODatabase : RoomDatabase() {

    abstract fun soDao(): SODao

    companion object {
        // Using Room's Database builder
        fun getInstance(context: Context): SODatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                SODatabase::class.java,
                Constants.DB_NAME
            ).build()
        }
    }
}