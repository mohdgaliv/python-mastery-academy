package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lesson
import com.example.engine.ExecutionResult
import com.example.ui.components.ExecutionConsole
import com.example.ui.components.PythonCodeEditor
import com.example.ui.components.SimpleMarkdownView
import com.example.ui.components.XpBadge
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueLight
import com.example.ui.theme.PythonYellow
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.AcademyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    lessonId: String,
    viewModel: AcademyViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToLesson: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lesson = remember(lessonId) { viewModel.getLesson(lessonId) }

    if (lesson == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Lesson not found: $lessonId")
        }
        return
    }

    val dashboardState by viewModel.dashboardState.collectAsState()
    val isCompleted = lesson.id in dashboardState.completedLessonIds
    val isBookmarked = dashboardState.bookmarkedLessons.any { it.id == lesson.id }

    // Interactive Practice Code State
    var practiceCode by remember(lessonId) { mutableStateOf(lesson.practiceTask?.starterCode ?: "") }
    var practiceResult by remember(lessonId) { mutableStateOf<ExecutionResult?>(null) }
    var showHint by remember(lessonId) { mutableStateOf(false) }
    var showSolution by remember(lessonId) { mutableStateOf(false) }

    // Example code execution state
    var exampleResult by remember(lessonId) { mutableStateOf<ExecutionResult?>(null) }

    // Mini Quiz State
    var selectedQuizOption by remember(lessonId) { mutableIntStateOf(-1) }
    var quizSubmitted by remember(lessonId) { mutableStateOf(false) }

    // Notes Sheet State
    var showNotesSheet by remember { mutableStateOf(false) }
    val noteEntity by viewModel.getLessonNoteFlow(lessonId).collectAsState(initial = null)
    var noteContent by remember(noteEntity) { mutableStateOf(noteEntity?.noteContent ?: "") }

    val nextLesson = remember(lessonId) { viewModel.getNextLesson(lessonId) }
    val previousLesson = remember(lessonId) { viewModel.getPreviousLesson(lessonId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Level ${lesson.levelId} • Lesson ${lesson.orderNumber}",
                            fontSize = 12.sp,
                            color = PythonBlueLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = lesson.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(lesson.id)
                            Toast.makeText(
                                context,
                                if (isBookmarked) "Removed bookmark" else "Lesson bookmarked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) PythonYellow else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { showNotesSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Notes",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (previousLesson != null) {
                        OutlinedButton(
                            onClick = { onNavigateToLesson(previousLesson.id) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 13.sp)
                        }
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = {
                            viewModel.markLessonCompleted(lesson.id, lesson.xpReward)
                            Toast.makeText(context, "+${lesson.xpReward} XP Earned! Lesson Complete!", Toast.LENGTH_SHORT).show()
                            if (nextLesson != null) {
                                onNavigateToLesson(nextLesson.id)
                            } else {
                                onNavigateBack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) SuccessGreen else PythonBlue
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("complete_lesson_button")
                    ) {
                        Text(
                            text = if (isCompleted) "Completed (Next →)" else "Mark Complete & Next →",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Header Card
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
                                text = "LEVEL ${lesson.levelId} • LESSON ${lesson.orderNumber}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PythonBlueLight
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                XpBadge(xp = lesson.xpReward)
                                if (isCompleted) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = SuccessGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = lesson.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lesson.subtitle,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "⏱ Estimated Time: ~${lesson.estimatedMinutes} minutes",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // 2. Concept Explanation
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Concept Breakdown",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PythonBlueLight
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        SimpleMarkdownView(markdown = lesson.conceptExplanation)
                    }
                }
            }

            // 3. Syntax Quick Reference
            if (lesson.syntax.isNotBlank()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Syntax Blueprint",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PythonYellow
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = Color(0xFF0F172A),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = lesson.syntax,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp,
                                    color = Color(0xFF38BDF8),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 4. Interactive Live Example
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
                                text = "Executable Example",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        PythonCodeEditor(
                            code = lesson.codeExample,
                            onCodeChange = {},
                            readOnly = true,
                            onRunCode = {
                                exampleResult = viewModel.executeCode(lesson.codeExample)
                            }
                        )

                        if (exampleResult != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            ExecutionConsole(result = exampleResult, expectedOutput = lesson.expectedOutput)
                        }
                    }
                }
            }

            // 5. Common Mistakes & Key Takeaways
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Common Mistakes
                        if (lesson.commonMistakes.isNotEmpty()) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "Common Pitfalls", fontWeight = FontWeight.Bold, color = WarningAmber, fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                lesson.commonMistakes.forEach { mistake ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text(text = "• ", color = WarningAmber, fontWeight = FontWeight.Bold)
                                        Text(text = mistake, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }

                        // Key Takeaways
                        if (lesson.keyTakeaways.isNotEmpty()) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "Key Takeaways", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                lesson.keyTakeaways.forEach { takeaway ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text(text = "✓ ", color = SuccessGreen, fontWeight = FontWeight.Bold)
                                        Text(text = takeaway, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 6. Interactive Practice Task
            if (lesson.practiceTask != null) {
                val task = lesson.practiceTask
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(PythonBlue.copy(alpha = 0.5f))),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Hands-On Practice Task",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PythonBlueLight
                                )
                                Surface(
                                    color = PythonBlue.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = task.title,
                                        color = PythonBlueLight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = task.description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Code Editor
                            PythonCodeEditor(
                                code = practiceCode,
                                onCodeChange = { practiceCode = it },
                                readOnly = false,
                                onRunCode = {
                                    practiceResult = viewModel.executeCode(practiceCode)
                                }
                            )

                            if (practiceResult != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                ExecutionConsole(
                                    result = practiceResult,
                                    expectedOutput = task.expectedOutput
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Hint & Solution Accordions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (task.hint.isNotBlank()) {
                                    OutlinedButton(
                                        onClick = { showHint = !showHint },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(if (showHint) "Hide Hint" else "Show Hint 💡", fontSize = 12.sp)
                                    }
                                }

                                if (task.solutionCode.isNotBlank()) {
                                    OutlinedButton(
                                        onClick = { showSolution = !showSolution },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(if (showSolution) "Hide Solution" else "View Solution 🔑", fontSize = 12.sp)
                                    }
                                }
                            }

                            if (showHint && task.hint.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    color = WarningAmber.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "💡 Hint: ${task.hint}",
                                        color = WarningAmber,
                                        fontSize = 13.sp,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }

                            if (showSolution && task.solutionCode.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "🔑 Solution Code:",
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = task.solutionCode,
                                        color = Color(0xFFF1F5F9),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 7. Mini Quiz Question
            if (lesson.miniQuiz != null) {
                val quiz = lesson.miniQuiz
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Quick Concept Check",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PythonYellow
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = quiz.question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            quiz.options.forEachIndexed { index, option ->
                                val isSelected = selectedQuizOption == index
                                val isCorrect = index == quiz.correctIndex

                                val bgColor = when {
                                    quizSubmitted && isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                    quizSubmitted && isSelected && !isCorrect -> ErrorRed.copy(alpha = 0.2f)
                                    isSelected -> PythonBlue.copy(alpha = 0.2f)
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = bgColor,
                                    border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = SolidColor(PythonBlue)) else null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable(enabled = !quizSubmitted) {
                                            selectedQuizOption = index
                                            quizSubmitted = true
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${('A' + index)}. ",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = option,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            if (quizSubmitted) {
                                Spacer(modifier = Modifier.height(10.dp))
                                val correct = selectedQuizOption == quiz.correctIndex
                                Surface(
                                    color = if (correct) SuccessGreen.copy(alpha = 0.12f) else ErrorRed.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = if (correct) "✓ Correct!" else "✗ Incorrect",
                                            fontWeight = FontWeight.Bold,
                                            color = if (correct) SuccessGreen else ErrorRed,
                                            fontSize = 13.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = quiz.explanation,
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

            // Extra space at bottom for scrolling above bottom bar
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Notes Bottom Sheet
    if (showNotesSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.saveLessonNote(lessonId, noteContent)
                showNotesSheet = false
            },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Lesson Notes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = {
                        viewModel.saveLessonNote(lessonId, noteContent)
                        showNotesSheet = false
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = "Personal study notes for '${lesson.title}'. Automatically saved to your device.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = noteContent,
                    onValueChange = { noteContent = it },
                    placeholder = { Text("Write your key insights, code snippets, or reminders here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = {
                        viewModel.saveLessonNote(lessonId, noteContent)
                        Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()
                        showNotesSheet = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PythonBlue),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Save Note")
                }
            }
        }
    }
}
