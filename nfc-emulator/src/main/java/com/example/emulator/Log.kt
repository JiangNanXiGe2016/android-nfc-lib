package com.example.emulator

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.Date

class Log(val tv: TextView) {
    fun i(tag: String, msg: String) {
        Handler(Looper.getMainLooper()).post {
            var stringBuffer = StringBuffer(tv.text)
            stringBuffer.append("\n")
            stringBuffer.append("${Date().time}  $msg")
            stringBuffer.append("\n")
            tv.text = stringBuffer.toString()
        }
    }
}