package com.swanand.mc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.swanand.mc.databinding.SymptomsLayoutBinding

class SymptomsFragment : Fragment() {
    private lateinit var binding: SymptomsLayoutBinding
    private val symptomRatingMap = mutableMapOf<String, Float>()
    private lateinit var selectedSymptom:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SymptomsLayoutBinding.inflate(inflater, container, false)
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

        binding.update.setOnClickListener {
            val parentFragment = parentFragment
            if (parentFragment is SymptomsFragmentCallback) {
                parentFragment.onCloseSymptomsFragment(symptomRatingMap)
                parentFragmentManager.beginTransaction().remove(this@SymptomsFragment).commit()

            }
        }
    }


}



