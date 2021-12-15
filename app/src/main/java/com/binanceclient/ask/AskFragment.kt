package com.binanceclient.ask


import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import com.binanceclient.App
import com.binanceclient.R
import com.binanceclient.XFragment
import java.math.BigDecimal


class AskFragment : XFragment() {
    override val color = ResourcesCompat.getColor(App.resourses!!, R.color.bottom_nav_2,null)
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.askOrderBook
    }
}