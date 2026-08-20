package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.ChallengesData
import com.example.data.model.ChallengeDifficulty
import com.example.data.model.ChallengeTestCase
import com.example.data.model.CodingChallenge
import com.example.engine.ExecutionResult
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.PythonCodeEditor
import com.example.ui.components.XpBadge
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueLight
import com.example.ui.theme.PythonYellow
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.AcademyViewModel

data class TestCaseRunResult(
    val testCase: ChallengeTestCase,
    val actualOutput: String,
    val passed: Boolean,
    val error: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengesScreen(
    viewModel: AcademyViewModel,
    modifier: Modifier = Modifier
) {
    val challengeEntities by viewModel.challengeProgress.collectAsState()
    val solvedChallengeIds = remember(challengeEntities) {
        challengeEntities.filter { it.solved }.map { it.challengeId }.toSet()
    }

    var selectedChallengeId by remember { mutableStateOf<String?>(null) }
    var selectedFilter by remember { mutableStateOf<ChallengeDifficulty?>(null) }

    if (selectedChallengeId != null) {
        val challenge = ChallengesData.getChallengeById(selectedChallengeId!!)
        if (challenge != null) {
            ChallengeWorkspace(
                challenge = challenge,
                isSolved = challenge.id in solvedChallengeIds,
                onBack = { selectedChallengeId = null },
                onSolve = { code, xp ->
                    viewModel.recordChallengeCompletion(challenge.id, code, xp)
                },
                onExecute = { code -> viewModel.executeCode(code) }
            )
            return
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coding Challenges",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Algorithm Practice & Problem Solving",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Filter Chips Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedFilter == null,
                            onClick = { selectedFilter = null },
                            label = { Text("All (${ChallengesData.challenges.size})") }
                        )
                        FilterChip(
                            selected = selectedFilter == ChallengeDifficulty.EASY,
                            onClick = { selectedFilter = ChallengeDifficulty.EASY },
                            label = { Text("Easy") }
                        )
                        FilterChip(
                            selected = selectedFilter == ChallengeDifficulty.MEDIUM,
                            onClick = { selectedFilter = ChallengeDifficulty.MEDIUM },
                            label = { Text("Medium") }
                        )
                        FilterChip(
                            selected = selectedFilter == ChallengeDifficulty.HARD,
                            onClick = { selectedFilter = ChallengeDifficulty.HARD },
                            label = { Text("Hard") }
                        )
                    }
                }
            }

            val filteredChallenges = ChallengesData.challenges.filter {
                selectedFilter == null || it.difficulty == selectedFilter
            }

            items(filteredChallenges) { challenge ->
                val isSolved = challenge.id in solvedChallengeIds

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedChallengeId = challenge.id }
                        .testTag("challenge_item_${challenge.id}")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                DifficultyBadge(difficulty = challenge.difficulty)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = challenge.topic,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                XpBadge(xp = challenge.xpReward)
                                if (isSolved) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Solved",
                                        tint = SuccessGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = challenge.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = challenge.description,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = if (isSolved) "Review Solution →" else "Solve Challenge →",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSolved) SuccessGreen else PythonBlueLight
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeWorkspace(
    challenge: CodingChallenge,
    isSolved: Boolean,
    onBack: () -> Unit,
    onSolve: (String, Int) -> Unit,
    onExecute: (String) -> ExecutionResult,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var code by remember { mutableStateOf(challenge.starterCode) }
    var testResults by remember { mutableStateOf<List<TestCaseRunResult>?>(null) }
    var showHints by remember { mutableStateOf(false) }
    var showSolution by remember { mutableStateOf(false) }
    var allPassed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = challenge.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${challenge.topic} • +${challenge.xpReward} XP",
                            fontSize = 12.sp,
                            color = PythonBlueLight
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    DifficultyBadge(difficulty = challenge.difficulty, modifier = Modifier.padding(end = 12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Problem Description
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Problem Statement",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PythonBlueLight
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = challenge.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )

                        if (challenge.requirements.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Requirements & Constraints:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            challenge.requirements.forEach { req ->
                                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                    Text(text = "• ", color = PythonYellow, fontWeight = FontWeight.Bold)
                                    Text(text = req, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }

            // Interactive Code Editor
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Your Solution Code",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        PythonCodeEditor(
                            code = code,
                            onCodeChange = { code = it },
                            minLines = 8
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                // Run against all test cases
                                val results = challenge.testCases.map { tc ->
                                    val testScript = "$code\n${tc.callScript}"
                                    val res = onExecute(testScript)
                                    val actual = res.stdout.lines().lastOrNull { it.isNotBlank() } ?: ""
                                    val passed = actual.trim() == tc.expectedOutput.trim()
                                    TestCaseRunResult(tc, actual, passed, res.error)
                                }
                                testResults = results
                                val passedAll = results.all { it.passed }
                                allPassed = passedAll
                                if (passedAll) {
                                    onSolve(code, challenge.xpReward)
                                    Toast.makeText(context, "🎉 All Test Cases Passed! +${challenge.xpReward} XP", Toast.LENGTH_LONG).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PythonBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("run_challenge_tests_button")
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Test Cases", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Test Results View
            if (testResults != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Test Results",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (allPassed) SuccessGreen else ErrorRed
                                )
                                Text(
                                    text = if (allPassed) "All Passed (${testResults?.size}/${testResults?.size})" else "Failed Tests",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (allPassed) SuccessGreen else ErrorRed
                                )
                            }

                            testResults?.forEachIndexed { index, tr ->
                                Surface(
                                    color = Color(0xFF0F172A),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Test Case ${index + 1}: ${tr.testCase.inputSummary}",
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Monospace,
                                                color = Color(0xFFE2E8F0)
                                            )
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = if (tr.passed) Icons.Default.Check else Icons.Default.Close,
                                                    contentDescription = null,
                                                    tint = if (tr.passed) SuccessGreen else ErrorRed,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = if (tr.passed) "PASS" else "FAIL",
                                                    color = if (tr.passed) SuccessGreen else ErrorRed,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Expected: ${tr.testCase.expectedOutput} | Actual: ${tr.actualOutput}",
                                            fontSize = 11.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = if (tr.passed) Color(0xFF94A3B8) else ErrorRed
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Hints & Solution Explanation
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (challenge.hints.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { showHints = !showHints },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showHints) "Hide Hints" else "Hints 💡", fontSize = 13.sp)
                        }
                    }
                    if (challenge.solutionCode.isNotBlank()) {
                        OutlinedButton(
                            onClick = { showSolution = !showSolution },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showSolution) "Hide Solution" else "Solution 🔑", fontSize = 13.sp)
                        }
                    }
                }
            }

            if (showHints && challenge.hints.isNotEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = "💡 Problem Hints", fontWeight = FontWeight.Bold, color = WarningAmber)
                            Spacer(modifier = Modifier.height(6.dp))
                            challenge.hints.forEach { hint ->
                                Text(text = "• $hint", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(vertical = 2.dp))
                            }
                        }
                    }
                }
            }

            if (showSolution && challenge.solutionCode.isNotBlank()) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = "🔑 Reference Solution", fontWeight = FontWeight.Bold, color = SuccessGreen)
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                color = Color(0xFF0F172A),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = challenge.solutionCode,
                                    color = Color(0xFFF1F5F9),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            if (challenge.explanation.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = challenge.explanation,
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
}
