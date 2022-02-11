package com.binanceclient.ask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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