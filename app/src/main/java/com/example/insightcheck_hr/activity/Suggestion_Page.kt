package com.example.insightcheck_hr.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.adapter.SuggestionAdapter
import com.example.insightcheck_hr.adapter.leaveRequestAdapter
import com.example.insightcheck_hr.dataClass.suggestion
import com.example.insightcheck_hr.dataClass.user
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Suggestion_Page : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<suggestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion_page)

        userRecyclerView = findViewById(R.id.Rv_suggestion)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<suggestion>()
        getUserData()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("suggestion")

        dbref.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.exists()){

                        for (userSnapshot in snapshot.children){

                            val user = userSnapshot.getValue(suggestion::class.java)
                            userArrayList.add(user!!)

                        }

                        userRecyclerView.adapter = SuggestionAdapter(userArrayList)
                    }

                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}