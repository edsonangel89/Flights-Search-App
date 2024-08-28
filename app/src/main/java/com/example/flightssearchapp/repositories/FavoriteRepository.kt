package com.example.flightssearchapp.repositories

import com.example.flightssearchapp.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun getAllFavorites(): List<Favorite>

    suspend fun insertFavorite(favorite: Favorite)

}