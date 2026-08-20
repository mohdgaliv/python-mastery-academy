package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChallengeDifficulty
import com.example.engine.ExecutionResult
import com.example.ui.theme.*
import com.example.ui.theme.WarningAmber

@Composable
fun XpBadge(
    xp: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = ElegantDarkSurfaceElevated,
        shape = CircleShape,
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(ElegantDarkBorder)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(text = "⚡", fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$xp XP",
                color = ElegantDarkTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun StreakBadge(
    streakDays: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = ElegantDarkSurfaceElevated,
        shape = CircleShape,
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(ElegantDarkBorder)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(text = "🔥", fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$streakDays",
                color = ElegantDarkTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun DifficultyBadge(
    difficulty: ChallengeDifficulty,
    modifier: Modifier = Modifier
) {
    val (color, text) = when (difficulty) {
        ChallengeDifficulty.EASY -> SuccessGreen to "EASY"
        ChallengeDifficulty.MEDIUM -> WarningAmber to "MEDIUM"
        ChallengeDifficulty.HARD -> ErrorRed to "HARD"
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(color.copy(alpha = 0.4f))),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun PythonKeyboardBar(
    onInsert: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val quickTokens = listOf(
        "    " to "Tab (4 spaces)",
        "def " to "def",
        "return " to "return",
        "for " to "for",
        "in " to "in",
        "if " to "if",
        "else:" to "else:",
        "print()" to "print()",
        "len()" to "len()",
        "range()" to "range()",
        "[]" to "[]",
        "{}" to "{}",
        "()" to "()",
        ":" to ":",
        "==" to "==",
        "!=" to "!=",
        "=" to "=",
        "\"\"" to "\"\"",
        "'" to "'",
        "_" to "_",
        "#" to "#",
        "+" to "+",
        "-" to "-",
        "*" to "*",
        "/" to "/"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CodeEditorBackground)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        quickTokens.forEach { (token, label) ->
            Surface(
                onClick = { onInsert(token) },
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.height(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun PythonCodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    minLines: Int = 5,
    onRunCode: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val lines = code.lines()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CodeEditorBackground)
            .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(12.dp))
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(ErrorRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(WarningAmber)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(SuccessGreen)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "python3",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Python Code", code))
                        Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy code",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }

                if (!readOnly) {
                    IconButton(
                        onClick = { onCodeChange("") },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear code",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Editor Body with Line Numbers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Line numbers column
            Column(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(28.dp),
                horizontalAlignment = Alignment.End
            ) {
                val totalLines = maxOf(lines.size, minLines)
                for (i in 1..totalLines) {
                    Text(
                        text = "$i",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        color = Color(0xFF4B5563),
                        lineHeight = 20.sp
                    )
                }
            }

            // Code Text Area
            Box(modifier = Modifier.weight(1f)) {
                if (readOnly) {
                    Text(
                        text = highlightPythonSyntax(code),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    BasicTextField(
                        value = code,
                        onValueChange = onCodeChange,
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = Color(0xFFF1F5F9),
                            lineHeight = 20.sp
                        ),
                        cursorBrush = SolidColor(PythonYellow),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.None,
                            autoCorrectEnabled = false
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("python_code_input")
                    )
                }
            }
        }

        if (!readOnly) {
            PythonKeyboardBar(
                onInsert = { token ->
                    onCodeChange(code + token)
                }
            )
        }

        if (onRunCode != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onRunCode,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PythonBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("run_code_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Run",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Run Code", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ExecutionConsole(
    result: ExecutionResult?,
    modifier: Modifier = Modifier,
    expectedOutput: String? = null
) {
    if (result == null) return

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F172A)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(if (result.success) SuccessGreen.copy(alpha = 0.5f) else ErrorRed.copy(alpha = 0.5f))
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (result.success) Icons.Default.Check else Icons.Default.Warning,
                        contentDescription = if (result.success) "Success" else "Error",
                        tint = if (result.success) SuccessGreen else ErrorRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (result.success) "EXECUTION SUCCESSFUL" else "EXECUTION ERROR",
                        color = if (result.success) SuccessGreen else ErrorRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = "${result.executionTimeMs} ms",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Output / Traceback
            Surface(
                color = Color(0xFF020617),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    if (result.stdout.isNotEmpty()) {
                        Text(
                            text = result.stdout,
                            color = Color(0xFFE2E8F0),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    } else if (result.success) {
                        Text(
                            text = "(Process finished with exit code 0 - No output)",
                            color = Color(0xFF64748B),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    }

                    if (result.error != null) {
                        Text(
                            text = result.error,
                            color = ErrorRed,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Expected output match check if provided
            if (expectedOutput != null && result.success) {
                val matches = result.stdout.trim() == expectedOutput.trim()
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (matches) SuccessGreen.copy(alpha = 0.12f) else WarningAmber.copy(alpha = 0.12f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (matches) "✓ Output matches expected result exactly!" else "⚠ Output does not match expected result.",
                        color = if (matches) SuccessGreen else WarningAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

fun highlightPythonSyntax(code: String) = buildAnnotatedString {
    val keywords = setOf(
        "def", "return", "if", "elif", "else", "for", "while", "in", "import", "from",
        "as", "class", "try", "except", "finally", "raise", "with", "yield", "lambda",
        "pass", "break", "continue", "global", "nonlocal", "and", "or", "not", "is"
    )
    val builtins = setOf("print", "len", "range", "sum", "min", "max", "type", "str", "int", "float", "list", "dict", "set", "tuple", "bool")

    val lines = code.lines()
    lines.forEachIndexed { index, line ->
        if (line.trimStart().startsWith("#")) {
            // Whole comment line
            withStyle(SpanStyle(color = SyntaxComment)) {
                append(line)
            }
        } else {
            val words = line.split(Regex("(?<=\\W)|(?=\\W)"))
            for (word in words) {
                when {
                    word in keywords -> withStyle(SpanStyle(color = SyntaxKeyword, fontWeight = FontWeight.Bold)) { append(word) }
                    word in builtins -> withStyle(SpanStyle(color = SyntaxBuiltin)) { append(word) }
                    word.startsWith("\"") || word.startsWith("'") -> withStyle(SpanStyle(color = SyntaxString)) { append(word) }
                    word.toIntOrNull() != null || word.toDoubleOrNull() != null -> withStyle(SpanStyle(color = SyntaxNumber)) { append(word) }
                    else -> withStyle(SpanStyle(color = Color(0xFFF1F5F9))) { append(word) }
                }
            }
        }
        if (index < lines.size - 1) {
            append("\n")
        }
    }
}

@Composable
fun SimpleMarkdownView(
    markdown: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val lines = markdown.lines()
        for (line in lines) {
            when {
                line.startsWith("# ") -> {
                    Text(
                        text = line.removePrefix("# "),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                line.startsWith("## ") -> {
                    Text(
                        text = line.removePrefix("## "),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                line.startsWith("### ") -> {
                    Text(
                        text = line.removePrefix("### "),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PythonBlueLight
                    )
                }
                line.startsWith("- ") || line.startsWith("* ") -> {
                    Row(modifier = Modifier.padding(start = 6.dp)) {
                        Text(text = "• ", color = PythonYellow, fontWeight = FontWeight.Bold)
                        Text(
                            text = line.substring(2),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 20.sp
                        )
                    }
                }
                line.isNotBlank() -> {
                    Text(
                        text = line,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        lineHeight = 21.sp
                    )
                }
            }
        }
    }
}
