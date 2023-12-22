package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityCartListBinding

class CartListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCartListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}