package com.jozeftvrdy.o2_home_assignment.feature.scratch.di

import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.main.MainScreenViewModel
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.registration.RegistrationViewModel
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.scratch.ScratchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scratchFeatureModule = module {
    viewModel {
        MainScreenViewModel(
            getScratchCardFlowUseCase = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        ScratchViewModel(
            scratchRepository = get(),
            getScratchCardFlowUseCase = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        RegistrationViewModel(
            scratchRepository = get(),
            getScratchCardFlowUseCase = get(),
            savedStateHandle = get(),
        )
    }
}