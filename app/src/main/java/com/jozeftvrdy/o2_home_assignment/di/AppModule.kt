package com.jozeftvrdy.o2_home_assignment.di

import com.jozeftvrdy.o2_home_assignment.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainActivityViewModel(
            getScratchCardFlowUseCase = get()
        )
    }
}