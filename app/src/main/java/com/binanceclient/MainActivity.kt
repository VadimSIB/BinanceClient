package com.binanceclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.binanceclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val KEY_SELECTED_ID = "KEY_SELECTED_ID"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SELECTED_ID, binding.navView.selectedItemId.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        var col = R.color.bottom_nav_color1
        var but = R.id.navigation_bid
        binding.navView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.navigation_bid -> {
                    col = R.color.bottom_nav_color1
                    but = R.id.navigation_bid
                }
                R.id.navigation_ask -> {
                    col = R.color.bottom_nav_color2
                    but = R.id.navigation_ask
                }
                R.id.navigation_inf -> {
                    col = R.color.bottom_nav_color3
                    but = R.id.navigation_inf
                }
            }
            navController.navigate(but)
            binding.navView.itemIconTintList = ContextCompat.getColorStateList(this, col)
            binding.navView.itemTextColor = ContextCompat.getColorStateList(this, col)
            true
        }
        val activId: Int
        if (savedInstanceState == null) {
            activId = R.id.navigation_bid
        }
        else {
            activId = savedInstanceState.getString(KEY_SELECTED_ID)?.toInt()!!
        }
        binding.navView.selectedItemId = activId
    }
}



