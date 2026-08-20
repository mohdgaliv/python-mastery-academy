package com.example.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val levelId: Int = 1,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false,
    val completedAt: Long? = null,
    val lastViewedAt: Long = System.currentTimeMillis()
) {
    @get:Ignore
    val completed: Boolean get() = isCompleted

    @get:Ignore
    val bookmarked: Boolean get() = isBookmarked

    @get:Ignore
    val lastAccessedAt: Long get() = lastViewedAt
}

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey val levelId: Int,
    val score: Int,
    val totalQuestions: Int,
    val passed: Boolean,
    val completedAt: Long = System.currentTimeMillis(),
    val bestScore: Int = score
) {
    @get:Ignore
    val scorePercent: Int get() = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
}

@Entity(tableName = "challenge_progress")
data class ChallengeProgressEntity(
    @PrimaryKey val challengeId: String,
    val isCompleted: Boolean = false,
    val userCode: String = "",
    val completedAt: Long? = null
) {
    @get:Ignore
    val solved: Boolean get() = isCompleted
}

typealias ChallengeEntity = ChallengeProgressEntity

@Entity(tableName = "lesson_notes")
data class LessonNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lessonId: String,
    val lessonTitle: String = "",
    val levelId: Int = 1,
    val content: String = "",
    val updatedAt: Long = System.currentTimeMillis()
) {
    @get:Ignore
    val noteContent: String get() = content
}

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Python Learner",
    val totalXp: Int = 0,
    val streakDays: Int = 1,
    val lastActiveDate: String = "",
    val certificateId: String = "PMA-7X92K4",
    val certificateEarnedAt: Long? = null,
    val isDarkMode: Boolean? = null
) {
    @get:Ignore
    val displayName: String get() = name

    @get:Ignore
    val certificateEarned: Boolean get() = certificateEarnedAt != null

    @get:Ignore
    val certificateIssuedDate: String
        get() = if (certificateEarnedAt != null) {
            SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(Date(certificateEarnedAt))
        } else ""
}

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val requiredProgress: Int = 1,
    val xpReward: Int = 50,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
) {
    @get:Ignore
    val unlocked: Boolean get() = isUnlocked

    @get:Ignore
    val progress: Int get() = if (isUnlocked) requiredProgress else 0
}
