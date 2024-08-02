package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.registration

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    navigation: Navigation,
    viewModel: RegistrationViewModel = koinViewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {
    val cardState by viewModel.scratchCardUiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = cardState) {
        if (cardState is ScratchCardUiState.Revealed.Registered) {
            navigation.popBack()
        }
    }

    RegistrationScreen(
        isLoading = cardState is ScratchCardUiState.Revealed.Registering,
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
    isLoading: Boolean,
    onBackClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    ScreenTitleContent(
        title = stringResource(id = R.string.registration_screen_title),
        text = stringResource(id = R.string.registration_screen_text),
        onBackClicked = onBackClicked
    ) {
        WeightSpacer()

        Button(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            onClick = onRegisterClicked,
            enabled = !isLoading
        ) {
            ButtonText(
                text = stringResource(id = R.string.registration_screen_btn),
                enabled = !isLoading
            )

            Spacer(rawSize = 2)

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}