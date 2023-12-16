package com.pjff.mousywater.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityLoginBinding
import com.pjff.mousywater.databinding.ActivityMainBinding
import com.pjff.mousywater.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 4: Get the stored username from the Android SharedPreferences.
        // START
        // Create an instance of Android SharedPreferences
        val sharedPreferences =
            getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
        // Set the result to the tv_main.
        binding.tvMain.text= "The logged in user is $username."
        // END
    }
}