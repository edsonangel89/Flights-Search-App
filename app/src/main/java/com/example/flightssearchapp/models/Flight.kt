package com.example.flightssearchapp.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Flight(
    val id: Int,
    val departure: Airport,
    val arrive: Airport,
    val likeState: MutableState<Boolean> = mutableStateOf(false)
)
