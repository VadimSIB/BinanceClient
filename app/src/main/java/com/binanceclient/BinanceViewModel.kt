package com.binanceclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.math.BigDecimal


class BinanceViewModel(private val state: SavedStateHandle): ViewModel() {

    private val binanceRepository = BinanceRepository()
    val bidOrderBook: LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
        get() = binanceRepository._bidOrderBook
    val askOrderBook: LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
        get() = binanceRepository._askOrderBook
    val savedSelected = state.getLiveData<String>("selected")

    init {
        val savedNam = state.get<String>("sym")
        val symb: String
        if (savedNam!=null) {
            symb = savedNam
        }else{
            symb = "BTCUSDT"
            state["selected"] = "BTC/USDT"
        }
        state["sym"] = symb
        binanceRepository.initializeDepthCache(symb)
        binanceRepository.startDepthEventStreaming(symb)
    }

    fun selectPair(item: String) {
        val sym = CryptoPairType.fromPairName(item)?.symbol
        binanceRepository.initializeDepthCache(sym)
        binanceRepository.startDepthEventStreaming(sym)
        state["sym"] = sym
        state["selected"] = item
    }

    override fun onCleared() {
        super.onCleared()
        binanceRepository.runningSocket?.close()
    }
}