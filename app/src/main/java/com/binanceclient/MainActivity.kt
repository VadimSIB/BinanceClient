package com.binanceclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.binanceclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)

        binding.navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_bid -> {
                    navController.navigate(R.id.navigation_bid)
                    binding.navView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.bottom_nav_color1)
                    binding.navView.itemTextColor = ContextCompat.getColorStateList(this, R.color.bottom_nav_color1)
                }
                R.id.navigation_ask -> {
                    navController.navigate(R.id.navigation_ask)
                    binding.navView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.bottom_nav_color2)
                    binding.navView.itemTextColor = ContextCompat.getColorStateList(this, R.color.bottom_nav_color2)
                }
                R.id.navigation_inf -> {
                    navController.navigate(R.id.navigation_inf)
                    binding.navView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.bottom_nav_color3)
                    binding.navView.itemTextColor = ContextCompat.getColorStateList(this, R.color.bottom_nav_color3)
                }
            }
            true
        }
        binding.navView.selectedItemId = R.id.navigation_bid
    }
}



