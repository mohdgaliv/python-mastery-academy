package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.*

@Database(
    entities = [
        LessonProgressEntity::class,
        QuizResultEntity::class,
        ChallengeProgressEntity::class,
        LessonNoteEntity::class,
        UserProfileEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AcademyDatabase : RoomDatabase() {
    abstract fun academyDao(): AcademyDao

    companion object {
        @Volatile
        private var INSTANCE: AcademyDatabase? = null

        fun getInstance(context: Context): AcademyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AcademyDatabase::class.java,
                    "python_mastery_academy.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun getDatabase(context: Context): AcademyDatabase = getInstance(context)
    }
}
