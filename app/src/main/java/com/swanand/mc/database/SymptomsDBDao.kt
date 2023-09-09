package com.swanand.mc.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SymptomsDBDao {
    @Query("SELECT * FROM SymptomsDB")
    fun getAll(): List<SymptomsDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg symptoms: SymptomsDB)

    @Delete
    fun delete(symptoms: SymptomsDB)

    @Query("DELETE FROM SymptomsDB")
    fun deleteAll()
}