package com.example.insightcheck_hr.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.activity.DepartmentAnalysis
import com.example.insightcheck_hr.activity.Prediction
import com.example.insightcheck_hr.activity.ProjectAnalysis
import com.example.insightcheck_hr.activity.PromotionAnalysis
import com.example.insightcheck_hr.activity.SalaryAnalysis
import com.example.insightcheck_hr.activity.WorkAccidentAnalysis
import com.example.insightcheck_hr.activity.timeSpentAnalysis

class Analysis : Fragment() {


    private lateinit var departmentLayout: LinearLayout
    private lateinit var projectLayout: LinearLayout
    private lateinit var salaryLayout: LinearLayout
    private lateinit var timeSpentLayout: LinearLayout
    private lateinit var promotionLayout: LinearLayout
    private lateinit var workAccidentLayout: LinearLayout
    private lateinit var predictButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analysis, container, false)

        departmentLayout = view.findViewById(R.id.departmentlayout)
        projectLayout = view.findViewById(R.id.projectlayout)
        salaryLayout = view.findViewById(R.id.salarylayout)
        timeSpentLayout = view.findViewById(R.id.timespentlayout)
        promotionLayout = view.findViewById(R.id.promotionlayout)
        workAccidentLayout = view.findViewById(R.id.workaccidentlayout)
        predictButton = view.findViewById(R.id.predictbutton)

        departmentLayout.setOnClickListener {
            startActivity(Intent(context, DepartmentAnalysis::class.java))
        }

        projectLayout.setOnClickListener {
            startActivity(Intent(context, ProjectAnalysis::class.java))
        }

        salaryLayout.setOnClickListener {
            startActivity(Intent(context, SalaryAnalysis::class.java))
        }

        timeSpentLayout.setOnClickListener {
            startActivity(Intent(context, timeSpentAnalysis::class.java))
        }

        promotionLayout.setOnClickListener {
            startActivity(Intent(context, PromotionAnalysis::class.java))
        }

        workAccidentLayout.setOnClickListener {
            startActivity(Intent(context, WorkAccidentAnalysis::class.java))
        }

        predictButton.setOnClickListener {
            startActivity(Intent(context, Prediction::class.java))
        }

        return view
    }
}