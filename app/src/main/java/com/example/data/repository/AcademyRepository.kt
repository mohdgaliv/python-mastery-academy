package com.example.data.repository

import com.example.data.curriculum.AchievementsData
import com.example.data.curriculum.ChallengesData
import com.example.data.curriculum.CurriculumData
import com.example.data.db.AcademyDao
import com.example.data.model.AchievementEntity
import com.example.data.model.ChallengeEntity
import com.example.data.model.CodingChallenge
import com.example.data.model.CourseLevel
import com.example.data.model.Lesson
import com.example.data.model.LessonNoteEntity
import com.example.data.model.LessonProgressEntity
import com.example.data.model.QuizResultEntity
import com.example.data.model.UserProfileEntity
import com.example.engine.ExecutionResult
import com.example.engine.PythonRunner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class AcademyRepository(
    private val dao: AcademyDao,
    private val pythonRunner: PythonRunner? = null
) {
    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val allLessonProgress: Flow<List<LessonProgressEntity>> = dao.getAllLessonProgress()
    val allQuizResults: Flow<List<QuizResultEntity>> = dao.getAllQuizResults()
    val allChallengeProgress: Flow<List<ChallengeEntity>> = dao.getAllChallengeProgress()
    val allAchievements: Flow<List<AchievementEntity>> = dao.getAllAchievements()
    val bookmarkedLessons: Flow<List<LessonProgressEntity>> = dao.getBookmarkedLessonProgress()

    suspend fun ensureInitialized() {
        val profile = dao.getUserProfileOnce()
        if (profile == null) {
            dao.insertOrUpdateUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = "Python Explorer",
                    totalXp = 0,
                    streakDays = 1,
                    lastActiveDate = "",
                    certificateId = "PMA-${UUID.randomUUID().toString().take(6).uppercase()}",
                    certificateEarnedAt = null,
                    isDarkMode = null
                )
            )
        }

        val existingAch = dao.getAllAchievements().firstOrNull() ?: emptyList()
        if (existingAch.isEmpty()) {
            val list = AchievementsData.achievements.map { ach ->
                AchievementEntity(
                    id = ach.id,
                    title = ach.title,
                    description = ach.description,
                    iconName = ach.iconName,
                    requiredProgress = ach.requiredProgress,
                    xpReward = ach.xpReward,
                    isUnlocked = false,
                    unlockedAt = null
                )
            }
            dao.insertAchievements(list)
        }
    }

    fun getCurriculumLevels(): List<CourseLevel> = CurriculumData.levels
    fun getChallenges(): List<CodingChallenge> = ChallengesData.challenges

    fun findLesson(id: String): Lesson? = CurriculumData.findLessonById(id)
    fun getNextLesson(id: String): Lesson? = CurriculumData.getNextLesson(id)
    fun getPreviousLesson(id: String): Lesson? = CurriculumData.getPreviousLesson(id)
    fun getLevel(id: Int): CourseLevel? = CurriculumData.getLevelById(id)

    suspend fun getLessonProgress(lessonId: String): LessonProgressEntity? {
        return dao.getLessonProgress(lessonId)
    }

    suspend fun markLessonCompleted(lessonId: String, xpEarned: Int) {
        val existing = dao.getLessonProgress(lessonId)
        val now = System.currentTimeMillis()
        val lesson = findLesson(lessonId)
        val levelId = lesson?.levelId ?: 1

        if (existing == null) {
            dao.insertOrUpdateLessonProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    levelId = levelId,
                    isCompleted = true,
                    completedAt = now,
                    lastViewedAt = now
                )
            )
            addXp(xpEarned)
        } else if (!existing.isCompleted) {
            dao.insertOrUpdateLessonProgress(
                existing.copy(
                    isCompleted = true,
                    completedAt = now,
                    lastViewedAt = now
                )
            )
            addXp(xpEarned)
        }
        checkAndUnlockAchievements()
    }

    suspend fun toggleBookmark(lessonId: String) {
        val existing = dao.getLessonProgress(lessonId)
        val now = System.currentTimeMillis()
        val lesson = findLesson(lessonId)
        val levelId = lesson?.levelId ?: 1

        if (existing == null) {
            dao.insertOrUpdateLessonProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    levelId = levelId,
                    isBookmarked = true,
                    lastViewedAt = now
                )
            )
        } else {
            dao.insertOrUpdateLessonProgress(
                existing.copy(isBookmarked = !existing.isBookmarked, lastViewedAt = now)
            )
        }
    }

    suspend fun saveLessonNote(lessonId: String, content: String) {
        val lesson = findLesson(lessonId)
        val existing = dao.getNoteForLesson(lessonId)
        val note = LessonNoteEntity(
            id = existing?.id ?: 0,
            lessonId = lessonId,
            lessonTitle = lesson?.title ?: "Python Lesson",
            levelId = lesson?.levelId ?: 1,
            content = content,
            updatedAt = System.currentTimeMillis()
        )
        dao.insertOrUpdateNote(note)
    }

    suspend fun getLessonNote(lessonId: String): String? {
        return dao.getNoteForLesson(lessonId)?.content
    }

    fun getLessonNoteFlow(lessonId: String): Flow<LessonNoteEntity?> {
        return dao.getNoteForLessonFlow(lessonId)
    }

    suspend fun recordQuizScore(levelId: Int, scorePercent: Int, passed: Boolean, xpEarned: Int) {
        val level = getLevel(levelId)
        val totalQ = level?.quiz?.questions?.size ?: 5
        val correctScore = (scorePercent * totalQ) / 100

        dao.insertOrUpdateQuizResult(
            QuizResultEntity(
                levelId = levelId,
                score = correctScore,
                totalQuestions = totalQ,
                passed = passed,
                completedAt = System.currentTimeMillis(),
                bestScore = correctScore
            )
        )
        if (passed) {
            addXp(xpEarned)
        }
        checkAndUnlockAchievements()
    }

    suspend fun recordChallengeCompletion(challengeId: String, userCode: String, xpEarned: Int) {
        val existing = dao.getChallengeProgress(challengeId)
        if (existing == null || !existing.isCompleted) {
            dao.insertOrUpdateChallengeProgress(
                ChallengeEntity(
                    challengeId = challengeId,
                    isCompleted = true,
                    userCode = userCode,
                    completedAt = System.currentTimeMillis()
                )
            )
            addXp(xpEarned)
        }
        checkAndUnlockAchievements()
    }

    suspend fun addXp(amount: Int) {
        val profile = dao.getUserProfileOnce() ?: return
        dao.insertOrUpdateUserProfile(
            profile.copy(
                totalXp = profile.totalXp + amount
            )
        )
    }

    suspend fun updateDisplayName(name: String) {
        val profile = dao.getUserProfileOnce() ?: return
        dao.insertOrUpdateUserProfile(profile.copy(name = name))
    }

    suspend fun grantCertificate(dateStr: String) {
        val profile = dao.getUserProfileOnce() ?: return
        dao.insertOrUpdateUserProfile(
            profile.copy(
                certificateEarnedAt = System.currentTimeMillis()
            )
        )
        checkAndUnlockAchievements()
    }

    suspend fun resetAllProgress() {
        dao.clearAllLessonProgress()
        dao.clearAllQuizResults()
        dao.clearAllChallengeProgress()
        dao.clearAllNotes()
        dao.resetAchievements()
        dao.resetProfileStats()
        ensureInitialized()
    }

    suspend fun checkAndUnlockAchievements() {
        val progressList = dao.getAllLessonProgress().firstOrNull() ?: emptyList()
        val completedCount = progressList.count { it.isCompleted }
        val quizResults = dao.getAllQuizResults().firstOrNull() ?: emptyList()
        val passedQuizzes = quizResults.count { it.passed }
        val perfectQuizzes = quizResults.count { it.scorePercent == 100 }
        val challenges = dao.getAllChallengeProgress().firstOrNull() ?: emptyList()
        val solvedChallenges = challenges.count { it.isCompleted }
        val profile = dao.getUserProfileOnce()

        // 1st lesson
        if (completedCount >= 1) unlockAch("ach_first_lesson", 1)
        if (completedCount >= 5) unlockAch("ach_5_lessons", 5)
        if (completedCount >= 10) unlockAch("ach_10_lessons", 10)
        if (completedCount >= 25) unlockAch("ach_25_lessons", 25)
        if (completedCount >= CurriculumData.totalLessonsCount) unlockAch("ach_all_lessons", completedCount)

        // Quizzes
        if (passedQuizzes >= 1) unlockAch("ach_first_quiz", 1)
        if (perfectQuizzes >= 1) unlockAch("ach_perfect_quiz", 1)

        // Challenges
        if (solvedChallenges >= 1) unlockAch("ach_first_challenge", 1)
        if (solvedChallenges >= 5) unlockAch("ach_5_challenges", 5)

        // Certificate
        if (profile?.certificateEarnedAt != null) {
            unlockAch("ach_graduate", 1)
        }
    }

    private suspend fun unlockAch(id: String, progressVal: Int) {
        val existingList = dao.getAllAchievements().firstOrNull() ?: return
        val existing = existingList.firstOrNull { it.id == id } ?: return
        if (!existing.isUnlocked) {
            dao.updateAchievement(
                existing.copy(
                    isUnlocked = true,
                    unlockedAt = System.currentTimeMillis()
                )
            )
            addXp(existing.xpReward)
        }
    }

    suspend fun executePythonCode(code: String): ExecutionResult {
        return pythonRunner?.executePythonCode(code) ?: runDirectInterpreter(code)
    }

    private fun runDirectInterpreter(code: String): ExecutionResult {
        val runner = PythonRunner(context = android.app.Application())
        return kotlinx.coroutines.runBlocking {
            runner.executePythonCode(code)
        }
    }
}
