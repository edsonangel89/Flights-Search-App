package com.example.flightssearchapp.repositories

import com.example.flightssearchapp.dao.AirportDao
import com.example.flightssearchapp.models.Airport
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(private val airportDao: AirportDao) : AirportRepository {

    override suspend fun getAllAirports(): List<Airport> = airportDao.getAllAirports()

    override suspend fun getAirports(search: String): List<Airport> = airportDao.getAirports(search)

}