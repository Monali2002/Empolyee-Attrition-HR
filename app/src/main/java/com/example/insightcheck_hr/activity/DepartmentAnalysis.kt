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

class DepartmentAnalysis : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet

    lateinit var barEntriesList: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var branch = arrayOf("Sales", "Accounting", "HR", "Technical", "Support", "IT","product_mng","Marketing","R&D")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_analysis)
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
        entries.add(PieEntry(27f, "Sales"))
        entries.add(PieEntry(3.6f, "Management"))
        entries.add(PieEntry(5.0f, "HR"))
        entries.add(PieEntry(5.2f, "Accounting"))
        entries.add(PieEntry(5.6f, "Marketing"))
        entries.add(PieEntry(5.7f, "Product_Mng"))
        entries.add(PieEntry(5.8f, "R&D"))
        entries.add(PieEntry(8.1f, "IT"))
        entries.add(PieEntry(15.2f, "Support"))
        entries.add(PieEntry(18.7f, "Technical"))

        val dataSet = PieDataSet(entries, "Departments")


        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#ef97a2"))
        colors.add(Color.parseColor("#de669d"))
        colors.add(Color.parseColor("#d70077"))
        colors.add(Color.parseColor("#9e0057"))
        colors.add(Color.parseColor("#6b2e72"))
        colors.add(Color.parseColor("#843a8e"))
        colors.add(Color.parseColor("#9d64a6"))
        colors.add(Color.parseColor("#bc9ac8"))
        colors.add(Color.parseColor("#70b5e5"))
        colors.add(Color.parseColor("#4185c6"))

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
        barEntriesList.add(BarEntry(1f, 2689f))
        barEntriesList.add(BarEntry(2f, 512f))
        barEntriesList.add(BarEntry(3f, 488f))
        barEntriesList.add(BarEntry(4f, 1854f))
        barEntriesList.add(BarEntry(5f, 1509f))
        barEntriesList.add(BarEntry(6f, 384f))
        barEntriesList.add(BarEntry(7f, 818f))
        barEntriesList.add(BarEntry(8f, 576f))
        barEntriesList.add(BarEntry(9f, 561f))
        barEntriesList.add(BarEntry(10f, 609f))
        return barEntriesList
    }

    private fun getBarChartDataForSet2(): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        // on below line we are adding data
        // to our bar entries list
        barEntriesList.add(BarEntry(1f, 550f))
        barEntriesList.add(BarEntry(2f, 109f))
        barEntriesList.add(BarEntry(3f, 113f))
        barEntriesList.add(BarEntry(4f, 390f))
        barEntriesList.add(BarEntry(5f, 312f))
        barEntriesList.add(BarEntry(6f, 52f))
        barEntriesList.add(BarEntry(7f, 158f))
        barEntriesList.add(BarEntry(8f, 110f))
        barEntriesList.add(BarEntry(9f, 112f))
        barEntriesList.add(BarEntry(10f, 85f))
        return barEntriesList
    }
}