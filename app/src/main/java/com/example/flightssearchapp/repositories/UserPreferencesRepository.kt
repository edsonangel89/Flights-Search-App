package com.example.flightssearchapp.repositories

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
     private val dataStore: DataStore<Preferences>
)
{
    /*private lateinit var searchList: Flow<String>*/
    val searchPreferences: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.d(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
               preferences[SEARCH_PREFERENCES] ?: ""
        }

    private companion object {
        val SEARCH_PREFERENCES = stringPreferencesKey("search_preferences")
        const val TAG = "UserPreferencesRepo"
    }

    /*suspend fun getFavorites() {
         val favorites:
    }*/

    suspend fun saveSearch(search: String) {
          dataStore.edit { preferences ->
                preferences[SEARCH_PREFERENCES] = "searches"
          }
    }
}