package com.example.insightcheck_hr.fragments


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.AsyncTask
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.insightcheck_hr.R
import org.json.JSONException
import org.json.JSONObject
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class Analysis : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analysis, container, false)
        pieChart = view.findViewById(R.id.pie_chart)
        preparePieChartData()

        // Customize pie chart appearance (optional)
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.animateY(1400)

        satisfactionLevelEditText = view.findViewById(R.id.satisfactionLevelEditText)
        lastEvaluationEditText = view.findViewById(R.id.lastEvaluationEditText)
        numberProjectEditText = view.findViewById(R.id.numberProjectEditText)
        averageMonthlyHoursEditText = view.findViewById(R.id.averageMonthlyHoursEditText)
        timeSpendCompanyEditText = view.findViewById(R.id.timeSpendCompanyEditText)
        workAccidentEditText = view.findViewById(R.id.workAccidentEditText)
        promotionLast5YearsEditText = view.findViewById(R.id.promotionLast5YearsEditText)
        departmentsEditText = view.findViewById(R.id.departmentsEditText)
        salaryEditText = view.findViewById(R.id.salaryEditText)
        predictButton = view.findViewById(R.id.predictButton)
        predictionTextView = view.findViewById(R.id.predictionTextView)

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

        return view
    }
    private inner class MakePredictionTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val apiUrl = "https://178a-2401-4900-1c96-b2a6-bd06-b96-3a2f-dc79.ngrok-free.app/predict"  // Update with your server URL
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
                if (prediction == "An Employee may leave the organization") {
                    predictionTextView.text = "Employee may leave the organization"
                } else {
                    predictionTextView.text = "Employee may stay with the organization"
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
        entries.add(PieEntry(40f, "Sales"))
        entries.add(PieEntry(30f, "Marketing"))
        entries.add(PieEntry(30f, "Development"))

        val dataSet = PieDataSet(entries, "Departments")

        // Set colors for each slice (optional)
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#FFC107"))
        colors.add(Color.parseColor("#F44336"))
        colors.add(Color.parseColor("#2ECC71"))
        // ... add more colors as needed
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        activity?.runOnUiThread {
            // Update PieChart UI here
            pieChart.data = data
            pieChart.invalidate() // Refresh chart
        }
    }
}
