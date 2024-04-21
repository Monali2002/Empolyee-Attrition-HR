package com.example.insightcheck_hr.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.insightcheck_hr.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SalaryAnalysis : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet

    lateinit var barEntriesList: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var branch = arrayOf("Low", "Medium", "High")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_analysis)

        pieChart = findViewById(R.id.pie_chart1)
        preparePieChartData()
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.animateY(1400)


        // Barchart
        barChart = findViewById(R.id.bar_chart)
        // on below line we are creating a new bar data set
        barDataSet1 = BarDataSet(getBarChartDataForSet1(), "Present")
        barDataSet1.setColor(resources.getColor(R.color.Purple40))
        barDataSet2 = BarDataSet(getBarChartDataForSet2(), "Left")
        barDataSet2.setColor(resources.getColor(R.color.purple80))

        var data = BarData(barDataSet1, barDataSet2)
        barChart.data = data
        barChart.description.isEnabled = false
        var xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(branch)
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setGranularity(0.2f)
        xAxis.setGranularityEnabled(true)
        barChart.setDragEnabled(true)
        barChart.setVisibleXRangeMaximum(3f)
        val barSpace = 0.1f
        val groupSpace = 0.3f
        data.barWidth = 0.15f
        barChart.xAxis.axisMinimum = 0f
        barChart.animateY(1500)
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false
        barChart.invalidate()

    }

    private fun preparePieChartData() {
        val entries = ArrayList<PieEntry>()

        // Add each data point with its label and value
        entries.add(PieEntry(47.9f, "Low"))
        entries.add(PieEntry(43.9f, "Medium"))
        entries.add(PieEntry(8.3f, "High"))

        val dataSet = PieDataSet(entries, "Salary")


        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#f7b7a3"))
        colors.add(Color.parseColor("#ea5f89"))
        colors.add(Color.parseColor("#9b3192"))

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()

    }

    private fun getBarChartDataForSet1(): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        // on below line we are adding
        // data to our bar entries list
        barEntriesList.add(BarEntry(1f, 4566f))
        barEntriesList.add(BarEntry(2f, 4492f))
        barEntriesList.add(BarEntry(3f, 942f))
        return barEntriesList
    }

    private fun getBarChartDataForSet2(): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        // on below line we are adding data
        // to our bar entries list
        barEntriesList.add(BarEntry(1f, 1174f))
        barEntriesList.add(BarEntry(2f, 769f))
        barEntriesList.add(BarEntry(3f, 48f))
        return barEntriesList
    }
}