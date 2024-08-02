package com.jozeftvrdy.o2_home_assignment.core.ui.animated

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

interface AnimatedDisabledButtonState {
    val isButtonEnabled: Boolean
}

@Composable
fun <T : AnimatedDisabledButtonState> AnimatedDisabledButton(
    modifier: Modifier = Modifier,
    state: T,
    onClick: () -> Unit,
    buttonContent: @Composable RowScope.(T) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state,
        contentAlignment = Alignment.Center,
        transitionSpec = {
            fadeIn().togetherWith(
                fadeOut()
            )
        }
    ) { animatedState ->
        Button(
            modifier = Modifier,
            onClick = onClick,
            enabled = animatedState.isButtonEnabled
        ) {
            buttonContent(
                animatedState
            )
        }
    }
}