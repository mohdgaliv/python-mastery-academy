package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ExecutionConsole
import com.example.ui.components.PythonCodeEditor
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueLight
import com.example.ui.viewmodel.AcademyViewModel

data class CodeTemplate(
    val title: String,
    val code: String
)

val PLAYGROUND_TEMPLATES = listOf(
    CodeTemplate(
        "Greeting",
        """# Python Basics
def welcome(name):
    return f"Welcome to Python Mastery Academy, {name}!"

msg = welcome("Developer")
print(msg)
"""
    ),
    CodeTemplate(
        "List Comp",
        """# Data transformation with List Comprehensions
numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
evens_squared = [n ** 2 for n in numbers if n % 2 == 0]

print(f"Original: {numbers}")
print(f"Evens Squared: {evens_squared}")
"""
    ),
    CodeTemplate(
        "OOP Bank",
        """# Object-Oriented Banking Account
class BankAccount:
    def __init__(self, owner, balance=0):
        self.owner = owner
        self.balance = balance

    def deposit(self, amount):
        self.balance += amount
        return "Deposited $" + f"{amount}. New balance: $" + f"{self.balance}"

acc = BankAccount("Alex", 250)
print(acc.deposit(100))
print(f"Account: {acc.owner}, Balance: $" + f"{acc.balance}")
"""
    ),
    CodeTemplate(
        "AI Perceptron",
        """# Artificial Neuron (Perceptron Forward Pass)
def relu(z):
    return max(0, z)

inputs = [0.8, -0.5, 1.2]
weights = [0.5, 1.0, 0.3]
bias = 0.2

# Weighted sum: z = sum(x * w) + b
z = sum(x * w for x, w in zip(inputs, weights)) + bias
output = relu(z)

print(f"Inputs: {inputs}")
print(f"Weighted sum (z): {round(z, 3)}")
print(f"Neuron Output (ReLU): {round(output, 3)}")
"""
    ),
    CodeTemplate(
        "Fibonacci",
        """# Iterative Fibonacci Series
def generate_fibonacci(n):
    seq = []
    a, b = 0, 1
    for _ in range(n):
        seq.append(a)
        a, b = b, a + b
    return seq

print("First 10 Fibonacci numbers:")
print(generate_fibonacci(10))
"""
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundScreen(
    viewModel: AcademyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val code by viewModel.playgroundCode.collectAsState()
    val executionResult by viewModel.playgroundResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Python IDE & Sandbox",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Real offline Python execution engine",
                            fontSize = 12.sp,
                            color = PythonBlueLight
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Python Code", code))
                            Toast.makeText(context, "Code copied!", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy")
                    }
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
            // Preset Templates Carousel
            item {
                Column {
                    Text(
                        text = "Quick Presets & Templates",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PLAYGROUND_TEMPLATES.forEach { template ->
                            FilterChip(
                                selected = false,
                                onClick = { viewModel.updatePlaygroundCode(template.code) },
                                label = { Text(template.title) },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }
            }

            // Editor Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                                text = "Code Editor",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row {
                                OutlinedButton(
                                    onClick = { viewModel.updatePlaygroundCode("") },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(32.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Clear", fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        PythonCodeEditor(
                            code = code,
                            onCodeChange = { viewModel.updatePlaygroundCode(it) },
                            minLines = 10
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { viewModel.runPlaygroundCode() },
                            colors = ButtonDefaults.buttonColors(containerColor = PythonBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("run_playground_button")
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Python Script", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Output Terminal
            if (executionResult != null) {
                item {
                    Text(
                        text = "Standard Output & Diagnostics",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ExecutionConsole(result = executionResult)
                }
            }
        }
    }
}
