package com.binanceclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binanceclient.databinding.FragmentXCellBinding
import java.math.BigDecimal


class XAdapter(_data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>, _color: Int): RecyclerView.Adapter<XAdapter.ItemViewHolder>() {
    private var data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>
    private var color: Int
    init {this.data = _data; this.color = _color}
    fun setData(_data: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>){this.data = _data}

    inner class ItemViewHolder(private val itemBinding: FragmentXCellBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(order: MutableMap.MutableEntry<BigDecimal, BigDecimal>){
            itemBinding.amountText.text = String.format("%8.4f", order.value)
            itemBinding.priceText.text =  String.format("%11.6f", order.key)
            itemBinding.totalText.text = String.format("%11.6f", order.key*order.value)
            itemBinding.priceText.setTextColor(color)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = FragmentXCellBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val order: MutableMap.MutableEntry<BigDecimal, BigDecimal> = data[position]
        itemViewHolder.bind(order)
    }

    override fun getItemCount() = data.size
}