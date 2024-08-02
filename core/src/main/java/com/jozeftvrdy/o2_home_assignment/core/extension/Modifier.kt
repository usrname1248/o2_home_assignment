package com.jozeftvrdy.o2_home_assignment.core.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.conditional(
    conditionResult: Boolean,
    conditionalCallBack: @Composable Modifier.() -> Modifier
) = if (conditionResult) {
    this.then(
        Modifier.run {
            conditionalCallBack()
        }
    )
} else Modifier

@Composable
fun Modifier.nonRippleClick(
    onClick: () -> Unit
): Modifier = this.clickable(
    onClick = onClick,
    interactionSource = remember { MutableInteractionSource() },
    indication = null
)
