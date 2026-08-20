package com.example.data.db

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademyDao {

    // Lesson Progress
    @Query("SELECT * FROM lesson_progress")
    fun getAllLessonProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun getLessonProgress(lessonId: String): LessonProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLessonProgress(progress: LessonProgressEntity)

    @Query("SELECT COUNT(*) FROM lesson_progress WHERE isCompleted = 1")
    fun getCompletedLessonsCount(): Flow<Int>

    @Query("SELECT * FROM lesson_progress WHERE isBookmarked = 1")
    fun getBookmarkedLessonProgress(): Flow<List<LessonProgressEntity>>

    // Quiz Results
    @Query("SELECT * FROM quiz_results")
    fun getAllQuizResults(): Flow<List<QuizResultEntity>>

    @Query("SELECT * FROM quiz_results WHERE levelId = :levelId")
    suspend fun getQuizResultForLevel(levelId: Int): QuizResultEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateQuizResult(result: QuizResultEntity)

    // Challenge Progress
    @Query("SELECT * FROM challenge_progress")
    fun getAllChallengeProgress(): Flow<List<ChallengeProgressEntity>>

    @Query("SELECT * FROM challenge_progress WHERE challengeId = :challengeId")
    suspend fun getChallengeProgress(challengeId: String): ChallengeProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateChallengeProgress(progress: ChallengeProgressEntity)

    // Lesson Notes
    @Query("SELECT * FROM lesson_notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<LessonNoteEntity>>

    @Query("SELECT * FROM lesson_notes WHERE lessonId = :lessonId LIMIT 1")
    fun getNoteForLessonFlow(lessonId: String): Flow<LessonNoteEntity?>

    @Query("SELECT * FROM lesson_notes WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getNoteForLesson(lessonId: String): LessonNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNote(note: LessonNoteEntity): Long

    @Query("DELETE FROM lesson_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileOnce(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(profile: UserProfileEntity)

    // Achievements
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)

    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)

    // Reset all progress
    @Query("DELETE FROM lesson_progress")
    suspend fun clearAllLessonProgress()

    @Query("DELETE FROM quiz_results")
    suspend fun clearAllQuizResults()

    @Query("DELETE FROM challenge_progress")
    suspend fun clearAllChallengeProgress()

    @Query("DELETE FROM lesson_notes")
    suspend fun clearAllNotes()

    @Query("UPDATE achievements SET isUnlocked = 0, unlockedAt = NULL")
    suspend fun resetAchievements()

    @Query("UPDATE user_profile SET totalXp = 0, streakDays = 1, certificateEarnedAt = NULL WHERE id = 1")
    suspend fun resetProfileStats()
}
