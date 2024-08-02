package com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.scratch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozeftvrdy.o2_home_assignment.data.repository.ScratchRepository
import com.jozeftvrdy.o2_home_assignment.domain.scratchCard.GetScratchCardFlowUseCase
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.ScratchCardUiState
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.model.toUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScratchViewModel(
    val scratchRepository: ScratchRepository,
    getScratchCardFlowUseCase: GetScratchCardFlowUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val scratchCardUiState: StateFlow<ScratchCardUiState> = getScratchCardFlowUseCase().mapLatest { repoModel ->
        repoModel.toUiModel()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
        initialValue = ScratchCardUiState.Loading
    )

    fun scratch() {
        viewModelScope.launch {
            scratchRepository.revealCard()
        }
    }
}