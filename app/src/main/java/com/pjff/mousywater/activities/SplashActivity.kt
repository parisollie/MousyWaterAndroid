package com.pjff.mousywater.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.content.Intent
import android.os.Handler
import com.pjff.mousywater.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // TODO Step 6: Add the below code to make the screen as full screen and finish the activity after 2500 milliseconds.
        // START

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Adding the handler to after the a task after some delay.
        // It is deprecated in the API level 30.
        Handler().postDelayed(
            {
                // Launch the Main Activity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish() // Call this when your activity is done and should be closed.
            },
            2500
        ) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.

        // TODO Step 2: Use the custom font to show the app name in the splash screen.
        // This is used to get the file from the assets folder and set it to the title textView.
        // START
        // TODO Step 5: Comment the Typeface code here once you have replaced the TextView with custom class in the XML file.
        // START
       /* val typeface: Typeface =
            Typeface.createFromAsset(assets, "Montserrat-Bold.ttf")
        tv_app_name.typeface = typeface*/
        // END
        // END


    }
}