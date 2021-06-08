package com.binanceclient.ask


import androidx.lifecycle.LiveData
import com.binanceclient.XFragment
import java.math.BigDecimal


class AskFragment : XFragment() {
    override val color = "#AA0000"
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.askOrderBook
    }
}