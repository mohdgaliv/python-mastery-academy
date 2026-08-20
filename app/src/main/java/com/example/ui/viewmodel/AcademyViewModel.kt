package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.curriculum.CurriculumData
import com.example.data.db.AcademyDatabase
import com.example.data.model.AchievementEntity
import com.example.data.model.ChallengeEntity
import com.example.data.model.CodingChallenge
import com.example.data.model.CourseLevel
import com.example.data.model.Lesson
import com.example.data.model.LessonProgressEntity
import com.example.data.model.QuizResultEntity
import com.example.data.model.UserProfileEntity
import com.example.data.repository.AcademyRepository
import com.example.engine.ExecutionResult
import com.example.engine.PythonRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UserRank(
    val title: String,
    val minXp: Int,
    val icon: String,
    val badgeColor: Long
)

val USER_RANKS = listOf(
    UserRank("Python Novice", 0, "🌱", 0xFF6B7280),
    UserRank("Syntax Apprentice", 250, "⚡", 0xFF3B82F6),
    UserRank("Logic Crafter", 750, "🔧", 0xFF10B981),
    UserRank("Data Wrangler", 1500, "📦", 0xFFF59E0B),
    UserRank("OOP Architect", 2500, "🏛️", 0xFF8B5CF6),
    UserRank("Python Master", 4000, "👑", 0xFFEC4899),
    UserRank("AI Pioneer", 6000, "🧠", 0xFF6366F1)
)

data class DashboardState(
    val profile: UserProfileEntity = UserProfileEntity(1, "Python Explorer", 0, 1, "", "PMA-001", null, null),
    val rank: UserRank = USER_RANKS[0],
    val nextRank: UserRank? = USER_RANKS[1],
    val totalLessonsCount: Int = 50,
    val completedLessonsCount: Int = 0,
    val progressPercent: Float = 0f,
    val nextLesson: Lesson? = null,
    val bookmarkedLessons: List<Lesson> = emptyList(),
    val completedLessonIds: Set<String> = emptySet(),
    val passedLevelQuizzes: Set<Int> = emptySet()
)

class AcademyViewModel(
    application: Application,
    private val repository: AcademyRepository
) : AndroidViewModel(application) {

    private val pythonRunner = PythonRunner(application)

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    val lessonProgress: StateFlow<List<LessonProgressEntity>> = repository.allLessonProgress.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val quizResults: StateFlow<List<QuizResultEntity>> = repository.allQuizResults.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val challengeProgress: StateFlow<List<ChallengeEntity>> = repository.allChallengeProgress.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val achievements: StateFlow<List<AchievementEntity>> = repository.allAchievements.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Combined Dashboard State Flow
    val dashboardState: StateFlow<DashboardState> = combine(
        repository.userProfile,
        repository.allLessonProgress,
        repository.allQuizResults
    ) { profile, progress, quizzes ->
        val user = profile ?: UserProfileEntity(1, "Python Explorer", 0, 1, "", "PMA-001", null, null)
        val completedSet = progress.filter { it.isCompleted }.map { it.lessonId }.toSet()
        val totalCount = CurriculumData.totalLessonsCount
        val completedCount = completedSet.size
        val percent = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f

        // Calculate Rank
        val currentRank = USER_RANKS.lastOrNull { user.totalXp >= it.minXp } ?: USER_RANKS[0]
        val currentRankIndex = USER_RANKS.indexOf(currentRank)
        val nextRank = if (currentRankIndex + 1 < USER_RANKS.size) USER_RANKS[currentRankIndex + 1] else null

        // Find Next Incomplete Lesson
        val allLessons = CurriculumData.levels.flatMap { it.lessons }
        val nextIncomplete = allLessons.firstOrNull { it.id !in completedSet } ?: allLessons.firstOrNull()

        val bookmarkedIds = progress.filter { it.isBookmarked }.map { it.lessonId }.toSet()
        val bookmarkedList = allLessons.filter { it.id in bookmarkedIds }

        val passedLevels = quizzes.filter { it.passed }.map { it.levelId }.toSet()

        DashboardState(
            profile = user,
            rank = currentRank,
            nextRank = nextRank,
            totalLessonsCount = totalCount,
            completedLessonsCount = completedCount,
            progressPercent = percent,
            nextLesson = nextIncomplete,
            bookmarkedLessons = bookmarkedList,
            completedLessonIds = completedSet,
            passedLevelQuizzes = passedLevels
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DashboardState()
    )

    // Active Playground State
    private val _playgroundCode = MutableStateFlow(
        """# Welcome to the Python Mastery Playground!
# Write any Python code below and tap 'Run Code'

def greet_developer(name, track="AI & ML"):
    print(f"Hello, {name}!")
    print(f"Enrolled in: {track}")
    return True

greet_developer("Explorer")
"""
    )
    val playgroundCode = _playgroundCode.asStateFlow()

    private val _playgroundResult = MutableStateFlow<ExecutionResult?>(null)
    val playgroundResult = _playgroundResult.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    fun getLevels(): List<CourseLevel> = repository.getCurriculumLevels()
    fun getChallenges(): List<CodingChallenge> = repository.getChallenges()

    fun getLesson(id: String): Lesson? = repository.findLesson(id)
    fun getNextLesson(id: String): Lesson? = repository.getNextLesson(id)
    fun getPreviousLesson(id: String): Lesson? = repository.getPreviousLesson(id)
    fun getLevel(levelId: Int): CourseLevel? = repository.getLevel(levelId)

    fun markLessonCompleted(lessonId: String, xpEarned: Int) {
        viewModelScope.launch {
            repository.markLessonCompleted(lessonId, xpEarned)
        }
    }

    fun toggleBookmark(lessonId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(lessonId)
        }
    }

    fun saveLessonNote(lessonId: String, note: String) {
        viewModelScope.launch {
            repository.saveLessonNote(lessonId, note)
        }
    }

    fun getLessonNoteFlow(lessonId: String) = repository.getLessonNoteFlow(lessonId)

    suspend fun executeCodeAsync(code: String): ExecutionResult {
        return pythonRunner.executePythonCode(code)
    }

    fun executeCode(code: String): ExecutionResult {
        return kotlinx.coroutines.runBlocking(Dispatchers.Default) {
            pythonRunner.executePythonCode(code)
        }
    }

    fun updatePlaygroundCode(code: String) {
        _playgroundCode.value = code
    }

    fun runPlaygroundCode() {
        viewModelScope.launch {
            val result = pythonRunner.executePythonCode(_playgroundCode.value)
            _playgroundResult.value = result
        }
    }

    fun recordQuizResult(levelId: Int, scorePercent: Int, passed: Boolean, xpEarned: Int) {
        viewModelScope.launch {
            repository.recordQuizScore(levelId, scorePercent, passed, xpEarned)
        }
    }

    fun recordChallengeCompletion(challengeId: String, code: String, xpEarned: Int) {
        viewModelScope.launch {
            repository.recordChallengeCompletion(challengeId, code, xpEarned)
        }
    }

    fun updateDisplayName(name: String) {
        viewModelScope.launch {
            repository.updateDisplayName(name)
        }
    }

    fun claimCertificate() {
        val dateStr = SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(Date())
        viewModelScope.launch {
            repository.grantCertificate(dateStr)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
        }
    }
}

class AcademyViewModelFactory(
    private val repository: AcademyRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val app = android.app.Application()
        return AcademyViewModel(app, repository) as T
    }
}
