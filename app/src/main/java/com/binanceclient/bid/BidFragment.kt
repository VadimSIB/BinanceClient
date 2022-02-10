package com.binanceclient.bid


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import com.binanceclient.R
import com.binanceclient.XFragment
import java.math.BigDecimal


class BidFragment : XFragment() {
    override var color = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        color =  ResourcesCompat.getColor(requireContext().resources, R.color.bottom_nav_1,null)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>> {
        return binanceViewModel.bidOrderBook
    }
}