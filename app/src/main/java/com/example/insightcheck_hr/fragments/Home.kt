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
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Home : Fragment(), CalendarAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvDateMonth: TextView
    private lateinit var ivCalendarNext: ImageView
    private lateinit var ivCalendarPrevious: ImageView
    private lateinit var barChart: BarChart


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
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(2500f, 0f, "Sales"))
        entries.add(BarEntry(2000f, 1f, "Accounting"))
        entries.add(BarEntry(1500f, 2f, "HR"))
        entries.add(BarEntry(1800f, 3f, "Marketing"))

        val barDataSet = BarDataSet(entries, "Employee Count by Department")
        barDataSet.setColors(
            Color.parseColor("#FFC107"), // Sales - Orange
            Color.parseColor("#F44336"), // Accounting - Red
            Color.parseColor("#2ECC71"), // HR - Green
            Color.parseColor("#3498DB")  // Marketing - Blue
        )
        barDataSet.setValueTextSize(16f)
//        barDataSet.barWidth = 0.6f// Adjust data label size (optional)

        val barData = BarData(barDataSet)

        barChart.data = barData
        barChart.description.isEnabled = false // Hide chart description
        barChart.animateY(1500) // Animate bar entry appearance
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM // Set x-axis position

        // Remove grid lines
        barChart.axisLeft.setDrawGridLines(false) // Disable left y-axis grid lines
        barChart.xAxis.setDrawGridLines(false)

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
}
