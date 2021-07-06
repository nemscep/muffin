package com.nemscep.muffin.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentDashboardBinding
import com.nemscep.muffin.track.TrackBottomSheetDialog
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
        binding.setupToolbar()
    }

    private fun FragmentDashboardBinding.setupToolbar() {
        toolbarDashboard.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.profileOption -> Toast
                    .makeText(requireContext(), "Profile", Toast.LENGTH_SHORT)
                    .show()
                R.id.notificationOption -> Toast
                    .makeText(requireContext(), "Notifications", Toast.LENGTH_SHORT)
                    .show()
                else -> Unit
            }
            true
        }
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
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            // Manually set item change since onNavigationItemSelected overrides setupWithNavController destination change.
            NavigationUI.onNavDestinationSelected(item, navController)
            when (item.itemId) {
                R.id.track -> {
                    TrackBottomSheetDialog().show(childFragmentManager, TrackBottomSheetDialog.TAG)
                    return@setOnNavigationItemSelectedListener false
                }
                else -> true
            }
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
