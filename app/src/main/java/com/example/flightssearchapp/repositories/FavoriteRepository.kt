package com.example.flightssearchapp.repositories

import com.example.flightssearchapp.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getAllFavorites(): Flow<List<Favorite>>

    suspend fun insertFavorite(favorite: Favorite)

}