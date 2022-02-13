package com.binanceclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.binanceclient.databinding.FragmentXBinding
import com.binanceclient.sel.SelViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
abstract class XFragment : Fragment() {

    val selViewModel: SelViewModel by activityViewModels()
    val binanceViewModel: BinanceViewModel by viewModels()
    private var color: Int = 0
    protected abstract val clr: Int
    protected abstract fun getXOrderBook(): LiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>
    private var _binding: FragmentXBinding? = null
    private val binding get() = _binding!!
    private lateinit var xGridAdapter: XAdapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        color =  ResourcesCompat.getColor(requireContext().resources, clr,null)
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
        xOrderBook.observe(viewLifecycleOwner) {
            it?.let {
                xGridAdapter.setData(it)
                xGridAdapter.notifyDataSetChanged()
            }
        }

        selViewModel.savedSelected.observe(viewLifecycleOwner) {
            it?.let {
                val pos: Int = it.indexOf("/")
                val quotValuta: String = it.substring(0, pos)
                val baseValuta: String = it.substring(pos + 1)
                val am = "Amount $quotValuta"
                val pr = "Price, $baseValuta"
                binding.amountHat.setText(am)
                binding.priceHat.setText(pr)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}