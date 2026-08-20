package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonBlueDark
import com.example.ui.theme.PythonYellow
import com.example.ui.theme.PythonYellowDark
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AcademyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificateScreen(
    viewModel: AcademyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dashboardState by viewModel.dashboardState.collectAsState()
    val isEarned = dashboardState.profile.certificateEarned
    val totalCount = dashboardState.totalLessonsCount
    val completedCount = dashboardState.completedLessonsCount
    val isEligible = completedCount >= 10 // Eligible after completing initial foundational milestones or all lessons

    var showNameDialog by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(dashboardState.profile.displayName) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Course Certificate",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    if (isEarned) {
                        IconButton(
                            onClick = {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "🎓 I just earned my Official Python Mastery Certificate from Python Mastery Academy!\n" +
                                                "Certificate ID: ${dashboardState.profile.certificateId}\n" +
                                                "Verified Graduate: ${dashboardState.profile.displayName}"
                                    )
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Certificate"))
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                        }
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Certificate Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                listOf(PythonYellow, Color(0xFFD97706), PythonYellow)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .testTag("certificate_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header Seal
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(PythonYellow, PythonYellowDark))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "PYTHON MASTERY ACADEMY",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = PythonYellow
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "CERTIFICATE OF MASTERY",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = Color(0xFFF8FAFC)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "This is officially presented to",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dashboardState.profile.displayName,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = PythonYellow,
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                onClick = {
                                    editedName = dashboardState.profile.displayName
                                    showNameDialog = true
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit name",
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "For successfully completing the comprehensive 10-Level Python Engineering Curriculum, mastering Python Core, OOP Architecture, Algorithms, Data Pipelines, and Artificial Intelligence.",
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Bottom Certificate Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column {
                                Text(
                                    text = "CERTIFICATE ID",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF64748B)
                                )
                                Text(
                                    text = dashboardState.profile.certificateId,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color(0xFF94A3B8)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "STATUS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF64748B)
                                )
                                Text(
                                    text = if (isEarned) "VERIFIED & ISSUED ✓" else "IN PROGRESS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isEarned) SuccessGreen else PythonYellow
                                )
                            }
                        }
                    }
                }
            }

            // Action / Eligibility Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Graduation Status",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Course Completion",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$completedCount / $totalCount Lessons",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PythonBlue
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PythonBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!isEarned) {
                            Button(
                                onClick = {
                                    viewModel.claimCertificate()
                                    Toast.makeText(context, "🎉 Official Certificate Claimed!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PythonYellowDark),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("claim_certificate_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = Color.Black)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Claim & Issue Certificate",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SuccessGreen.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SuccessGreen
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Certificate active & officially verified.",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SuccessGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Name Edit Dialog
    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Customize Certificate Name") },
            text = {
                Column {
                    Text("Enter your full name as you wish it to appear on your graduation certificate:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editedName.isNotBlank()) {
                            viewModel.updateDisplayName(editedName.trim())
                            showNameDialog = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
