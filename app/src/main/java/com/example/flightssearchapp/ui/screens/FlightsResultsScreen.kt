package com.example.flightssearchapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.flightssearchapp.models.Flight
import com.example.flightssearchapp.ui.components.FlightCardsList

@Composable
fun FlightResults(
    optionsList: List<Flight>,
    vm: SearchViewModel,
    navHostController: NavHostController,
    modifier: Modifier
) {
    var posibleFlights = vm.posibleFlightsList
    var iconStates by remember { mutableStateOf(vm.flightStates) }

    if (optionsList.isNotEmpty()) {
        FlightCardsList(
            flightList = posibleFlights,
            flightStates = iconStates,
            vm = vm,
            modifier = modifier
        )
    }
}