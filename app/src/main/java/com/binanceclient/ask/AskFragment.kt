package com.binanceclient.ask

import androidx.lifecycle.LiveData
import com.binanceclient.R
import com.binanceclient.XFragment
import java.math.BigDecimal

class AskFragment : XFragment() {
    override var clr = R.color.bottom_nav_2
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.askOrderBook
    }
}