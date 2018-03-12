package com.eightsines.worklifebalance.core.state

enum class LoadingState {
    Initial,
    Loading,
    Loaded,
    LoadFailed;

    companion object {
        fun fromFaulted(isFaulted : Boolean) = if (isFaulted) LoadFailed else Loaded
    }
}
