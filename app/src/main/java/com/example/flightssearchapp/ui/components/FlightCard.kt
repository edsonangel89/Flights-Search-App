package com.example.flightssearchapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.flightssearchapp.models.Flight
import com.example.flightssearchapp.ui.screens.SearchViewModel

@Composable
fun FlightCardsList(
    flightList: MutableList<Flight>,
    flightStates: MutableList<Boolean>,
    vm: SearchViewModel,
    modifier: Modifier
) {

            LazyColumn(
                modifier = modifier
            ) {
                itemsIndexed(flightList) { index, flight ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, Color.LightGray, RectangleShape)
                        ) {
                            Column (
                                modifier = Modifier.fillMaxWidth(0.7f)
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
                                IconButton(
                                    onClick = { vm.updateState(flight.id) }
                                ) {
                                    if (flight.likeState) {
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
                        }
                }
            }
}

@Composable
fun FlightCard(
    flight: Flight,
    vm: SearchViewModel
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
        IconButton(
            onClick = { vm.updateState(flight.id) },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            if(flight.likeState) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
            else {
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