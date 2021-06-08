package com.binanceclient.bid


import androidx.lifecycle.LiveData
import com.binanceclient.XFragment
import java.math.BigDecimal


class BidFragment : XFragment() {
    override val color = "#009090"
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.bidOrderBook
    }
}