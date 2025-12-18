package com.sample.prak12.view.uicontroler

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sample.prak12.view.route.DestinasiEntry
import com.sample.prak12.view.route.DestinasiHome

@Composable
fun DataSiswaApp(navController: NavHostController = rememberNavController(),
                 modifier: Modifier
) {
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(navController: NavHostController,
                 modifier: Modifier = Modifier) {
    NavHost(navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier) {
        composable(DestinasiHome.route) {
            HomeScreen(navigateToItemEntry = {
                navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = {
//                    navController.navigate("${DestinasiEntry.route}/${it}")
                })
        }
        composable(DestinasiEntry.route) {
//            EntrySiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route)})
        }
    }
}
