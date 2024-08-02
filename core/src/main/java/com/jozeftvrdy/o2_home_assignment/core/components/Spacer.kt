package com.jozeftvrdy.o2_home_assignment.core.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spacer(rawSize: Int) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(rawSize.dp))
}

@Composable
fun RowScope.WeightSpacer(weight: Float = 1f, fill: Boolean = true) {
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier.weight(
            weight = weight,
            fill = fill
        )
    )
}

@Composable
fun ColumnScope.WeightSpacer(weight: Float = 1f, fill: Boolean = true) {
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier.weight(
            weight = weight,
            fill = fill
        )
    )
}