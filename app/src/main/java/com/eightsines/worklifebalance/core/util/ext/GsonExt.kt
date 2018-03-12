package com.eightsines.worklifebalance.core.util.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJsonExt(json: String) : T? = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
