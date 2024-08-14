package com.example.flightssearchapp.models

data class Flight(
    val id: Int,
    val departure: Airport,
    val arrive: Airport,
    var likeState: Boolean = false
)
