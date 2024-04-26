package com.example.insightcheck_hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.dataClass.Empolyee_data
import com.example.insightcheck_hr.fragments.Search

class DataAdapter (private var empolyeeList : ArrayList<Empolyee_data>) :RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = empolyeeList[position]
        holder.empName.text = currentItem.name
        holder.empDept.text = currentItem.Department

    }

    override fun getItemCount(): Int {

        return empolyeeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val empName :TextView = itemView.findViewById(R.id.tvName)
        val empDept :TextView = itemView.findViewById(R.id.tvDept)
    }

    fun setFilteredList(empolyeeList : ArrayList<Empolyee_data>){
        this.empolyeeList = empolyeeList
        notifyDataSetChanged()
    }
}