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

class WorkAccidentAnalysis : AppCompatActivity() {


    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet

    lateinit var barEntriesList: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var branch = arrayOf("0", "1")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_accident_analysis)

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
        entries.add(PieEntry(84.6f, "0"))
        entries.add(PieEntry(15.4f, "1"))

        val dataSet = PieDataSet(entries, "Work_accident")


        val colors = ArrayList<Int>()
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
        barEntriesList.add(BarEntry(1f, 8255f))
        barEntriesList.add(BarEntry(2f, 1745f))
        return barEntriesList
    }

    private fun getBarChartDataForSet2(): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        // on below line we are adding data
        // to our bar entries list
        barEntriesList.add(BarEntry(1f, 1886f))
        barEntriesList.add(BarEntry(2f, 105f))
        return barEntriesList
    }
}