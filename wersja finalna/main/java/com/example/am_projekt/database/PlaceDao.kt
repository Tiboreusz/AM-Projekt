package com.example.am_projekt.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    @Update
    fun updatePlace(edditedPlace: Place)

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlaceById(id: Int): Flow<Place?>

}
