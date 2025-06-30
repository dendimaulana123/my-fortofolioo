package com.example.rmn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rmn.ui.theme.RMNTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.rmn.ui.BiodataScreen
import com.example.rmn.ui.EducationScreen
import com.example.rmn.ui.ActivityScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RMNTheme {
                var selectedIndex by remember { mutableStateOf(0) }
                val screens = listOf(
                    Triple("Biodata", Icons.Default.Person, "Biodata"),
                    Triple("Pendidikan", Icons.Default.School, "Pendidikan"),
                    Triple("Aktivitas", Icons.Default.Work, "Aktivitas")
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            screens.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    selected = selectedIndex == index,
                                    onClick = { selectedIndex = index },
                                    label = { Text(screen.first) },
                                    icon = { Icon(screen.second, contentDescription = screen.third) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (selectedIndex) {
                        0 -> BiodataScreen()
                        1 -> EducationScreen()
                        2 -> ActivityScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RMNTheme {
        Greeting("Android")
    }
}