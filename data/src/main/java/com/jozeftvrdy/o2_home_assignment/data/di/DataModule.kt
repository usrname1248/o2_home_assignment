package com.jozeftvrdy.o2_home_assignment.data.di

import com.jozeftvrdy.o2_home_assignment.data.repository.ScratchRepository
import com.jozeftvrdy.o2_home_assignment.data.repository.ScratchRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<ScratchRepository> {
        ScratchRepositoryImpl(
            versionApi = get()
        )
    }
}