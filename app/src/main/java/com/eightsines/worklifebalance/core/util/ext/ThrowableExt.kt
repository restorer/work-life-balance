package com.eightsines.worklifebalance.core.util.ext

fun Throwable.toException() = this as? Exception ?: Exception(this)
