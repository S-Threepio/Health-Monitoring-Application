package com.swanand.mc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.swanand.mc.database.CovaDB
import com.swanand.mc.database.SymptomsDB
import com.swanand.mc.databinding.SymptomsLayoutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SymptomsFragment(var heartRate: Int, var breathingRate: Int) : Fragment() {
    private lateinit var binding: SymptomsLayoutBinding
    private val symptomRatingMap = mutableMapOf<String, Float>()
    private lateinit var selectedSymptom: String
    private lateinit var database: CovaDB


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SymptomsLayoutBinding.inflate(inflater, container, false)
        database = CovaDB.getInstance(this.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up your Spinner with the symptom array
        val symptomsArray = resources.getStringArray(R.array.symptoms_array)
        for (symptom in symptomsArray) {
            symptomRatingMap[symptom] = symptomRatingMap[symptom]?:0f
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, symptomsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.symptomsSpinner.adapter = adapter
        selectedSymptom = binding.symptomsSpinner.selectedItem.toString()

        // Set up an OnItemSelectedListener for the Spinner
        binding.symptomsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSymptom = symptomsArray[position]
                binding.ratingBar.rating = symptomRatingMap[selectedSymptom]?:0f
                // You can now use symptomRatingMap to store the ratings for each symptom
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            symptomRatingMap[selectedSymptom] = rating
        }

        binding.showDB.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.childContainer, DatabaseDisplayFragment())
                .addToBackStack("ChildFragment1")
                .commit()
        }

        binding.update.setOnClickListener {
            val symptomsList =
                SymptomsDB(
                    respiratoryRate = breathingRate.toFloat(),
                    heartRate = heartRate.toFloat(),
                    nausea = symptomRatingMap["Nausea"] ?: 0f,
                    headache = symptomRatingMap["Headache"] ?: 0f,
                    diarrhea = symptomRatingMap["Diarrhea"] ?: 0f,
                    soreThroat = symptomRatingMap["Sore Throat"] ?: 0f,
                    fever = symptomRatingMap["Fever"] ?: 0f,
                    muscleAche = symptomRatingMap["Muscle Ache"] ?: 0f,
                    lossOfSmellOrTaste = symptomRatingMap["Loss of Smell or Taste"] ?: 0f,
                    cough = symptomRatingMap["Cough"] ?: 0f,
                    shortOfBreath = symptomRatingMap["Shortness of Breath"] ?: 0f,
                    feelingTired = symptomRatingMap["Feeling Tired"] ?: 0f
                )


            // Insert the list of SymptomsDB objects into the Room database
            GlobalScope.launch(Dispatchers.IO) {
                database.symptomsDBDao().insertAll(symptomsList)
            }

            Toast.makeText(
                requireContext(),
                "Data uploaded to the database ${breathingRate}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}



