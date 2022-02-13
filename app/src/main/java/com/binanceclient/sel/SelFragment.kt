package com.binanceclient.sel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.binanceclient.CryptoPairType
import com.binanceclient.databinding.FragmentSelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelFragment : Fragment() {
    private var _binding: FragmentSelBinding? = null
    private val binding get() = _binding!!
    private var spinner: Spinner? = null
    private val selViewModel: SelViewModel by  activityViewModels()

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
        spinner?.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            CryptoPairType::class.sealedSubclasses.map{it.objectInstance?.name}))
        var spinnerTouched = false
        spinner!!.setOnTouchListener (object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                v?.performClick()
                spinnerTouched = true
                return v?.onTouchEvent(event) ?: true
            }
        })

        spinner?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (spinnerTouched) {
                    selViewModel.selectPair(spinner?.getSelectedItem().toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}