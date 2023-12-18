package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjff.mousywater.R
import android.content.Intent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.pjff.mousywater.databinding.ActivitySettingsBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.User
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader

//Bueno



class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}