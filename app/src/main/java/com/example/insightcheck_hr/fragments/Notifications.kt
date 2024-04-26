package com.example.insightcheck_hr.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.adapter.NotificationAdapter
import com.example.insightcheck_hr.dataClass.notification

class Notifications : Fragment() {

    private  lateinit var notrecyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<notification>
    lateinit var name : Array<String>
    lateinit var desc: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        name = arrayOf("Mitali", "Monali", "Prachi", "Sneha")
        desc = arrayOf("Received a Feedback form", "Received a Feedback form", "Received a Feedback form", "Received a Feedback form")


        notrecyclerView = view.findViewById(R.id.notificationRV)
        notrecyclerView.layoutManager = LinearLayoutManager(requireContext())

        notificationList = arrayListOf<notification>()
        getUserData()

        return view
    }

    private fun getUserData(){
        for (i in name.indices){
            val data = notification(name[i],desc[i])
            notificationList.add(data)
        }

        notrecyclerView.adapter = NotificationAdapter(notificationList)
    }

}

