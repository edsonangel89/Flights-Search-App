package com.example.flightssearchapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flightssearchapp.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorites(): List<Favorite>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

}