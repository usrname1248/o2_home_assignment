package com.jozeftvrdy.o2_home_assignment.data.repository

import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import kotlinx.coroutines.flow.StateFlow

private const val revealingOperationMillis: Long = 3000
private const val androidValidationValue = 277028

interface ScratchRepository {

    val cardStateFlow: StateFlow<CardStateModel>

    suspend fun revealCard()

    @Throws(IllegalStateException::class)
    suspend fun registerCard()

    class ValidationException: Exception()
    class GeneralException: Exception()
}