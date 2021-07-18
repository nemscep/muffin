package com.nemscep.muffin.balances.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ActivityEditBalancesBinding
import viewBinding

class EditBalancesActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityEditBalancesBinding::inflate)

    private val navController: NavController
        get() = run {
            val host = supportFragmentManager
                .findFragmentById(R.id.editActivityNavHost) as NavHostFragment
            return host.navController
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.setupToolbar()
    }

    private fun ActivityEditBalancesBinding.setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(emptySet())
        tbEditBalances.setupWithNavController(navController, appBarConfiguration)
        tbEditBalances.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.optionAddBalance -> Toast.makeText(
                    this@EditBalancesActivity,
                    "Adding balance",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Unit
            }
            true
        }
        tbEditBalances.setNavigationOnClickListener {
            if (!navController.navigateUp()) onBackPressed()
        }
    }
}
