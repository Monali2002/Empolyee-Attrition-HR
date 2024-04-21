package com.example.insightcheck_hr.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.insightcheck_hr.model.CalendarDateModel
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.adapter.CalendarAdapter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Home : Fragment(), CalendarAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvDateMonth: TextView
    private lateinit var ivCalendarNext: ImageView
    private lateinit var ivCalendarPrevious: ImageView
    private lateinit var barChart: BarChart
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var branch = arrayOf("Sales", "Accounting", "HR", "Technical", "Support", "IT","product_mng","Marketing","R&D")

    private val sdf = java.text.SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        barChart = view.findViewById(R.id.bar_chart)
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

        // Initialize views
        tvDateMonth = view.findViewById(R.id.text_date_month)
        recyclerView = view.findViewById(R.id.recyclerView)
        ivCalendarNext = view.findViewById(R.id.iv_calendar_next)
        ivCalendarPrevious = view.findViewById(R.id.iv_calendar_previous)
        setUpAdapter()
        ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }

        ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
        }
        setUpCalendar()

        return view
    }

    override fun onItemClick(text: String, date: String, day: String) {
        // Handle item click if needed
    }

    private fun setUpAdapter() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setData(calendarList2)
            adapter.setOnItemClickListener(this)
        }
        recyclerView.adapter = adapter
    }



    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)

        // Set calendar to the current date instead of the 1st day of the month
        monthCalendar.set(Calendar.DAY_OF_MONTH, currentDay)

        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)

            val isSelected = (
                    monthCalendar.get(Calendar.MONTH) == currentMonth &&
                            monthCalendar.get(Calendar.YEAR) == currentYear &&
                            monthCalendar.get(Calendar.DAY_OF_MONTH) == currentDay
                    )

            calendarList.add(CalendarDateModel(monthCalendar.time, isSelected))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setOnItemClickListener(this)
        adapter.setData(calendarList)
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