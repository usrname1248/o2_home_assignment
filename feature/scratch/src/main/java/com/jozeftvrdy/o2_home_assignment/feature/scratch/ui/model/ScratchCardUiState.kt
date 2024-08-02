package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import com.jozeftvrdy.o2_home_assignment.feature.scratch.R

sealed class ScratchCardUiState {
    abstract val mainScreenTitle: String
        @Composable get

    abstract val mainScreenText: String
        @Composable get

    val scratchButtonEnabled: Boolean
        get() = this is Unrevealed
    val registrationButtonEnabled: Boolean
        get() = this is Revealed && this !is Revealed.Registered


    data object Loading : ScratchCardUiState() {
        override val mainScreenTitle: String
            @Composable get() = stringResource(id = R.string.scratch_card_loading_title)

        override val mainScreenText: String
            @Composable get() = stringResource(id = R.string.scratch_card_loading_text)

    }

    sealed class Unrevealed : ScratchCardUiState() {
        data object NotScratched : Unrevealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_not_scratched_title)

            override val mainScreenText: String
                @Composable get() = stringResource(id = R.string.scratch_card_not_scratched_text)
        }

        data object Scratching : Unrevealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_scratching_title)

            override val mainScreenText: String
                @Composable get() = stringResource(id = R.string.scratch_card_scratching_text)
        }

        data object ScratchFailed : Unrevealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_scratch_failed_title)

            override val mainScreenText: String
                @Composable get() = stringResource(id = R.string.scratch_card_scratch_failed_text)
        }
    }

    sealed class Revealed : ScratchCardUiState() {

        abstract val generatedUUID: String

        data class Unregistered(
            override val generatedUUID: String
        ) : Revealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_revealed_title)

            override val mainScreenText: String
                @Composable get() = stringResource(
                    id = R.string.scratch_card_revealed_text,
                    generatedUUID
                )
        }

        data class Registering(
            override val generatedUUID: String
        ) : Revealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_registration_in_progress_title)

            override val mainScreenText: String
                @Composable get() = stringResource(
                    id = R.string.scratch_card_registration_in_progress_text,
                    generatedUUID
                )
        }

        data class Registered(
            override val generatedUUID: String
        ) : Revealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_registered_title)

            override val mainScreenText: String
                @Composable get() = stringResource(
                    id = R.string.scratch_card_registered_text,
                    generatedUUID
                )
        }

        data class RegistrationFailed(
            override val generatedUUID: String
        ) : Revealed() {
            override val mainScreenTitle: String
                @Composable get() = stringResource(id = R.string.scratch_card_registration_failed_title)

            override val mainScreenText: String
                @Composable get() = stringResource(
                    id = R.string.scratch_card_registration_failed_text,
                    generatedUUID
                )
        }
    }
}

fun CardStateModel.toUiModel(): ScratchCardUiState = when (this) {
    CardStateModel.Unrevealed.Initial -> ScratchCardUiState.Unrevealed.NotScratched
    CardStateModel.Unrevealed.Scratching -> ScratchCardUiState.Unrevealed.Scratching
    CardStateModel.Unrevealed.ScratchingFailed -> ScratchCardUiState.Unrevealed.ScratchFailed
    is CardStateModel.Revealed.Unregistered -> ScratchCardUiState.Revealed.Unregistered(
        generatedUUID = this.generatedUUID.toString()
    )

    is CardStateModel.Revealed.Registering -> ScratchCardUiState.Revealed.Registering(
        generatedUUID = this.generatedUUID.toString()
    )

    is CardStateModel.Revealed.Registered -> ScratchCardUiState.Revealed.Registered(
        generatedUUID = this.generatedUUID.toString()
    )

    is CardStateModel.Revealed.RegisterFailed -> ScratchCardUiState.Revealed.RegistrationFailed(
        generatedUUID = this.generatedUUID.toString()
    )

}