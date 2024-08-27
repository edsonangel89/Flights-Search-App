package com.example.flightssearchapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flightssearchapp.R
import com.example.flightssearchapp.ui.screens.FlightResults
import com.example.flightssearchapp.ui.screens.SearchScreen
import com.example.flightssearchapp.ui.screens.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                    navigate = { navController.navigate("flights") },
                    vm = vm
                )
            }
        }
        composable("flights") {
            Scaffold (
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate("home") }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                },
                content = {
                    it
                    FlightResults(
                        optionsList = vm.posibleFlightsList,
                        navHostController = navController,
                        vm = vm,
                        modifier = modifier.padding(it)
                    )
                }/*,
                modifier = Modifier.padding(8.dp)*/
            )
        }
    }
}