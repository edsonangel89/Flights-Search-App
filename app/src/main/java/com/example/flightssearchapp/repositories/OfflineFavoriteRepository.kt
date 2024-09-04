package com.example.flightssearchapp.repositories

import com.example.flightssearchapp.dao.FavoriteDao
import com.example.flightssearchapp.models.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    override suspend fun getAllFavorites(): List<Favorite> = favoriteDao.getAllFavorites()

    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)

}