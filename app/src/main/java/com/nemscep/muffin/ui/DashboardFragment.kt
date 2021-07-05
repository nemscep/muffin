package com.nemscep.muffin.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentDashboardBinding
import viewBinding

class DashboardFragment : Fragment(layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val navController: NavController
        get() = run {
            val host = childFragmentManager
                .findFragmentById(R.id.dashboardNavHost) as NavHostFragment
            return host.navController
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupNavController()
        binding.setupDestinationChangeListener()
    }

    private fun FragmentDashboardBinding.setupNavController() {
        // Setup bottom navigation
        bottomNavigation.setupWithNavController(navController)

        // Setup action bar
        val appBarConfiguration = AppBarConfiguration(ROOT_DESTINATIONS)
        toolbarDashboard.setupWithNavController(navController, appBarConfiguration)
    }

    private fun FragmentDashboardBinding.setupDestinationChangeListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(newDestination = destination)
        }
    }

    private fun FragmentDashboardBinding.handleDestinationChange(newDestination: NavDestination) {
        when (newDestination.id) {
            in ROOT_DESTINATIONS -> {
                bottomNavigation.isVisible = true
            }
            else -> {
                bottomNavigation.isVisible = false
            }
        }
    }

    companion object {
        val ROOT_DESTINATIONS = setOf(R.id.overviewFragment, R.id.historyFragment)
    }
}
