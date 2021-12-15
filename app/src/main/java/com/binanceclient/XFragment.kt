package com.binanceclient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.binanceclient.databinding.FragmentXBinding
import java.math.BigDecimal


abstract class XFragment : Fragment() {

    val binanceViewModel: BinanceViewModel by activityViewModels()
    protected abstract val color: Int
    protected abstract fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
    private var _binding: FragmentXBinding? = null
    private val binding get() = _binding!!
    private lateinit var xGridAdapter: XAdapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentXBinding.inflate(inflater, container, false)
        val root: View = binding.getRoot()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: List<MutableMap.MutableEntry<BigDecimal, BigDecimal>> = arrayListOf()
        xGridAdapter = XAdapter(list, color)
        binding.xCells.adapter = xGridAdapter
        val xOrderBook = getXOrderBook()
        xOrderBook.observe(viewLifecycleOwner, { it?.let {
            xGridAdapter.setData(it)
            xGridAdapter.notifyDataSetChanged()
        } })

        binanceViewModel.savedSelected.observe(viewLifecycleOwner, { it?.let {
            val pos: Int = it.indexOf("/")
            val quotValuta: String = it.substring(0, pos)
            val baseValuta: String = it.substring(pos+1)
            val am = "Amount $quotValuta"
            val pr = "Price, $baseValuta"
            binding.amountHat.setText(am)
            binding.priceHat.setText(pr)
        } })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}