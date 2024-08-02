package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.registration

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jozeftvrdy.o2_home_assignment.core.components.ButtonText
import com.jozeftvrdy.o2_home_assignment.core.components.ScreenTitleContent
import com.jozeftvrdy.o2_home_assignment.core.components.Spacer
import com.jozeftvrdy.o2_home_assignment.core.components.WeightSpacer
import com.jozeftvrdy.o2_home_assignment.core.navigation.Navigation
import com.jozeftvrdy.o2_home_assignment.core.ui.animated.AnimatedDisabledButton
import com.jozeftvrdy.o2_home_assignment.core.ui.animated.AnimatedDisabledButtonState
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import org.koin.androidx.compose.koinViewModel

private data class RegistrationScreenAnimatedButtonState(
    override val isButtonEnabled: Boolean,
    val isLoading: Boolean
) : AnimatedDisabledButtonState

@Composable
fun RegistrationScreen(
    navigation: Navigation,
    viewModel: RegistrationViewModel = koinViewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {
    val cardState by viewModel.scratchCardUiState.collectAsStateWithLifecycle()

    val isRegistered = remember(cardState) {
        cardState is ScratchCardUiState.Revealed.Registered
    }
    val isLoading = remember(cardState) {
        cardState is ScratchCardUiState.Revealed.Registering
    }

    val animatedButtonState = remember(isRegistered, isLoading) {
        RegistrationScreenAnimatedButtonState(
            isButtonEnabled = !isLoading && !isRegistered,
            isLoading = isLoading
        )
    }

    RegistrationScreen(
        buttonState = animatedButtonState,
        title = if (isRegistered) {
            stringResource(id = R.string.registration_screen_done_title)
        } else {
            stringResource(id = R.string.registration_screen_title)
        },
        text = if (isRegistered) {
            stringResource(id = R.string.registration_screen_done_text)
        } else {
            stringResource(id = R.string.registration_screen_text)
        },
        onRegisterClicked = {
            viewModel.register()
        },
        onBackClicked = {
            navigation.popBack()
        }
    )
}

@Composable
private fun RegistrationScreen(
    buttonState: RegistrationScreenAnimatedButtonState,
    title: String,
    text: String,
    onBackClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    ScreenTitleContent(
        title = title,
        text = text,
        onBackClicked = onBackClicked
    ) {
        WeightSpacer()

        AnimatedDisabledButton(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            state = buttonState,
            onClick = onRegisterClicked
        ) { animatedButtonState ->
            ButtonText(
                text = stringResource(id = R.string.registration_screen_btn),
                enabled = animatedButtonState.isButtonEnabled
            )

            if (animatedButtonState.isLoading) {
                Spacer(rawSize = 8)

                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}