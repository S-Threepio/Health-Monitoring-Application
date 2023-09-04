package com.swanand.mc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swanand.mc.database.CovaDB
import com.swanand.mc.database.SymptomsDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseDisplayFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DBAdapter // Replace with your adapter class


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_database_display, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DBAdapter(emptyList()) // Pass your data to the adapter
        recyclerView.adapter = adapter
        return view
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
