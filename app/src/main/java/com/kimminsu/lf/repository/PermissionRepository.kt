package com.kimminsu.lf.repository

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionRepository(private val activity: Activity, private val list: List<String>, private val code: Int) {

    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            Log.d("not access", "no")
            requestPermissions()
            //showAlert()
        } else {
            Log.d("access", "yes")
            Toast.makeText(activity, "Permissions already granted", Toast.LENGTH_SHORT).show()
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

    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need permission(s)")
        builder.setMessage("Some permissions are required to do the task.")
        builder.setPositiveButton("OK") { dialog, which -> requestPermissions() }
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "Should show an explanation.", Toast.LENGTH_SHORT).show()
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