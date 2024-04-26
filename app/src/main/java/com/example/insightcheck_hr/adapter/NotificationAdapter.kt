package com.example.insightcheck_hr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.dataClass.notification
import android.view.View

class NotificationAdapter (private var notificationList : ArrayList<notification>): RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = notificationList[position]
        holder.userName.text = currentItem.name
        holder.notDesc.text = currentItem.desc

    }

    override fun getItemCount(): Int {
        return  notificationList.size
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val userName : TextView = itemView.findViewById(R.id.nameTv)
        val notDesc : TextView = itemView.findViewById(R.id.descTv)
    }
}