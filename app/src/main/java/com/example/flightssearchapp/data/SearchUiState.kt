package com.example.flightssearchapp.data

import com.example.flightssearchapp.models.Airport

data class SearchUiState(
    val posibleFlights: List<Pair<Airport, Airport>> = listOf()
)
