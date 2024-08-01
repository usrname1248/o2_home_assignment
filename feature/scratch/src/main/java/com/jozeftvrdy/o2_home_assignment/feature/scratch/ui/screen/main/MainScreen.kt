package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jozeftvrdy.o2_home_assignment.core.components.ButtonText
import com.jozeftvrdy.o2_home_assignment.core.components.ScreenTitleContent
import com.jozeftvrdy.o2_home_assignment.core.components.WeightSpacer
import com.jozeftvrdy.o2_home_assignment.core.navigation.Navigation
import com.jozeftvrdy.o2_home_assignment.core.navigation.Screen
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navigation: Navigation,
    viewModel: MainScreenViewModel = koinViewModel()
) {

    val state by viewModel.scratchCardUiState.collectAsStateWithLifecycle()

    MainScreen(
        uiState = state,
        navigateToScratch = {
            navigation.navigateTo(Screen.ScratchScreen)
        },
        navigateToRegister = {
            navigation.navigateTo(Screen.RegistrationScreen)
        }
    )

}

@Composable
private fun MainScreen(
    uiState: ScratchCardUiState,
    navigateToScratch: () -> Unit,
    navigateToRegister: () -> Unit
) {
    ScreenTitleContent(
        title = uiState.title,
        text = uiState.text
    ) {
        WeightSpacer()

        MainScreenButtons(
            uiState = uiState,
            navigateToScratch = navigateToScratch,
            navigateToRegister = navigateToRegister
        )
    }
}

@Composable
private fun MainScreenButtons(
    uiState: ScratchCardUiState,
    navigateToScratch: () -> Unit,
    navigateToRegister: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val scratchButtonEnabled = uiState is ScratchCardUiState.Hidden
        val registerButtonEnabled = uiState is ScratchCardUiState.Revealed

        Button(
            onClick = navigateToScratch,
            enabled = scratchButtonEnabled
        ) {
            ButtonText(
                text = stringResource(id = R.string.main_screen_to_scratch_screen),
                enabled = scratchButtonEnabled
            )
        }

        Button(
            onClick = navigateToRegister,
            enabled = registerButtonEnabled
        ) {
            ButtonText(
                text = stringResource(id = R.string.main_screen_to_registration_screen),
                enabled = registerButtonEnabled
            )
        }
    }
}