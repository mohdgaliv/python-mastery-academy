package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.CurriculumData
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyViewModel

@Composable
fun HomeScreen(
    viewModel: AcademyViewModel,
    onNavigateToRoadmap: () -> Unit,
    onNavigateToLesson: (String) -> Unit,
    onNavigateToChallenges: () -> Unit,
    onNavigateToPlayground: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToCertificate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dashboardState by viewModel.dashboardState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ElegantDarkBg),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Header: Title, Pro Member Badge, Streak & Profile Avatar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Python Mastery",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElegantDarkPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                        )
                        Text(
                            text = dashboardState.rank.title.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElegantDarkTextSecondary,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Streak Pill
                    Surface(
                        color = ElegantDarkSurfaceElevated,
                        shape = CircleShape,
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = "🔥", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${dashboardState.profile.streakDays}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Avatar Circle
                    val initials = dashboardState.profile.displayName
                        .split(" ")
                        .mapNotNull { it.firstOrNull()?.toString() }
                        .take(2)
                        .joinToString("")
                        .ifEmpty { "PY" }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(ElegantDarkAvatarBg)
                            .border(1.dp, ElegantDarkBorderLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Hero "Currently Learning" Card (Elegant Dark Blue Contrast Banner)
        item {
            val nextLesson = dashboardState.nextLesson
            val progressPercent = (dashboardState.progressPercent * 100).toInt()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(ElegantDarkPrimary)
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "CURRENTLY LEARNING",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElegantDarkOnPrimary.copy(alpha = 0.8f),
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (nextLesson != null) nextLesson.title else "Full Course Completed!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = ElegantDarkOnPrimary,
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = if (nextLesson != null) "Level ${nextLesson.levelId} • ${nextLesson.subtitle}" else "All 50 Lessons Mastered",
                        fontSize = 13.sp,
                        color = ElegantDarkOnPrimary.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Progress Bar Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { dashboardState.progressPercent },
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = ElegantDarkOnPrimary,
                            trackColor = ElegantDarkOnPrimary.copy(alpha = 0.2f)
                        )
                        Text(
                            text = "$progressPercent%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElegantDarkOnPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (nextLesson != null) {
                                onNavigateToLesson(nextLesson.id)
                            } else {
                                onNavigateToCertificate()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElegantDarkOnPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("continue_lesson_button")
                    ) {
                        Text(
                            text = if (nextLesson != null) "Continue Lesson" else "View Certificate",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // 3. Stats Grid (2 Columns: Total XP & Roadmap)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Total XP Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(ElegantDarkSurface)
                        .border(1.dp, ElegantDarkBorder, RoundedCornerShape(24.dp))
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "TOTAL XP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElegantDarkTextSecondary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "%,d".format(dashboardState.profile.totalXp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "+120 today",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SuccessGreen
                        )
                    }
                }

                // Roadmap Card
                val passedCount = dashboardState.passedLevelQuizzes.size
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(ElegantDarkSurface)
                        .border(1.dp, ElegantDarkBorder, RoundedCornerShape(24.dp))
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "ROADMAP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElegantDarkTextSecondary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "%02d/10".format(passedCount),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Levels Finished",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = ElegantDarkTextSecondary
                        )
                    }
                }
            }
        }

        // 4. Goals Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "YOUR GOALS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElegantDarkTextMuted,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ElegantDarkPrimary,
                        modifier = Modifier.clickable { onNavigateToAchievements() }
                    )
                }

                // Goal Card 1: Master of Loops / Challenges
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ElegantDarkSurface,
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToChallenges() }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(ElegantDarkSurfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🏆", fontSize = 22.sp)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Master of Loops",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Finish Level 2 Coding Challenges",
                                fontSize = 12.sp,
                                color = ElegantDarkTextSecondary
                            )
                        }

                        // Target Indicator (Active)
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(2.dp, ElegantDarkPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(ElegantDarkPrimary)
                            )
                        }
                    }
                }

                // Goal Card 2: Logic Ninja / Quizzes
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ElegantDarkSurface.copy(alpha = 0.8f),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToRoadmap() }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(ElegantDarkSurfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🧠", fontSize = 22.sp)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Logic Ninja",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Score 100% on 5 Quizzes",
                                fontSize = 12.sp,
                                color = ElegantDarkTextSecondary
                            )
                        }

                        // Target Indicator (In-progress)
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(2.dp, ElegantDarkBorderMuted, CircleShape)
                        )
                    }
                }
            }
        }

        // 5. Quick Navigation Feature Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "LEARNING MODULES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElegantDarkTextMuted,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickNavCard(
                        title = "Roadmap",
                        subtitle = "10 Full Levels",
                        icon = Icons.Default.Map,
                        accentColor = ElegantDarkPrimary,
                        onClick = onNavigateToRoadmap,
                        modifier = Modifier.weight(1f)
                    )
                    QuickNavCard(
                        title = "Challenges",
                        subtitle = "Test & Run Code",
                        icon = Icons.Default.Code,
                        accentColor = PythonYellow,
                        onClick = onNavigateToChallenges,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickNavCard(
                        title = "Python IDE",
                        subtitle = "Offline Sandbox",
                        icon = Icons.Default.Terminal,
                        accentColor = SuccessGreen,
                        onClick = onNavigateToPlayground,
                        modifier = Modifier.weight(1f)
                    )
                    QuickNavCard(
                        title = "Achievements",
                        subtitle = "Badges & Ranks",
                        icon = Icons.Default.EmojiEvents,
                        accentColor = PurpleAccent,
                        onClick = onNavigateToAchievements,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 6. Curriculum Level Milestones Carousel
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "10-LEVEL ROADMAP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElegantDarkTextMuted,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = "Explore →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ElegantDarkPrimary,
                        modifier = Modifier.clickable { onNavigateToRoadmap() }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CurriculumData.levels.forEach { level ->
                        val levelCompletedCount = level.lessons.count { it.id in dashboardState.completedLessonIds }
                        val levelTotal = level.lessons.size

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = ElegantDarkSurface,
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
                            ),
                            modifier = Modifier
                                .width(170.dp)
                                .clickable { onNavigateToRoadmap() }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = level.icon, fontSize = 24.sp)
                                    if (levelCompletedCount == levelTotal) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "LEVEL ${level.id}",
                                    fontSize = 11.sp,
                                    color = ElegantDarkPrimary,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = level.title.substringAfter("— "),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { if (levelTotal > 0) levelCompletedCount.toFloat() / levelTotal else 0f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = ElegantDarkPrimary,
                                    trackColor = ElegantDarkSurfaceElevated
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "$levelCompletedCount / $levelTotal lessons",
                                    fontSize = 11.sp,
                                    color = ElegantDarkTextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // 7. Bookmarked Lessons
        if (dashboardState.bookmarkedLessons.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "SAVED BOOKMARKS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElegantDarkTextMuted,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )

                    dashboardState.bookmarkedLessons.forEach { lesson ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = ElegantDarkSurface,
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToLesson(lesson.id) }
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = null,
                                        tint = PythonYellow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = lesson.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Level ${lesson.levelId} • ${lesson.subtitle}",
                                            fontSize = 12.sp,
                                            color = ElegantDarkTextSecondary
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = ElegantDarkTextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = ElegantDarkSurface,
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(ElegantDarkBorder)
        ),
        modifier = modifier
            .height(105.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = ElegantDarkTextSecondary
                )
            }
        }
    }
}
