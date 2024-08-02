package com.jozeftvrdy.o2_home_assignment.data.repository

import com.jozeftvrdy.networking.api.VersionApi
import com.jozeftvrdy.networking.model.ifSuccess
import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

private const val revealingOperationMillis: Long = 3000
private const val androidValidationValue = 277028

class ScratchRepository(
    private val versionApi: VersionApi
) {
    private val mutableCardStateFlow: MutableStateFlow<CardStateModel> =
        MutableStateFlow(CardStateModel.Unrevealed.Initial)
    val cardStateFlow: StateFlow<CardStateModel>
        get() = mutableCardStateFlow


    suspend fun revealCard() {
        val currentValue = mutableCardStateFlow.value
        assert(currentValue is CardStateModel.Unrevealed.Initial || currentValue is CardStateModel.Unrevealed.ScratchingFailed)

        mutableCardStateFlow.emit(
            CardStateModel.Unrevealed.Scratching
        )

        kotlin.runCatching {
            val generatedUUID: UUID = UUID.randomUUID()
            delay(revealingOperationMillis)
            generatedUUID
        }.onFailure {
            mutableCardStateFlow.emit(
                CardStateModel.Unrevealed.ScratchingFailed
            )
        }.onSuccess {
            mutableCardStateFlow.emit(
                CardStateModel.Revealed.Unregistered(
                    generatedUUID = it
                )
            )
        }

    }

    @Throws(IllegalStateException::class)
    suspend fun registerCard() {
        val currentState = mutableCardStateFlow.value
        if (currentState !is CardStateModel.Revealed) {
            throw IllegalStateException()
        }

        mutableCardStateFlow.emit(
            CardStateModel.Revealed.Registering(
                generatedUUID = currentState.generatedUUID
            )
        )

        kotlin.runCatching {
            versionApi.getVersion(currentState.generatedUUID).ifSuccess { success ->
                    if (success.android <= androidValidationValue) {
                        throw ValidationException()
                    }

                    return@runCatching mutableCardStateFlow.emit(
                        CardStateModel.Revealed.Registered(
                            generatedUUID = currentState.generatedUUID
                        )
                    )
                }

                throw GeneralException()
        }.onFailure {
            mutableCardStateFlow.emit(
                CardStateModel.Revealed.RegisterFailed(
                    generatedUUID = currentState.generatedUUID
                )
            )
        }
    }

    class ValidationException: Exception()
    class GeneralException: Exception()
}