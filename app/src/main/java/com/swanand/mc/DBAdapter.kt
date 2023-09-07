package com.swanand.mc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swanand.mc.database.SymptomsDB

class DBAdapter(private var data: List<SymptomsDB>) : RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.db_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textViewRespiratoryRate.text = "Respiratory Rate: ${item.respiratoryRate}"
        holder.textViewHeartRate.text = "Heart Rate: ${item.heartRate}"
        holder.textViewNausea.text = "Nausea: ${item.nausea}"
        holder.textViewHeadache.text = "Headache: ${item.headache}"
        holder.textViewDiarrhea.text = "Diarrhea: ${item.diarrhea}"
        holder.textViewSoreThroat.text = "Sore Throat: ${item.soreThroat}"
        holder.textViewFever.text = "Fever: ${item.fever}"
        holder.textViewMuscleAche.text = "Muscle Ache: ${item.muscleAche}"
        holder.textViewLossOfSmellOrTaste.text = "Loss of Smell or Taste: ${item.lossOfSmellOrTaste}"
        holder.textViewCough.text = "Cough: ${item.cough}"
        holder.textViewShortOfBreath.text = "Shortness of Breath: ${item.shortOfBreath}"
        holder.textViewFeelingTired.text = "Feeling Tired: ${item.feelingTired}"
    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewRespiratoryRate: TextView = itemView.findViewById(R.id.textViewRespiratoryRate)
        val textViewHeartRate: TextView = itemView.findViewById(R.id.textViewHeartRate)
        val textViewNausea: TextView = itemView.findViewById(R.id.textViewNausea)
        val textViewHeadache: TextView = itemView.findViewById(R.id.textViewHeadache)
        val textViewDiarrhea: TextView = itemView.findViewById(R.id.textViewDiarrhea)
        val textViewSoreThroat: TextView = itemView.findViewById(R.id.textViewSoreThroat)
        val textViewFever: TextView = itemView.findViewById(R.id.textViewFever)
        val textViewMuscleAche: TextView = itemView.findViewById(R.id.textViewMuscleAche)
        val textViewLossOfSmellOrTaste: TextView = itemView.findViewById(R.id.textViewLossOfSmellOrTaste)
        val textViewCough: TextView = itemView.findViewById(R.id.textViewCough)
        val textViewShortOfBreath: TextView = itemView.findViewById(R.id.textViewShortOfBreath)
        val textViewFeelingTired: TextView = itemView.findViewById(R.id.textViewFeelingTired)
    }

    fun updateData(newData: List<SymptomsDB>) {
        data = newData
        notifyDataSetChanged()
    }

}
