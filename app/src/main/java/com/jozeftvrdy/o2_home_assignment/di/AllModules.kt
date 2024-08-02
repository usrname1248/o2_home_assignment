package com.jozeftvrdy.o2_home_assignment.di

import com.jozeftvrdy.networking.di.apiModule
import com.jozeftvrdy.networking.di.networkModule
import com.jozeftvrdy.o2_home_assignment.data.di.dataModule
import com.jozeftvrdy.o2_home_assignment.domain.di.domainModule
import com.jozeftvrdy.o2_home_assignment.feature.scratch.di.scratchFeatureModule

val allModules = listOf(
    appModule,
    scratchFeatureModule,
    domainModule,
    dataModule,
    networkModule,
    apiModule
)