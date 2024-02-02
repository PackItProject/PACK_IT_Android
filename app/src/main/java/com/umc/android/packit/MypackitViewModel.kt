package com.umc.android.packit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccumulationViewModel : ViewModel() {
    private val _accumulationPoints = MutableLiveData<Int>()
    val accumulationPoints: LiveData<Int>
        get() = _accumulationPoints

    fun setAccumulationPoints(points: Int) {
        _accumulationPoints.value = points
    }
}
