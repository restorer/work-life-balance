package com.eightsines.worklifebalance.core.util.ext

import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.view.View

fun View.setBackgroundCompat(background : Drawable) = ViewCompat.setBackground(this, background)
