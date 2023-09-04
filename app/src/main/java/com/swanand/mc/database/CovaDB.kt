package com.swanand.mc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define your database version
private const val DATABASE_VERSION = 1

@Database(entities = [SymptomsDB::class], version = DATABASE_VERSION)
abstract class CovaDB : RoomDatabase() {
    abstract fun symptomsDBDao(): SymptomsDBDao
    companion object {
        // Singleton instance of the Room database
        @Volatile
        private var INSTANCE: CovaDB? = null

        // Function to get or create the database instance
        fun getInstance(context: Context): CovaDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CovaDB::class.java,
                    "CovaDB" // Replace with your desired database name
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
