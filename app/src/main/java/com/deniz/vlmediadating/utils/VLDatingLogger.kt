package com.deniz.vlmediadating.utils

import android.util.Log

object VLDatingLogger {
    private const val TAG = "VLDatingLogger"

    fun logDebug(message: String) {
        Log.d(TAG, message)
    }

    fun logError(message: String) {
        Log.e(TAG, message)
    }
}