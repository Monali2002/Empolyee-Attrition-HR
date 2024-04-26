package com.example.insightcheck_hr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.dataClass.suggestion

class SuggestionAdapter(private val userList: ArrayList<suggestion>): RecyclerView.Adapter<SuggestionAdapter.SugViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SugViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.suggestion,
        parent, false)

        return SugViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SugViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.message.text = currentitem.suggestionMessage
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class SugViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val message : TextView = itemView.findViewById(R.id.tvSuggestionMessage)
    }
}