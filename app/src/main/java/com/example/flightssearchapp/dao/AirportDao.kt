package com.example.flightssearchapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightssearchapp.models.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport")
    suspend fun getAllAirports(): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code LIKE :search OR name LIKE :search ORDER BY iata_code")
    suspend fun getAirports(search: String): List<Airport>

}