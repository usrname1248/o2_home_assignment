package com.jozeftvrdy.o2_home_assignment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
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
                        Toast.makeText(
                            this@MainActivity,
                            this@MainActivity.getString(R.string.scratch_card_registration_failed_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            O2_home_assignmentTheme {
                val navController = rememberNavController()
                NavHost(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    navController = navController,
                    startDestination = Screen.MainScratchScreen.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                200,
                                easing = CubicBezierEasing(.35f, .51f, .44f, .85f)
                            )
                        ) + slideInHorizontally(
                            animationSpec = tween(
                                450,
                                easing = LinearEasing
                            ),
                            initialOffsetX = { it * 3 / 4 }

                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = 600,
                                delayMillis = 100
                            )
                        )
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(250))
                    },
                    popExitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = 350,
                                easing = CubicBezierEasing(.66f, .42f, .62f, .53f),
                                delayMillis = 150
                            )
                        ) + slideOutHorizontally(
                            animationSpec = tween(
                                500,
                                easing = LinearEasing
                            ),
                            targetOffsetX = { it }

                        )
                    }

                ) {
                    navigationContent(navController = navController)
                }
            }
        }
    }
}