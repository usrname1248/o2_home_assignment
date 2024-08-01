package com.jozeftvrdy.o2_home_assignment.core.navigation

sealed class Screen(
    val route: String
) {
    data object MainScratchScreen : Screen("MainScreen")

    data object ScratchScreen : Screen("MainScreen/Scratch")

    data object RegistrationScreen : Screen("MainScreen/Registration")
}