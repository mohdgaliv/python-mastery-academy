package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.db.AcademyDatabase
import com.example.data.repository.AcademyRepository
import com.example.ui.navigation.AppNavigation
import com.example.ui.theme.PythonMasteryTheme
import com.example.ui.viewmodel.AcademyViewModel
import com.example.ui.viewmodel.AcademyViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: AcademyViewModel by viewModels {
        val database = AcademyDatabase.getDatabase(applicationContext)
        val repository = AcademyRepository(database.academyDao())
        AcademyViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PythonMasteryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
