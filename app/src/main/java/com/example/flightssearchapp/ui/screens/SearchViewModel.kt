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

    var _favList = mutableStateOf(listOf<Favorite>())

    /*private val _favList = MutableStateFlow(listOf<Favorite>())
    val favList = _favList.asStateFlow()*/

    fun updateState(ind: Int) {
        viewModelScope.launch {
            Log.d("POSIBLE FLIGHTS LIST", posibleFlightsList.size.toString())
            var updatedList = posibleFlightsList.toMutableList()
            Log.d("INT", ind.toString())
            var index = updatedList.indexOf(posibleFlightsList[ind])
            Log.d("UPDATED LIST BEFORE", updatedList.toString())
            updatedList[index].likeState = !updatedList[index].likeState
            Log.d("UPDATED LIST AFTER", updatedList.toString())
            /*posibleFlightsList.value[ind].likeState = !posibleFlightsList.value[ind].likeState*/
            /*posibleFlightsList[ind].likeState = !posibleFlightsList[ind].likeState*/
            posibleFlightsList = updatedList
        }
    }

    init {
        viewModelScope.launch {
            airportRepository.getAllAirports().forEach {
                airportListLocal.add(Pair(it.iataCode, it.name))
                airportCompleteList.add(it)
            }
            try {
                _favList.value = favoriteRepository.getAllFavorites()
                Log.d("FAVLIST", _favList.value.toString())
                Log.d("POSIBLE FLIGHTS", posibleFlightsList.toString())
            } catch (e: Exception) {

            }
        }
    }

    fun updateAirId(aId: String) {
        airId = aId
    }

    fun getAirports(search: String) {
        viewModelScope.launch {
            if (search.isBlank()) {
                airportSearches = emptyList()
            } else {
                airportSearches = searchLocal(search, airportListLocal)
            }
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
            posibleFlightsList = emptyList<Flight>().toMutableList()
            var flightsList = listOf<Any>()
            var airport: Airport = Airport(0,"","",0)
            val posibleFlights: MutableList<Flight> = mutableListOf()
            var fStates: MutableList<Boolean> = mutableListOf()
            var searchSplit = search.split(" - ")
            var id = 0

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
                flightsList.forEach { arrAir ->
                    var flight = Flight(id = id, departure = airport, arrive = arrAir)
                    _favList.value.forEach{ fav ->
                        if (fav.departure == flight.departure.iataCode && fav.destination == flight.arrive.iataCode) {
                            flight.likeState = true
                        }
                    }
                    posibleFlights.add(flight)
                    Log.d("POSIBLE FLIGHTS", posibleFlights.size.toString())
                    flightStates.add(flight.likeState)
                    id++
                }
            }
            else {

            }
            flightStates = fStates
            Log.d("DEBUG", posibleFlightsList.size.toString())
            posibleFlightsList = posibleFlights
            Log.d("FAVLIST", _favList.value.toString())
            Log.d("POSIBLE FLIGHTS", posibleFlightsList.toString())
            Log.d("DEBUG", posibleFlightsList.size.toString())
        }
    }

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch {
            try {
                favoriteRepository.insertFavorite(favorite)
                _favList.value = favoriteRepository.getAllFavorites()
            }
            catch (e: Exception) {
                Log.d("UPDATE FAVORITE EXCEPTION", "EXCEPTION")
            }
        }
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