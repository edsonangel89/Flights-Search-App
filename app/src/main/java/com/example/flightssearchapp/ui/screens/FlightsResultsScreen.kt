package com.example.flightssearchapp.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flightssearchapp.models.Favorite
import com.example.flightssearchapp.models.Flight

@Composable
fun FlightResults(
    optionsList: List<Flight>,
    vm: SearchViewModel,
    navHostController: NavHostController,
    modifier: Modifier
) {
    var posibleFlights = vm.posibleFlightsList.value
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

@Composable
fun FlightCardsList(
    flightList: MutableList<Flight>,
    flightStates: MutableList<Boolean>,
    vm: SearchViewModel = viewModel(),
    modifier: Modifier
) {
    var posibleFlights = vm.posibleFlightsList.value

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(posibleFlights) { index, flight ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.LightGray, RectangleShape)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(4.dp)
                ){
                    Text(text = flight.departure.iataCode)
                    Text(text = flight.departure.name)
                    Text(text = flight.arrive.iataCode)
                    Text(text = flight.arrive.name)
                }
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    if (flight.likeState.value) {
                        IconButton(
                            onClick = {
                                vm.updateState(flight.id)
                                val favorite = Favorite(departure = flight.departure.iataCode, destination = flight.arrive.iataCode)
                                /*vm.updateFavorite(favorite)*/
                                vm.deleteFavorite(favorite)
                                vm.posibleFlightsList.value = posibleFlights
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                Log.d("ONCLICK", "TEST")
                                vm.updateState(flight.id)
                                Log.d("ONCLICK", "TEST")
                                val favorite = Favorite(departure = flight.departure.iataCode, destination = flight.arrive.iataCode)
                                Log.d("ONCLICK", "TEST")
                                Log.d("FLIGHT ID", vm._favList.value.size.plus(1).toString())
                                vm.updateFavorite(favorite)
                                vm.posibleFlightsList.value = posibleFlights
                                Log.d("ONCLICK", "TEST")
                            }
                        ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                    }
                }
            }
        }
    }
}

@Composable
fun FlightCard(
    flight: Flight,
    vm: SearchViewModel = viewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            /*.padding(2.dp)*/
            .border(1.dp, Color.LightGray, RectangleShape)
    ) {
        Column {
            Text(text = flight.departure.iataCode)
            Text(text = flight.departure.name)
            Text(text = flight.arrive.iataCode)
            Text(text = flight.arrive.name)
        }
        if(flight.likeState.value) {
            IconButton(
                onClick = { vm.updateState(flight.id) },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        }
        else {
            IconButton(
                onClick = { vm.updateState(flight.id) },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun FlightCardState(
    flightState: Boolean,
    changeState: () -> Unit
) {
    IconButton(
        onClick = changeState,
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        if (flightState) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}