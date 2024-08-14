package com.example.flightssearchapp.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightssearchapp.R
import com.example.flightssearchapp.ui.screens.SearchViewModel
import com.example.flightssearchapp.ui.screens.SearchScreen

@Composable
fun FlightsApp(
    vm: SearchViewModel = viewModel(factory = SearchViewModel.factory),
    modifier: Modifier = Modifier
) {
    /*val aList = vm.airportList.collectAsState().value*/

    Scaffold { innerPaddings ->
        innerPaddings
        SearchScreen(
            label = R.string.search_label,
            value = vm.airId,
            onValueChange = {
                vm.updateAirId(it)
                vm.getAirports(it)
                            },
            /*modifier = Modifier.padding(innerPaddings),*/
            vm = vm
        )
    }
}