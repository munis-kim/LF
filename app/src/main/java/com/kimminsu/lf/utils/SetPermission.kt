package com.kimminsu.lf.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SetPermission(private val activity: Activity, private val list: List<String>, private val code: Int) {

    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        }
    }

    private fun isPermissionsGranted(): Int {
        var counter = 0;
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }

    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) return permission
        }
        return ""
    }

    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "앱을 사용하려면 동의해야합니다.", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        } else {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        }
    }

    fun processPermissionsResult(): Boolean {
        var result = 0
        for (permission in list) {
            result += ContextCompat.checkSelfPermission(activity, permission)
        }
        if (result == PackageManager.PERMISSION_GRANTED) return true
        return false
    }
}