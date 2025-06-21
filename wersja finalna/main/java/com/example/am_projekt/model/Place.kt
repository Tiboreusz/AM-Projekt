package com.example.am_projekt.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//### Model Miejsca ###//
@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String,
    val rating: Int,
    val description: String,
    val photoUri: String? = null //Do doddawania zdjęć z galerii, zrobimy jak to rozkminimy
)
