package com.jozeftvrdy.o2_home_assignment.domain.scratchCard

import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import com.jozeftvrdy.o2_home_assignment.data.repository.ScratchRepository
import kotlinx.coroutines.flow.StateFlow

class GetScratchCardFlowUseCase(
    private val repository: ScratchRepository
) {
    operator fun invoke() : StateFlow<CardStateModel> = repository.cardStateFlow
}