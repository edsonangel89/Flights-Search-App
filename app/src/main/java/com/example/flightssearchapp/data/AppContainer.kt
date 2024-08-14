package com.example.flightssearchapp.data

import android.content.Context
import com.example.flightssearchapp.repositories.AirportRepository
import com.example.flightssearchapp.repositories.FavoriteRepository
import com.example.flightssearchapp.repositories.OfflineAirportRepository
import com.example.flightssearchapp.repositories.OfflineFavoriteRepository

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val airportRepository: AirportRepository = OfflineAirportRepository(FlightsSearchAppDatabase.getDatabase(context).airportDao())

    override val favoriteRepository: FavoriteRepository = OfflineFavoriteRepository(FlightsSearchAppDatabase.getDatabase(context).favoriteDao())


}