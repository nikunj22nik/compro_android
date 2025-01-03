package com.yesitlab.compro

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DonutProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var progressPaint: Paint = Paint()
    private var backgroundPaint: Paint = Paint()

    private var progress = 0
    private var maxProgress = 100
    private val startAngle = 120f  // Start angle at 120 degrees
    private val totalAngle = -300f  // Sweep angle from 120 to 60 degrees, clockwise

    init {
        // Setup paints for the progress and background
        progressPaint.apply {
            color = resources.getColor(R.color.progressColor, null) // Define your progress color
            style = Paint.Style.STROKE
            strokeWidth = 10f // Thickness of the progress arc
            isAntiAlias = true
        }

        backgroundPaint.apply {
            color = resources.getColor(R.color.grayLight, null) // Define your background color
            style = Paint.Style.STROKE
            strokeWidth = 10f
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Define the bounds for the arc
        val oval = RectF(50f, 50f, width - 50f, height - 50f) // Adjust the size and padding

        // Draw the background arc (full circle)
        canvas?.drawArc(oval, startAngle, 300f, false, backgroundPaint)

        // Calculate the sweep angle based on progress
        val sweepProgress = (progress / maxProgress.toFloat()) * -totalAngle
        canvas?.drawArc(oval, startAngle, sweepProgress, false, progressPaint)
    }

    // Method to set the progress value
    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate() // Redraw the view
    }
}
