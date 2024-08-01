package com.jozeftvrdy.o2_home_assignment.feature.scratch.navigation

import androidx.navigation.NavGraphBuilder
import com.jozeftvrdy.o2_home_assignment.core.extension.composable
import com.jozeftvrdy.o2_home_assignment.core.navigation.Navigation
import com.jozeftvrdy.o2_home_assignment.core.navigation.Screen
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.main.MainScreen
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.registration.RegistrationScreen
import com.jozeftvrdy.o2_home_assignment.feature.scratch.ui.screen.scratch.ScratchScreen

fun NavGraphBuilder.scratchNavigation(
    navigation: Navigation
) {
    composable(Screen.MainScratchScreen) {
        MainScreen(navigation = navigation)
    }

    composable(Screen.ScratchScreen) {
        ScratchScreen(
            navigation = navigation,
        )
    }

    composable(Screen.RegistrationScreen) {
        RegistrationScreen(
            navigation = navigation
        )
    }
}