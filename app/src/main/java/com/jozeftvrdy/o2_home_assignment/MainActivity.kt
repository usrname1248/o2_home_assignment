package com.jozeftvrdy.o2_home_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jozeftvrdy.o2_home_assignment.core.navigation.Screen
import com.jozeftvrdy.o2_home_assignment.navigation.navigationContent
import com.jozeftvrdy.o2_home_assignment.ui.theme.O2_home_assignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            O2_home_assignmentTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.MainScratchScreen.route) {
                    navigationContent(navController = navController)
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
    O2_home_assignmentTheme {
        Greeting("Android")
    }
}