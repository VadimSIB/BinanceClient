package com.binanceclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class BinanceViewModel @Inject constructor(private val binanceRepository: BinanceRepository): ViewModel() {
    val bidOrderBook: LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
        get() = binanceRepository._bidOrderBook
    val askOrderBook: LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
        get() = binanceRepository._askOrderBook
}