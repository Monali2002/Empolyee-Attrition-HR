package com.example.insightcheck_hr.fragments


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.insightcheck_hr.model.CalendarDateModel
import com.example.insightcheck_hr.R
import com.example.insightcheck_hr.activity.LeaveRequest
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
    private lateinit var leaveRequestbtn: Button
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
        leaveRequestbtn = view.findViewById(R.id.leaveRequestbtn)
        // on below line we are creating a new bar data set
        barDataSet1 = BarDataSet(getBarChartDataForSet1(), "Present")
        barDataSet1.setColor(resources.getColor(R.color.Purple40))
        barDataSet2 = BarDataSet(getBarChartDataForSet2(), "Left")
        barDataSet2.setColor(resources.getColor(R.color.purple80))


        leaveRequestbtn.setOnClickListener {
            val intent = Intent(activity, LeaveRequest::class.java)
            startActivity(intent)
        }

        // on below line we are adding bar data set to bar data
        var data = BarData(barDataSet1, barDataSet2)

        // on below line we are setting data to our chart
        barChart.data = data

        // on below line we are setting description enabled.
        barChart.description.isEnabled = false

        // on below line setting x axis
        var xAxis = barChart.xAxis


        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.valueFormatter = IndexAxisValueFormatter(branch)

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true)

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(0.2f)

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true)

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true)

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3f)

        // below line is to add bar
        // space to our chart.
        val barSpace = 0.1f

        // below line is use to add group
        // spacing to our bar chart.
        val groupSpace = 0.3f

        // we are setting width of
        // bar in below line.
        data.barWidth = 0.15f

        // below line is to set minimum
        // axis to our chart.
        barChart.xAxis.axisMinimum = 0f

        // below line is to
        // animate our chart.
        barChart.animateY(1500)

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0f, groupSpace, barSpace)

        // Remove grid lines
        barChart.axisLeft.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false

        // below line is to invalidate
        // our bar chart.
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