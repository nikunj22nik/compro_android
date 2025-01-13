package com.yesitlab.compro.base

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

class TimeManager(private val context: Context) {
/*
    fun selectDateManager(onDateSelected: (String) -> Unit) {
        val c = Calendar.getInstance()

        // Get current date components
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                // Format the selected date
                val formattedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                onDateSelected(formattedDate) // Pass the selected date to the callback
            },
            year,
            month,
            day
        )

        datePickerDialog.show() // Show the dialog
    }


 */

    fun selectDateManager(onDateSelected: (String) -> Unit) {
        val c = Calendar.getInstance()

        // Get current date components
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date with leading zeros for month and day
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                onDateSelected(formattedDate) // Pass the selected date to the callback
            },
            year,
            month,
            day
        )

        datePickerDialog.show() // Show the dialog
    }


}
