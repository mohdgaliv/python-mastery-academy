package com.example.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.CertificateScreen
import com.example.ui.screens.ChallengesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonScreen
import com.example.ui.screens.PlaygroundScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.RoadmapScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueLight
import com.example.ui.viewmodel.AcademyViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Roadmap : Screen("roadmap", "Curriculum", Icons.Default.Map)
    object Challenges : Screen("challenges", "Challenges", Icons.Default.Code)
    object Playground : Screen("playground", "IDE", Icons.Default.Terminal)
    object Certificate : Screen("certificate", "Certificate", Icons.Default.WorkspacePremium)
    object Achievements : Screen("achievements", "Achievements", Icons.Default.EmojiEvents)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)

    object Lesson : Screen("lesson/{lessonId}", "Lesson", Icons.Default.Home) {
        fun createRoute(lessonId: String) = "lesson/$lessonId"
    }

    object Quiz : Screen("quiz/{levelId}", "Quiz", Icons.Default.Home) {
        fun createRoute(levelId: Int) = "quiz/$levelId"
    }
}

val BOTTOM_NAV_ITEMS = listOf(
    Screen.Home,
    Screen.Roadmap,
    Screen.Challenges,
    Screen.Playground,
    Screen.Certificate
)

@Composable
fun AppNavigation(
    viewModel: AcademyViewModel,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldShowBottomBar = BOTTOM_NAV_ITEMS.any { it.route == currentRoute } ||
            currentRoute == Screen.Achievements.route ||
            currentRoute == Screen.Settings.route

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(
                    containerColor = ElegantDarkSurface,
                    contentColor = ElegantDarkTextPrimary,
                    tonalElevation = 0.dp,
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = ElegantDarkBorder,
                        shape = androidx.compose.ui.graphics.RectangleShape
                    )
                ) {
                    BOTTOM_NAV_ITEMS.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                            label = { 
                                Text(
                                    text = item.title,
                                    fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Medium,
                                    fontSize = 11.sp
                                ) 
                            },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ElegantDarkOnPrimary,
                                selectedTextColor = androidx.compose.ui.graphics.Color.White,
                                indicatorColor = ElegantDarkPrimary,
                                unselectedIconColor = ElegantDarkTextMuted,
                                unselectedTextColor = ElegantDarkTextSecondary
                            ),
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToRoadmap = { navController.navigate(Screen.Roadmap.route) },
                    onNavigateToLesson = { lessonId -> navController.navigate(Screen.Lesson.createRoute(lessonId)) },
                    onNavigateToChallenges = { navController.navigate(Screen.Challenges.route) },
                    onNavigateToPlayground = { navController.navigate(Screen.Playground.route) },
                    onNavigateToAchievements = { navController.navigate(Screen.Achievements.route) },
                    onNavigateToCertificate = { navController.navigate(Screen.Certificate.route) }
                )
            }

            composable(Screen.Roadmap.route) {
                RoadmapScreen(
                    viewModel = viewModel,
                    onNavigateToLesson = { lessonId -> navController.navigate(Screen.Lesson.createRoute(lessonId)) },
                    onNavigateToQuiz = { levelId -> navController.navigate(Screen.Quiz.createRoute(levelId)) }
                )
            }

            composable(Screen.Challenges.route) {
                ChallengesScreen(viewModel = viewModel)
            }

            composable(Screen.Playground.route) {
                PlaygroundScreen(viewModel = viewModel)
            }

            composable(Screen.Certificate.route) {
                CertificateScreen(viewModel = viewModel)
            }

            composable(Screen.Achievements.route) {
                AchievementsScreen(viewModel = viewModel)
            }

            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = viewModel)
            }

            composable(
                route = Screen.Lesson.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            ) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "l1_1"
                LessonScreen(
                    lessonId = lessonId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToLesson = { nextId ->
                        navController.navigate(Screen.Lesson.createRoute(nextId)) {
                            popUpTo(Screen.Roadmap.route)
                        }
                    }
                )
            }

            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("levelId") { type = NavType.IntType })
            ) { backStackEntry ->
                val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
                QuizScreen(
                    levelId = levelId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
