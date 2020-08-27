package mz.co.insystems.service.ekumi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import mz.co.insystems.service.ekumi.R
import mz.co.insystems.service.ekumi.activities.search.SearchActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,SearchActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
