package com.example.flightssearchapp.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambdaN
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flightssearchapp.R
import com.example.flightssearchapp.ui.screens.SearchScreen
import com.example.flightssearchapp.ui.screens.SearchViewModel

@Composable
fun FlightsApp(
    vm: SearchViewModel = viewModel(factory = SearchViewModel.factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Scaffold { innerPaddings ->
                innerPaddings
                SearchScreen(
                    label = R.string.search_label,
                    value = vm.airId,
                    onValueChange = {
                        vm.updateAirId(it)
                        vm.getAirports(it)
                    },
                    navigate = { navController.navigate("home") },
                    vm = vm
                )
            }
        }
        /*composable("flights") {
            Scaffold { innerPaddings ->
                innerPaddings
                SearchScreen(
                    label = R.string.search_label,
                    value = vm.airId,
                    onValueChange = {
                        vm.updateAirId(it)
                        vm.getAirports(it)
                    },
                    vm = vm
                )
            }
        }*/
    }
}