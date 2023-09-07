package com.swanand.mc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swanand.mc.database.CovaDB
import com.swanand.mc.database.SymptomsDB
import com.swanand.mc.databinding.FragmentDatabaseDisplayBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseDisplayFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DBAdapter // Replace with your adapter class
    private lateinit var binding: FragmentDatabaseDisplayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatabaseDisplayBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DBAdapter(emptyList()) // Pass your data to the adapter
        recyclerView.adapter = adapter

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button press in the child fragment
                // You can perform your desired actions here
                parentFragmentManager.popBackStack(null, 0)
            }
        }

        // Register the callback with the parent fragment's activity
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDatabaseData()
    }

    // Add a method to set the database data when the fragment is created
    fun setDatabaseData() {
        val context = context ?: return

        val symptomsDao = CovaDB.getInstance(context).symptomsDBDao()
        GlobalScope.launch(Dispatchers.IO) {
            val data:List<SymptomsDB> = symptomsDao.getAll()
            // Update dbData with the retrieved data
            withContext(Dispatchers.Main) {
                adapter.updateData(data) // Notify the adapter that the data has changed
            }
        }
    }
}
