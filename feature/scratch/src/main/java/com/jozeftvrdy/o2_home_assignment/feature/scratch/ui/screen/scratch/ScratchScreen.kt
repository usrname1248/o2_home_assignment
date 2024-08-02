package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.scratch

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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

private data class ScratchScreenAnimatedButtonState(
    override val isButtonEnabled: Boolean,
    val isLoading: Boolean
) : AnimatedDisabledButtonState

@Composable
fun ScratchScreen(
    navigation: Navigation,
    viewModel: ScratchViewModel = koinViewModel()
) {
    val cardState by viewModel.scratchCardUiState.collectAsStateWithLifecycle()

    val isScratched = remember(cardState) {
        cardState is ScratchCardUiState.Revealed.Unregistered
    }
    val isLoading = remember(cardState) {
        cardState is ScratchCardUiState.Unrevealed.Scratching
    }

    val animatedButtonState = remember(isScratched, isLoading) {
        ScratchScreenAnimatedButtonState(
            isButtonEnabled = !isLoading && !isScratched,
            isLoading = isLoading
        )
    }

    ScratchScreen(
        buttonState = animatedButtonState,
        title = if (isScratched) {
            stringResource(id = R.string.scratch_screen_done_title)
        } else {
            stringResource(id = R.string.scratch_screen_title)
        },
        text = if (isScratched) {
            stringResource(id = R.string.scratch_screen_done_text)
        } else {
            stringResource(id = R.string.scratch_screen_text)
        },
        onScratchClicked = {
            viewModel.scratch()
        },
        onBackClicked = {
            navigation.popBack()
        }
    )
}

@Composable
private fun ScratchScreen(
    buttonState: ScratchScreenAnimatedButtonState,
    title: String,
    text: String,
    onBackClicked: () -> Unit,
    onScratchClicked: () -> Unit
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
            onClick = onScratchClicked
        ) { buttonAnimatedState ->
            ButtonText(
                text = stringResource(id = R.string.scratch_screen_btn),
                enabled = buttonAnimatedState.isButtonEnabled
            )

            if (buttonAnimatedState.isLoading) {
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