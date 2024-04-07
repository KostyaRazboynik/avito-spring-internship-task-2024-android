package com.kostyarazboynik.utils

import android.util.Log


object Logger {

    private var isDebug = false

    fun setIsDebug(isDebug: Boolean) {
        Logger.isDebug = isDebug
    }

    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun w(tag: String, errMessage: String) {
        Log.w(tag, errMessage)
    }

    fun e(tag: String, thr: Throwable) {
        Log.e(tag, "error " + thr.localizedMessage, thr)
    }
}
