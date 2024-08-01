package com.jozeftvrdy.o2_home_assignment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.jozeftvrdy.o2_home_assignment.core.navigation.NavigationImpl
import com.jozeftvrdy.o2_home_assignment.feature.scratch.navigation.scratchNavigation

fun NavGraphBuilder.navigationContent(navController: NavController) {
    val navigation =
        NavigationImpl(navController = navController)

    scratchNavigation(
        navigation = navigation
    )
}

