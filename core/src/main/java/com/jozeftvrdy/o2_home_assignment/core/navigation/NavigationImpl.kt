package com.jozeftvrdy.o2_home_assignment.core.navigation

import androidx.navigation.NavController

class NavigationImpl(private val navController: NavController) : Navigation {
    override fun navigateTo(screen: Screen) {
        navController.navigate(route = screen.route)
    }

    override fun popBackTo(screen: Screen) {
        navController.popBackStack(route = screen.route, inclusive = true)
    }
}