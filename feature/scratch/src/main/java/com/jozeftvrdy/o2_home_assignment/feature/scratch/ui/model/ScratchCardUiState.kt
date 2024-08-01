package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R

sealed interface ScratchCardUiState {
    val title: String
        @Composable get

    val text: String
        @Composable get

    data object Loading: ScratchCardUiState {
        override val title: String
            @Composable get() = stringResource(id = R.string.scratch_card_loading_title)

        override val text: String
            @Composable get() = stringResource(id = R.string.scratch_card_loading_text)

    }

    data object Hidden: ScratchCardUiState {
        override val title: String
            @Composable get() = stringResource(id = R.string.scratch_card_unrevealed_title)

        override val text: String
            @Composable get() = stringResource(id = R.string.scratch_card_unrevealed_text)
    }

    sealed class Revealed: ScratchCardUiState {
        abstract val generatedUUID: String

        data class Unregistered(
            override val generatedUUID: String
        ): Revealed() {
            override val title: String
                @Composable get() = stringResource(id = R.string.scratch_card_revealed_title)

            override val text: String
                @Composable get() = stringResource(id = R.string.scratch_card_revealed_text, generatedUUID)
        }

        data class Registered(
            override val generatedUUID: String
        ): Revealed() {
            override val title: String
                @Composable get() = stringResource(id = R.string.scratch_card_registered_title)

            override val text: String
                @Composable get() = stringResource(id = R.string.scratch_card_registered_text, generatedUUID)
        }

        data class RegistrationFailed(
            override val generatedUUID: String
        ): Revealed() {
            override val title: String
                @Composable get() = stringResource(id = R.string.scratch_card_registration_failed_title)

            override val text: String
                @Composable get() = stringResource(id = R.string.scratch_card_registration_failed_text, generatedUUID)
        }
    }
}

fun CardStateModel.toUiModel(): ScratchCardUiState = when (this) {
    CardStateModel.Initial -> ScratchCardUiState.Hidden
    is CardStateModel.Revealed.Registered -> ScratchCardUiState.Revealed.Registered(
        generatedUUID = this.generatedUUID.toString()
    )
    is CardStateModel.Revealed.Unregistered -> ScratchCardUiState.Revealed.Unregistered(
        generatedUUID = this.generatedUUID.toString()
    )
    is CardStateModel.Revealed.RegisterFailed -> ScratchCardUiState.Revealed.RegistrationFailed(
        generatedUUID = this.generatedUUID.toString()
    )
}