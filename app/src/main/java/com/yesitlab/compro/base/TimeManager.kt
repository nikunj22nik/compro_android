package com.yesitlab.compro.base

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

class TimeManager(context: Context) {


    fun selectDateManager(context: Context) : String?{
        var time : String? = null
        val c = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            context,
            { view, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our text view.
                time =
                    (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()
        return  time

    }


}