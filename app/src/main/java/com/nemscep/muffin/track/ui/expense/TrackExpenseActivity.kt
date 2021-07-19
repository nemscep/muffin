package com.nemscep.muffin.track.ui.expense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ActivityTrackExpenseBinding
import viewBinding

class TrackExpenseActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTrackExpenseBinding::inflate)

    private val navController: NavController
        get() = run {
            val host = supportFragmentManager
                .findFragmentById(R.id.trackExpenseNavHost) as NavHostFragment
            return host.navController
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.setupToolbar()
    }

    private fun ActivityTrackExpenseBinding.setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(emptySet())
        tbTrackExpense.setupWithNavController(navController, appBarConfiguration)
        tbTrackExpense.setOnMenuItemClickListener { item ->
            // TODO()
            true
        }
        tbTrackExpense.setNavigationOnClickListener {
            if (!navController.navigateUp()) onBackPressed()
        }
    }
}
