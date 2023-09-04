package com.swanand.mc.database

import androidx.room.*

@Entity(tableName = "SymptomsDB")
data class SymptomsDB(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Respiratory Rate") val respiratoryRate: Float = 0f,
    @ColumnInfo(name = "Heart Rate") val heartRate: Float = 0f,
    @ColumnInfo(name = "Nausea") val nausea: Float = 0f,
    @ColumnInfo(name = "Headache") val headache: Float = 0f,
    @ColumnInfo(name = "Diarrhea") val diarrhea: Float = 0f,
    @ColumnInfo(name = "Sore Throat") val soreThroat: Float = 0f,
    @ColumnInfo(name = "Fever") val fever: Float = 0f,
    @ColumnInfo(name = "Muscle Ache") val muscleAche: Float = 0f,
    @ColumnInfo(name = "Loss of Smell or Taste") val lossOfSmellOrTaste: Float = 0f,
    @ColumnInfo(name = "Cough") val cough: Float = 0f,
    @ColumnInfo(name = "Shortness of Breath") val shortOfBreath: Float = 0f,
    @ColumnInfo(name = "Feeling Tired") val feelingTired: Float = 0f
)

