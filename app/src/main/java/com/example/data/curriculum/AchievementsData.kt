package com.example.data.curriculum

import com.example.data.model.Achievement

object AchievementsData {
    val achievements = listOf(
        Achievement(
            id = "ach_first_lesson",
            title = "First Step",
            description = "Complete your very first Python lesson",
            iconName = "🌱",
            requiredProgress = 1,
            xpReward = 50
        ),
        Achievement(
            id = "ach_5_lessons",
            title = "Quick Starter",
            description = "Complete 5 curriculum lessons",
            iconName = "⚡",
            requiredProgress = 5,
            xpReward = 100
        ),
        Achievement(
            id = "ach_10_lessons",
            title = "Knowledge Seeker",
            description = "Complete 10 curriculum lessons",
            iconName = "📖",
            requiredProgress = 10,
            xpReward = 150
        ),
        Achievement(
            id = "ach_25_lessons",
            title = "Python Scholar",
            description = "Complete 25 curriculum lessons",
            iconName = "🎓",
            requiredProgress = 25,
            xpReward = 300
        ),
        Achievement(
            id = "ach_all_lessons",
            title = "Python Master",
            description = "Complete all lessons across all 10 levels",
            iconName = "👑",
            requiredProgress = 50,
            xpReward = 1000
        ),
        Achievement(
            id = "ach_first_quiz",
            title = "Quiz Ace",
            description = "Pass your first module quiz with 70%+ score",
            iconName = "🎯",
            requiredProgress = 1,
            xpReward = 100
        ),
        Achievement(
            id = "ach_perfect_quiz",
            title = "Perfectionist",
            description = "Achieve 100% on any module quiz",
            iconName = "✨",
            requiredProgress = 1,
            xpReward = 200
        ),
        Achievement(
            id = "ach_first_challenge",
            title = "Code Warrior",
            description = "Solve your first coding challenge",
            iconName = "⚔️",
            requiredProgress = 1,
            xpReward = 100
        ),
        Achievement(
            id = "ach_5_challenges",
            title = "Algorithm Specialist",
            description = "Solve 5 coding challenges",
            iconName = "🧩",
            requiredProgress = 5,
            xpReward = 250
        ),
        Achievement(
            id = "ach_portfolio_project",
            title = "Project Builder",
            description = "Complete a Level 9 portfolio project",
            iconName = "🛠️",
            requiredProgress = 1,
            xpReward = 200
        ),
        Achievement(
            id = "ach_ai_pioneer",
            title = "AI Pioneer",
            description = "Complete the Level 10 AI & Machine Learning module",
            iconName = "🧠",
            requiredProgress = 1,
            xpReward = 300
        ),
        Achievement(
            id = "ach_streak_3",
            title = "Streak Master",
            description = "Maintain a 3-day active learning streak",
            iconName = "🔥",
            requiredProgress = 3,
            xpReward = 150
        ),
        Achievement(
            id = "ach_graduate",
            title = "Certified Graduate",
            description = "Earn your official Python Mastery Academy Certificate",
            iconName = "📜",
            requiredProgress = 1,
            xpReward = 500
        )
    )
}
