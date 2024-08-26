package com.example.flightssearchapp.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightssearchapp.FlightsApplication
import com.example.flightssearchapp.models.Airport
import com.example.flightssearchapp.models.Favorite
import com.example.flightssearchapp.models.Flight
import com.example.flightssearchapp.repositories.AirportRepository
import com.example.flightssearchapp.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    var airId by mutableStateOf("")

    var airportCompleteList: MutableList<Airport> = mutableListOf()
    var airportListLocal: MutableList<Pair<String, String>> = mutableListOf()
    var airportSearches: List<Pair<String, String>> = mutableListOf()

    var posibleFlightsList: MutableList<Flight> = mutableListOf()

    var flightStates: MutableList<Boolean> = mutableListOf()

    fun updateState(ind: Int) {
        viewModelScope.launch {
            val updatedList = posibleFlightsList.toMutableList()
            val index = updatedList.indexOf(posibleFlightsList[ind])
            Log.d("UPDATED LIST BEFORE", updatedList.toString())
            updatedList[index].likeState = !updatedList[index].likeState
            Log.d("UPDATED LIST AFTER", updatedList.toString())
            /*posibleFlightsList.value[ind].likeState = !posibleFlightsList.value[ind].likeState*/
            /*posibleFlightsList[ind].likeState = !posibleFlightsList[ind].likeState*/
            posibleFlightsList = updatedList
        }
    }

    var id: Int = 0

    init {
        viewModelScope.launch {
            airportRepository.getAllAirports().forEach {
                airportListLocal.add(Pair(it.iataCode,it.name))
                airportCompleteList.add(it)
            }
        }
    }

    fun updateAirId(aId: String) {
        airId = aId
    }

    fun getAirports(search: String) =
        viewModelScope.launch {
            if(search.isBlank()) {
                airportSearches = emptyList()
            }
            else {
                airportSearches = searchLocal(search, airportListLocal)
            }
        }

    fun searchLocal(search: String,list: List<Pair<String, String>>): List<Pair<String, String>> {
        var searchList = mutableListOf<Pair<String, String>>()
        var searchLocal = search.lowercase()
        var searchSplit = searchLocal.split(" - ")
        if(searchSplit.size == 2) {
            var search1 = searchSplit[0]
            var search2 = searchSplit[1]
            list.forEach { elem ->
                val elemFLocal = elem.first.lowercase()
                val elemSLocal = elem.second.lowercase()
                if(elemFLocal.contentEquals(search1) || elemSLocal.contentEquals(search2)) {
                    searchList.add(elem)
                }
            }
        }
        else {
            list.forEach { elem ->
                val elemFLocal = elem.first.lowercase()
                val elemSLocal = elem.second.lowercase()
                if(elemFLocal.contains(searchLocal) || elemSLocal.contains(searchLocal)) {
                    searchList.add(elem)
                }
            }
        }
        return searchList
    }

    fun posibleFlights(search: String) {
        viewModelScope.launch {
            var flightsList = listOf<Any>()
            var airport: Airport = Airport(0,"","",0)
            val posibleFlights: MutableList<Flight> = mutableListOf()
            var fStates: MutableList<Boolean> = mutableListOf()
            var searchSplit = search.split(" - ")

            if(searchSplit.size == 2 || searchSplit.size == 3) {
                var search1 = searchSplit[0]
                var search2 = searchSplit[1]
                if(searchSplit.size == 3) {
                    var search3 = searchSplit[2]
                }
                airportCompleteList.forEach{
                    if(it.iataCode == search1) {
                        airport = it
                    }
                }
                flightsList = airportCompleteList.filterNot {
                    it.iataCode == search1
                }
                flightsList.forEach {
                    val flight = Flight(id = id, departure = airport, arrive = it)
                    posibleFlights.add(flight)
                    flightStates.add(flight.likeState)
                    id++
                }
            }
            else {

            }
            flightStates = fStates
            Log.d("DEBUG", posibleFlightsList.toString())
            posibleFlightsList = posibleFlights
            Log.d("DEBUG", posibleFlightsList.toString())
        }
    }

    fun getFavorites(): Flow<List<Favorite>> = favoriteRepository.getAllFavorites()

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch {
            favoriteRepository.insertFavorite(favorite)
        }

    companion object {
         val factory: ViewModelProvider.Factory =
             viewModelFactory {
                 initializer {
                     val application = (this[APPLICATION_KEY] as FlightsApplication)
                     val airportRepository = application.container.airportRepository
                     val favoriteRepository = application.container.favoriteRepository
                     SearchViewModel(airportRepository, favoriteRepository)
                 }
             }
    }

}