package com.eightsines.worklifebalance.core.util.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

fun Context.getDrawableCompat(id : Int) : Drawable = ContextCompat.getDrawable(this, id)!!
fun Context.getColorCompat(id : Int) : Int = ContextCompat.getColor(this, id)

fun Context.getFractionCompat(id : Int, base : Int = 1, pbase : Int = 1) : Float = resources.getFraction(
        id,
        base,
        pbase)

fun Context.checkSelfPermissionCompat(permission : String) = ContextCompat.checkSelfPermission(this, permission)
