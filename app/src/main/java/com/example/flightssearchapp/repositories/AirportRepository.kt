package com.example.flightssearchapp.repositories

import com.example.flightssearchapp.models.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    suspend fun getAllAirports(): List<Airport>

    suspend fun getAirports(search: String): List<Airport>

}