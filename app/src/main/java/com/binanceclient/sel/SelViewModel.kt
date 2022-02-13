package com.binanceclient.sel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.binanceclient.BinanceRepository
import com.binanceclient.CryptoPairType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelViewModel @Inject constructor(private val state: SavedStateHandle,
                                       private val binanceRepository: BinanceRepository): ViewModel() {
    val savedSelected = state.getLiveData<String>("selected")

    fun selectPair(item: String) {
        val sym = CryptoPairType.fromPairName(item)?.symbol
        binanceRepository.initializeDepthCache(sym)
        binanceRepository.startDepthEventStreaming(sym)
        state["sym"] = sym
        state["selected"] = item
    }

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

    override fun onCleared() {
        super.onCleared()
        binanceRepository.runningSocket?.close()
    }
}