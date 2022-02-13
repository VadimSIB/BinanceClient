package com.binanceclient.bid

import androidx.lifecycle.LiveData
import com.binanceclient.R
import com.binanceclient.XFragment
import java.math.BigDecimal

class BidFragment : XFragment() {
    override var clr = R.color.bottom_nav_1
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.bidOrderBook
    }
}