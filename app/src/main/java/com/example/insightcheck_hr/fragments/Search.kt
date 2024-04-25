package com.example.insightcheck_hr.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.adapter.DataAdapter
import com.example.insightcheck_hr.dataClass.Empolyee_data
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader

class Search : Fragment() {

    private lateinit var emprecyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var empArrayList :ArrayList<Empolyee_data>
    lateinit var name :Array <String>
    lateinit var department :Array <String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        emprecyclerView = view.findViewById(R.id.listofData)
        emprecyclerView.layoutManager = LinearLayoutManager(requireContext())

        readCSVFile()

        return view
    }

    private fun readCSVFile(){
        val bufferReader = BufferedReader(requireContext().assets.open("Employee-Attrition.csv").reader())
        val csvParser = CSVParser.parse(
            bufferReader,
            CSVFormat.DEFAULT
        )
        val list = mutableListOf<Empolyee_data>()
        csvParser.forEach{
            it?.let {
               val empolyeeData = Empolyee_data (
                 //name,Department,Age,Gender,MaritalStatus,Education, Job tenure,MonthlyIncome,PerformanceRating,Work location,EnvironmentSatisfaction,StandardHoursWorking,JobSatisfaction,Promotions,TrainingTimesLastYear,PercentSalaryHike,YearsAtCompany,YearsInCurrentRole,DistanceFromHome,EmployeeCount,JobInvolvement,NumCompaniesWorked,Over18,OverTime
                   name = it.get(0),
                   Department = it.get(1),
                )
                list.add(empolyeeData)
            }
        }
        adapter = DataAdapter(list as ArrayList<Empolyee_data>)
        emprecyclerView.adapter = adapter


    }
}
