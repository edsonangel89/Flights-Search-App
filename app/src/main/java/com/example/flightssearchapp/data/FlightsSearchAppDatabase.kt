package com.example.flightssearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.flightssearchapp.dao.AirportDao
import com.example.flightssearchapp.dao.FavoriteDao
import com.example.flightssearchapp.models.Airport
import com.example.flightssearchapp.models.Favorite
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightsSearchAppDatabase : RoomDatabase() {

    abstract fun airportDao() : AirportDao

    abstract fun favoriteDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FlightsSearchAppDatabase? = null

        fun getDatabase(context: Context) : FlightsSearchAppDatabase {
            return INSTANCE ?: synchronized(this) {
                 val instance =
                     Room.databaseBuilder(context.applicationContext, FlightsSearchAppDatabase::class.java, "flights")
                         .createFromAsset("database/flight_search.db")
                         .build()

                INSTANCE = instance
                         instance
            }
        }
    }
}