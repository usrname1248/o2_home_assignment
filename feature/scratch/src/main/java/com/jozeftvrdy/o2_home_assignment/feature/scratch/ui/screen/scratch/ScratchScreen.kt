package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.scratch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jozeftvrdy.o2_home_assignment.core.navigation.Navigation

@Composable
fun ScratchScreen(
    navigation: Navigation
) {
    Scaffold { scaffoldPadding ->
        Box(
            modifier = Modifier.padding(scaffoldPadding),
        ) {
            Text(
                "mainScreen"
            )
        }
    }
}