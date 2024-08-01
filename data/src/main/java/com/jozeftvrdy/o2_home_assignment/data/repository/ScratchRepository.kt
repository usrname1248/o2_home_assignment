package com.jozeftvrdy.o2_home_assignment.data.repository

import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

private const val revealingOperationMillis: Long = 3000

class ScratchRepository {
    private val mutableCardStateFlow: MutableStateFlow<CardStateModel> = MutableStateFlow(CardStateModel.Initial)
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

    suspend fun registerCard() {
        val currentState = mutableCardStateFlow.value
        if ( currentState is CardStateModel.Revealed.Unregistered) {
            delay(revealingOperationMillis)

            mutableCardStateFlow.emit(
                CardStateModel.Revealed.Registered(
                    generatedUUID = currentState.generatedUUID
                )
            )
        } else {
            assert(false)
        }
    }
}