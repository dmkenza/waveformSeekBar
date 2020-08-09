package com.masoudss.lib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.kadencelibrary.extension.view.afterMeasured
import kotlinx.android.synthetic.main.view_wavefrom_seekbar.view.*


class WaveformSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private val rangeGap = 1f


    init {
        LayoutInflater.from(context).inflate(R.layout.view_wavefrom_seekbar, this, true)

        afterMeasured {
            initData()
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initData() {

        val waveformWidth = waveform.width

        var dX1: Float = -1f
        var dX2: Float = -1f//waveform.width.toFloat()

        bt_marker_left.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (dX1 == -1f) {
                        dX1 = this.getX() - event.rawX - v.width
                    }
                }
                MotionEvent.ACTION_MOVE -> {

                    var moveX = event.rawX + dX1

                    val testX = (moveX + v.width/2)

                    val percent = testX/ waveformWidth.toFloat()

                    if(waveform.rangeRight < (percent  * 100  + rangeGap)){
                        return@setOnTouchListener false
                    }

                    if(testX< 0  ){
                        v.setBias(0f, false)
                        return@setOnTouchListener false
                    }

                    if(testX > waveformWidth){
                        v.setBias(100f, false)
                        return@setOnTouchListener false
                    }


                    v.setBias(percent, false)

                    waveform.rangeLeft = percent  * 100f
                    waveform.setProgress ( percent  * 100f, false )
                    waveform.invalidate()

                }
                else -> return@setOnTouchListener false
            }
            return@setOnTouchListener true
        }


        bt_marker_right.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (dX2 == -1f) {
                        dX2 = this.getX() - event.rawX + waveformWidth - v.width
                    }

                }
                MotionEvent.ACTION_MOVE -> {

                    var moveX = event.rawX + dX2

                    val testX = (moveX + v.width/2)


                    val percent = testX/ waveformWidth.toFloat()

                    if(waveform.rangeLeft > percent  * 100 - rangeGap){
                        return@setOnTouchListener false
                    }

                    if(testX< 0  ){
                        v.setBias(0f, false)
                        return@setOnTouchListener false
                    }

                    if(testX > waveformWidth){
                        v.setBias(100f, false)
                        return@setOnTouchListener false
                    }


                    v.setBias(percent, false)

                    waveform.rangeRight = percent  * 100f
                    waveform.setProgress ( percent  * 100f, false)
                    waveform.invalidate()

                }
                else -> return@setOnTouchListener false
            }
            return@setOnTouchListener true
        }

    }


    var sample: IntArray? = null
        set(value) {
            waveform?.sample = value
            field = value
        }

    var progress: Float = 0f
        set(value) {
            waveform?.setProgress(value, false)
            field = value
        }



    var waveBackgroundColor: Int = Color.LTGRAY
        set(value) {
            waveform?.waveBackgroundColor = value
            field = value
        }


    var rangeColor: Int = Color.LTGRAY
        set(value) {
            field = value
            waveform?.rangeColor = value
        }

    var rangeColorWithProgress: Int = Color.LTGRAY
        set(value) {
            field = value
            waveform?.rangeColorWithProgress = value
        }


    var waveProgressColor: Int = Color.WHITE
        set(value) {
            waveform?.waveProgressColor = value
            field = value
        }

    var waveGap: Float = Utils.dp(context, 2)
        set(value) {
            waveform?.waveGap = value
            field = value
        }

    var waveWidth: Float = Utils.dp(context, 5)
        set(value) {
            waveform?.waveWidth = value
            field = value
        }

    var waveMinHeight: Float = waveWidth
        set(value) {
            waveform?.waveMinHeight = value
            field = value
        }

    var waveCornerRadius: Float = Utils.dp(context, 2)
        set(value) {
            waveform?.waveCornerRadius = value
            field = value
        }

    var waveGravity: WaveGravity = WaveGravity.CENTER
        set(value) {
            waveform?.waveGravity = value
            field = value
        }

    var onProgressChanged: SeekBarOnProgressChanged? = null
        set(value) {
            waveform?.onProgressChanged = value
            field = value
        }

}

private fun View.setBias(bias: Float, isVertical: Boolean) {

    val params = this.getLayoutParams() as? ConstraintLayout.LayoutParams

    if(isVertical){
        params?.verticalBias = bias
    }else{
        params?.horizontalBias = bias
    }
    params?.let {
        this.layoutParams = params
    }
}
