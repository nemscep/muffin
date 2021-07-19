package com.nemscep.muffin.track.ui.topup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ActivityTrackTopupBinding
import viewBinding

class TrackTopupActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTrackTopupBinding::inflate)

    private val navController: NavController
        get() = run {
            val host = supportFragmentManager
                .findFragmentById(R.id.trackTopupNavHost) as NavHostFragment
            return host.navController
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.setupToolbar()
    }

    private fun ActivityTrackTopupBinding.setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(emptySet())
        tbTrackTopup.setupWithNavController(navController, appBarConfiguration)
        tbTrackTopup.setOnMenuItemClickListener { item ->
            // TODO()
            true
        }
        tbTrackTopup.setNavigationOnClickListener {
            if (!navController.navigateUp()) onBackPressed()
        }
    }
}
