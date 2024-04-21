package com.example.insightcheck_hr.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.insightcheck_hr.R
import com.github.mikephil.charting.charts.PieChart
import android.graphics.Color
import android.os.AsyncTask
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.json.JSONException
import org.json.JSONObject
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class Prediction : AppCompatActivity() {
    private lateinit var satisfactionLevelEditText: EditText
    private lateinit var lastEvaluationEditText: EditText
    private lateinit var numberProjectEditText: EditText
    private lateinit var averageMonthlyHoursEditText: EditText
    private lateinit var timeSpendCompanyEditText: EditText
    private lateinit var workAccidentEditText: EditText
    private lateinit var promotionLast5YearsEditText: EditText
    private lateinit var departmentsEditText: EditText
    private lateinit var salaryEditText: EditText
    private lateinit var predictButton: Button
    private lateinit var predictionTextView: TextView
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        //pieChart = findViewById(R.id.pie_chart1)
//        preparePieChartData()
//
//        // Customize pie chart appearance (optional)
//        pieChart.description.isEnabled = false
//        pieChart.isRotationEnabled = true
//        pieChart.animateY(1400)

        satisfactionLevelEditText = findViewById(R.id.satisfactionLevelEditText)
        lastEvaluationEditText = findViewById(R.id.lastEvaluationEditText)
        numberProjectEditText = findViewById(R.id.numberProjectEditText)
        averageMonthlyHoursEditText = findViewById(R.id.averageMonthlyHoursEditText)
        timeSpendCompanyEditText = findViewById(R.id.timeSpendCompanyEditText)
        workAccidentEditText = findViewById(R.id.workAccidentEditText)
        promotionLast5YearsEditText = findViewById(R.id.promotionLast5YearsEditText)
        departmentsEditText = findViewById(R.id.departmentsEditText)
        salaryEditText = findViewById(R.id.salaryEditText)
        predictButton = findViewById(R.id.predictButton)
        predictionTextView = findViewById(R.id.predictionTextView)

        predictButton.setOnClickListener {
            val satisfactionLevel = satisfactionLevelEditText.text.toString()
            val lastEvaluation = lastEvaluationEditText.text.toString()
            val numberProject = numberProjectEditText.text.toString()
            val averageMonthlyHours = averageMonthlyHoursEditText.text.toString()
            val timeSpendCompany = timeSpendCompanyEditText.text.toString()
            val workAccident = workAccidentEditText.text.toString()
            val promotionLast5Years = promotionLast5YearsEditText.text.toString()
            val departments = departmentsEditText.text.toString()
            val salary = salaryEditText.text.toString()

            val jsonData = """{
                "satisfaction_level": $satisfactionLevel,
                "last_evaluation": $lastEvaluation,
                "number_project": $numberProject,
                "average_monthly_hours": $averageMonthlyHours,
                "time_spend_company": $timeSpendCompany,
                "Work_accident": $workAccident,
                "promotion_last_5years": $promotionLast5Years,
                "departments ": "$departments",
                "salary": "$salary"
            }"""

            MakePredictionTask().execute(jsonData)
        }
    }

    private inner class MakePredictionTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val apiUrl = "https://your-api-url-here.com/predict"  // Update with your server URL
            val jsonData = params[0]

            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val outputStream: OutputStream = connection.outputStream
            outputStream.write(jsonData?.toByteArray())
            outputStream.flush()

            val responseCode = connection.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().readText()
            } else {
                "Error: $responseCode"
            }
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonResponse = JSONObject(result)
                val prediction = jsonResponse.getString("prediction")

                // Update UI based on the prediction
                predictionTextView.text = if (prediction == "An Employee may leave the organization") {
                    "Employee may leave the organization"
                } else {
                    "Employee may stay with the organization"
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                predictionTextView.text = "Error parsing prediction result"
            }
        }
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
        colors.add(Color.parseColor("#df6dff"))
        colors.add(Color.parseColor("#ff6bc7"))
        colors.add(Color.parseColor("#ff7670"))
        colors.add(Color.parseColor("#ffc16c"))
        colors.add(Color.parseColor("#ddff6c"))
        colors.add(Color.parseColor("#8bff6a"))
        colors.add(Color.parseColor("#6dffac"))
        colors.add(Color.parseColor("#6bffff"))
        colors.add(Color.parseColor("#6eaaff"))
        colors.add(Color.parseColor("#8b6dff"))

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.BLACK)

        // Update PieChart UI here
        pieChart.data = data
        pieChart.invalidate() // Refresh chart
    }
}
