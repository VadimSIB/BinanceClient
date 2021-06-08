package com.binanceclient

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binanceclient.R.*
import java.math.BigDecimal



class XAdapter(_data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>, val _color: String): RecyclerView.Adapter<XAdapter.ItemViewHolder>() {
    private var data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>

    init {this.data = _data}
    fun setData(_data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>){this.data = _data}

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountView: TextView
        val priceView: TextView
        val totalView: TextView
        init {
            amountView = itemView.findViewById(id.amount_text)
            priceView = itemView.findViewById(id.price_text)
            totalView = itemView.findViewById(id.total_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout.fragment_x_cell, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        itemViewHolder.amountView.text = String.format("%8.4f", data[position].value)
        itemViewHolder.priceView.text = String.format("%11.6f", data[position].key)
        itemViewHolder.totalView.text = String.format("%11.6f", data[position].key*data[position].value)
        itemViewHolder.priceView.setTextColor(Color.parseColor(_color))
    }

    override fun getItemCount() = data.size
}