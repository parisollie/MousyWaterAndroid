package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityDashboardBinding
import com.pjff.mousywater.ui.fragments.DashboardFragment
import com.pjff.mousywater.ui.fragments.OrdersFragment
import com.pjff.mousywater.ui.fragments.ProductsFragment

//Bueno
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(DashboardFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(DashboardFragment())
                R.id.profile -> replaceFragment(OrdersFragment())
                R.id.settings -> replaceFragment(ProductsFragment())

                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}