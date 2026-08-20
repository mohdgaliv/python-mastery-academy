package com.example.data.model

data class CourseLevel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String, // Emoji or symbol
    val colorHex: Long,
    val lessons: List<Lesson>,
    val quiz: ModuleQuiz
)

data class Lesson(
    val id: String,
    val levelId: Int,
    val orderNumber: Int,
    val title: String,
    val subtitle: String,
    val estimatedMinutes: Int,
    val xpReward: Int = 50,
    val conceptExplanation: String,
    val syntax: String,
    val codeExample: String,
    val expectedOutput: String,
    val commonMistakes: List<String>,
    val keyTakeaways: List<String>,
    val practiceTask: PracticeTask,
    val miniQuiz: MiniQuizQuestion? = null
)

data class PracticeTask(
    val title: String,
    val description: String,
    val starterCode: String,
    val expectedOutput: String,
    val solutionCode: String,
    val hint: String
)

data class MiniQuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class ModuleQuiz(
    val levelId: Int,
    val title: String,
    val passingScorePercent: Int = 70,
    val xpReward: Int = 150,
    val questions: List<QuizQuestion>
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

enum class ChallengeDifficulty {
    EASY, MEDIUM, HARD
}

data class CodingChallenge(
    val id: String,
    val title: String,
    val topic: String,
    val difficulty: ChallengeDifficulty,
    val xpReward: Int,
    val description: String,
    val requirements: List<String>,
    val starterCode: String,
    val testCases: List<ChallengeTestCase>,
    val hints: List<String>,
    val solutionCode: String,
    val explanation: String
)

data class ChallengeTestCase(
    val inputDescription: String,
    val expectedOutput: String,
    val testCode: String // Code appended or executed to test
) {
    val inputSummary: String get() = inputDescription
    val callScript: String get() = testCode
}

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val requiredProgress: Int,
    val xpReward: Int
)

data class CertificateInfo(
    val certificateId: String,
    val studentName: String,
    val courseTitle: String = "Python Mastery: Fundamentals to AI/ML",
    val issueDate: String,
    val completionPercentage: Int = 100,
    val totalXp: Int,
    val totalLessonsCompleted: Int,
    val competencies: List<String> = listOf(
        "Python Core Syntax & Control Flow",
        "Data Structures & Algorithm Fundamentals",
        "Modular Architecture & File I/O",
        "Object-Oriented Programming (OOP)",
        "Advanced Metaprogramming & Generators",
        "Real-World Project Implementation",
        "NumPy, Pandas & Machine Learning Foundations"
    )
)
