package com.example.am_projekt.database


import androidx.room.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.am_projekt.model.Place

@Database(entities = [Place::class], version = 1) //Baza przechowuje obiekty klasy "Place"//
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        //### Inicjowanie bazy ###//
        @Volatile//Zmienna INSTANCE jest zmieniana w wielu wątkach i jej zmiana ma być natychmiast widoczna dla innych wątków//
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( //Tworzenie bazy//
                    context.applicationContext,
                    AppDatabase::class.java,
                    "places_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

