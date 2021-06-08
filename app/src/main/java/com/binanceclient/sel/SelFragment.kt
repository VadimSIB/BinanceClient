package com.binanceclient.sel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.binanceclient.BinanceViewModel
import com.binanceclient.CryptoPairType
import com.binanceclient.databinding.FragmentSelBinding


class SelFragment : Fragment() {
    private var _binding: FragmentSelBinding? = null
    private val binding get() = _binding!!
    private var spinner: Spinner? = null
    private val binanceViewModel: BinanceViewModel by activityViewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelBinding.inflate(inflater, container, false)
        val root: View = binding.getRoot()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListenerOnSpinnerItemSelection()
    }

    fun addListenerOnSpinnerItemSelection() {
        spinner = binding.spinnerCryptoPair
        spinner?.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, CryptoPairType.getNames()))
        spinner?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                val item: CryptoPairType = CryptoPairType.fromPairName(spinner?.getSelectedItem().toString())
                binanceViewModel.selectPair(item._name)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}