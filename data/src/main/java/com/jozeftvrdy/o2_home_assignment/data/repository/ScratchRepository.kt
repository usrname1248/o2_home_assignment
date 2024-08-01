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
        MutableStateFlow(CardStateModel.Initial)
    val cardStateFlow: StateFlow<CardStateModel>
        get() = mutableCardStateFlow


    suspend fun revealCard() {
        assert(mutableCardStateFlow.value is CardStateModel.Initial)

        val generatedUUID: UUID = UUID.randomUUID()

        delay(revealingOperationMillis)

        mutableCardStateFlow.emit(
            CardStateModel.Revealed.Unregistered(
                generatedUUID = generatedUUID
            )
        )
    }

    suspend fun registerCard(): Result<Unit> = kotlin.runCatching {
        val currentState = mutableCardStateFlow.value
        return if (currentState is CardStateModel.Revealed.Unregistered) {
            versionApi.getVersion(currentState.generatedUUID).ifSuccess { success ->
                if (success.android <= androidValidationValue) {
                    throw ValidationException()
                }

                mutableCardStateFlow.emit(
                    CardStateModel.Revealed.Registered(
                        generatedUUID = currentState.generatedUUID
                    )
                )

                return@runCatching
            }

            throw GeneralException()
        } else {
            Result.failure(
                IllegalStateException()
            )
        }
    }

    class ValidationException: Exception()
    class GeneralException: Exception()
}