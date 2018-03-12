package com.eightsines.worklifebalance.core.util.ext

import android.app.Activity
import android.support.v4.app.ActivityCompat

fun Activity.shouldShowRequestPermissionRationaleCompat(
        permission : String
) = ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        permission)

fun Activity.requestPermissionsCompat(
        permissions : Array<String>,
        requestCode : Int) = ActivityCompat.requestPermissions(this, permissions, requestCode)
