package com.masoudss.lib

import android.content.Context
import android.util.TypedValue

internal object Utils {

    fun dp(context: Context?, dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context!!.resources.displayMetrics)
    }

}