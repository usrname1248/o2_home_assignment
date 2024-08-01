package com.jozeftvrdy.o2_home_assignment.di

import com.jozeftvrdy.o2_home_assignment.data.di.dataModule
import org.koin.core.module.Module

val allModules = listOf<Module>(
    dataModule
)