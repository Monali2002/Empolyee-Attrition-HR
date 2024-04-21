package com.example.insightcheck_hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.dataClass.user

class leaveRequestAdapter(private val userList: ArrayList<user>) : RecyclerView.Adapter<leaveRequestAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification,
        parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.leaveMessage.text = currentitem.leaveMessage
        holder.startDate.text = currentitem.startDate
        holder.endDate.text = currentitem.endDate
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tv_name)
        val leaveMessage : TextView = itemView.findViewById(R.id.tvMessage)
        val startDate : TextView = itemView.findViewById(R.id.tvStartDate)
        val endDate : TextView = itemView.findViewById(R.id.tvEndDate)

    }
}