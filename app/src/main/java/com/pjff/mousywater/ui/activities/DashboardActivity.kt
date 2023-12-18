package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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
        // Update the background color of the action bar as per our design requirement.
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@DashboardActivity,
                R.drawable.app_gradient_color_background
            )
        )
        // END

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