package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jozeftvrdy.o2_home_assignment.core.components.ButtonText
import com.jozeftvrdy.o2_home_assignment.core.components.ScreenTitleContent
import com.jozeftvrdy.o2_home_assignment.core.components.WeightSpacer
import com.jozeftvrdy.o2_home_assignment.core.navigation.Navigation
import com.jozeftvrdy.o2_home_assignment.core.navigation.Screen
import com.jozeftvrdy.o2_home_assignment.core.ui.animated.AnimatedDisabledButton
import com.jozeftvrdy.o2_home_assignment.core.ui.animated.AnimatedDisabledButtonState
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import org.koin.androidx.compose.koinViewModel

private data class AnimatedDisabledButtonStateImpl(
    override val isButtonEnabled: Boolean
) : AnimatedDisabledButtonState

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
        title = uiState.mainScreenTitle,
        text = uiState.mainScreenText
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
    val scratchButtonState = remember(uiState.scratchButtonEnabled) {
        AnimatedDisabledButtonStateImpl(
            isButtonEnabled = uiState.scratchButtonEnabled
        )
    }

    val registerButtonState = remember(uiState.registrationButtonEnabled) {
        AnimatedDisabledButtonStateImpl(
            isButtonEnabled = uiState.registrationButtonEnabled
        )
    }

    MainScreenButtons(
        scratchButtonState = scratchButtonState,
        registerButtonState = registerButtonState,
        navigateToScratch = navigateToScratch,
        navigateToRegister = navigateToRegister
    )
}

@Composable
private fun MainScreenButtons(
    scratchButtonState: AnimatedDisabledButtonStateImpl,
    registerButtonState: AnimatedDisabledButtonStateImpl,
    navigateToScratch: () -> Unit,
    navigateToRegister: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AnimatedDisabledButton(
            state = scratchButtonState,
            onClick = navigateToScratch
        ) { animatedButtonState ->
            ButtonText(
                text = stringResource(id = R.string.main_screen_to_scratch_screen),
                enabled = animatedButtonState.isButtonEnabled
            )
        }


        AnimatedDisabledButton(
            state = registerButtonState,
            onClick = navigateToRegister
        ) { animatedButtonState ->
            ButtonText(
                text = stringResource(id = R.string.main_screen_to_registration_screen),
                enabled = animatedButtonState.isButtonEnabled
            )
        }
    }
}