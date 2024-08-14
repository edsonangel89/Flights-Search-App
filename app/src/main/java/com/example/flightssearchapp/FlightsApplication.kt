package com.example.flightssearchapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightssearchapp.data.AppContainer
import com.example.flightssearchapp.data.AppDataContainer
import com.example.flightssearchapp.repositories.UserPreferencesRepository

private const val AIRPORT_SEARCH_PREFERENCES = "search_preferences"

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = AIRPORT_SEARCH_PREFERENCES
)

class FlightsApplication : Application() {

    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(datastore)
    }
}