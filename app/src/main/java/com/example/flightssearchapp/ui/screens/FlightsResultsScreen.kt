package com.example.flightssearchapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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