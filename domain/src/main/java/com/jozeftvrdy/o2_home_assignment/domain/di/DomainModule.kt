package com.jozeftvrdy.o2_home_assignment.domain.di

import com.jozeftvrdy.o2_home_assignment.domain.scratchCard.GetScratchCardFlowUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetScratchCardFlowUseCase(
            repository = get()
        )
    }
}