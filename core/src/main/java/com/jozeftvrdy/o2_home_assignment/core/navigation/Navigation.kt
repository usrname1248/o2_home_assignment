package com.jozeftvrdy.o2_home_assignment.core.navigation

interface Navigation {
    fun navigateTo(screen: Screen)
    fun popBackTo(screen: Screen)
}