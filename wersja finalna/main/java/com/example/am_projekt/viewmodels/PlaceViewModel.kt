package com.example.am_projekt.viewmodels
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.am_projekt.database.AppDatabase
import com.example.am_projekt.model.Place
import com.example.am_projekt.database.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val placeDao: PlaceDao = AppDatabase.getDatabase(application).placeDao()

    private val _allPlaces = MutableStateFlow<List<Place>>(emptyList())
    val allPlaces: StateFlow<List<Place>> = _allPlaces

    private val _selectedPlace = MutableStateFlow<Place?>(null)
    val selectedPlace: StateFlow<Place?> = _selectedPlace

    init {
        //### Pobieranie danych o miejscach ###//
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.getAllPlaces().collect { places ->
                _allPlaces.value = places
            }
        }
    }
        // ### Pobieranie danych o miejscu o odpowiednim indeksie ### //
    fun getPlaceById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.getPlaceById(id).collect {
                _selectedPlace.value = it
            }
        }
    }


    fun updatePlace(edditedPlace: Place){
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.updatePlace(edditedPlace)
        }

    }


    //### Dodawanie miejsca do bazy ###//
    fun insertPlace(place: Place) {
        viewModelScope.launch {
            placeDao.insertPlace(place)
        }
    }

    fun deleteAllPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.deleteAllPlaces()
        }
    }

}





//### Fabryka PlaceViewModel - taki troche konstruktor ###//
class PlaceViewModelFactory(
    private val application: Application //przechowywanie kontekstu aplikacji przekazywanego do PlaceViewModel//
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {   // Tworzenie instancji PlaceViewModel, T - parametr typu wykorzystywany do generowania
        if (modelClass.isAssignableFrom(PlaceViewModel::class.java)) {// konkretnego typu ViewModel //
            return PlaceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class") // Jak T się nie zgadza wyrzuca błąd //
    }
}



