package com.example.am_projekt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.am_projekt.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    //### Dodawanie do bazy ###//
    @Insert
    suspend fun insertPlace(place: Place)

    //### Odczyt z bazy ###//
    @Query("SELECT * FROM places")
    fun getAllPlaces(): Flow<List<Place>>

    //### Usuwanie z bazy ###//
    @Query("DELETE FROM places")
    suspend fun deleteAllPlaces()
}
