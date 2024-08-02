package com.jozeftvrdy.o2_home_assignment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jozeftvrdy.o2_home_assignment.core.navigation.Screen
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import com.jozeftvrdy.o2_home_assignment.navigation.navigationContent
import com.jozeftvrdy.o2_home_assignment.ui.theme.O2_home_assignmentTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.scratchCardUiState.collectLatest {
                    if (it is ScratchCardUiState.Revealed.RegistrationFailed) {
                        Toast.makeText(this@MainActivity, this@MainActivity.getString(R.string.scratch_card_registration_failed_message), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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