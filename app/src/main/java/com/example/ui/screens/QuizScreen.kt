package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.CurriculumData
import com.example.ui.components.XpBadge
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueLight
import com.example.ui.theme.PythonYellow
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AcademyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    levelId: Int,
    viewModel: AcademyViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val level = remember(levelId) { viewModel.getLevel(levelId) }
    val quiz = level?.quiz

    if (quiz == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Quiz not available for Level $levelId")
        }
        return
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionIndex -> selectedOption
    var quizFinished by remember { mutableStateOf(false) }

    val currentQuestion = quiz.questions[currentQuestionIndex]
    val totalQuestions = quiz.questions.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Level $levelId Quiz",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        if (!quizFinished) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Progress Indicator
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PythonBlueLight
                            )
                            Text(
                                text = "Pass: ${quiz.passingScorePercent}%",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (currentQuestionIndex + 1).toFloat() / totalQuestions.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PythonBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }

                    // Question Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = currentQuestion.question,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp
                            )

                            if (currentQuestion.codeSnippet != null) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    color = Color(0xFF0F172A),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = currentQuestion.codeSnippet,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp,
                                        color = Color(0xFF38BDF8),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Options List
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        currentQuestion.options.forEachIndexed { optIndex, optionText ->
                            val isSelected = userAnswers[currentQuestionIndex] == optIndex

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) PythonBlue.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface,
                                border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = SolidColor(PythonBlue)) else null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { userAnswers[currentQuestionIndex] = optIndex }
                                    .testTag("quiz_option_${optIndex}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) PythonBlue else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${('A' + optIndex)}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = optionText,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom Nav Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentQuestionIndex > 0) {
                        OutlinedButton(
                            onClick = { currentQuestionIndex-- },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    val hasAnsweredCurrent = userAnswers.containsKey(currentQuestionIndex)
                    val isLastQuestion = currentQuestionIndex == totalQuestions - 1

                    Button(
                        onClick = {
                            if (isLastQuestion) {
                                // Calculate score and record
                                val correctCount = quiz.questions.indices.count { idx ->
                                    userAnswers[idx] == quiz.questions[idx].correctOptionIndex
                                }
                                val scorePercent = ((correctCount.toFloat() / totalQuestions.toFloat()) * 100).toInt()
                                val passed = scorePercent >= quiz.passingScorePercent
                                viewModel.recordQuizResult(levelId, scorePercent, passed, quiz.xpReward)
                                quizFinished = true
                            } else {
                                currentQuestionIndex++
                            }
                        },
                        enabled = hasAnsweredCurrent,
                        colors = ButtonDefaults.buttonColors(containerColor = PythonBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("quiz_next_button")
                    ) {
                        Text(if (isLastQuestion) "Submit Quiz" else "Next Question →", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Results Screen
            val correctCount = quiz.questions.indices.count { idx ->
                userAnswers[idx] == quiz.questions[idx].correctOptionIndex
            }
            val scorePercent = ((correctCount.toFloat() / totalQuestions.toFloat()) * 100).toInt()
            val passed = scorePercent >= quiz.passingScorePercent

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (passed) SuccessGreen.copy(alpha = 0.15f) else ErrorRed.copy(alpha = 0.15f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (passed) Icons.Default.EmojiEvents else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (passed) SuccessGreen else ErrorRed,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = if (passed) "Quiz Passed! 🎉" else "Quiz Incomplete",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (passed) SuccessGreen else ErrorRed
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Score: $scorePercent% ($correctCount / $totalQuestions correct)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (passed) {
                                Spacer(modifier = Modifier.height(10.dp))
                                XpBadge(xp = quiz.xpReward)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        userAnswers.clear()
                                        currentQuestionIndex = 0
                                        quizFinished = false
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Retake")
                                }

                                Button(
                                    onClick = onNavigateBack,
                                    colors = ButtonDefaults.buttonColors(containerColor = PythonBlue),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Return to Roadmap")
                                }
                            }
                        }
                    }
                }

                // Answers Review Breakdown
                item {
                    Text(
                        text = "Question Review",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(quiz.questions.size) { idx ->
                    val q = quiz.questions[idx]
                    val userPick = userAnswers[idx]
                    val isCorrect = userPick == q.correctOptionIndex

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Question ${idx + 1}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PythonBlueLight
                                )
                                Text(
                                    text = if (isCorrect) "✓ Correct" else "✗ Incorrect",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) SuccessGreen else ErrorRed
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = q.question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Correct Answer: ${q.options[q.correctOptionIndex]}",
                                fontSize = 13.sp,
                                color = SuccessGreen,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = q.explanation,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
