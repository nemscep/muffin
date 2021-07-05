package com.nemscep.muffin.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentDashboardBinding
import viewBinding

class DashboardFragment : Fragment(layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupNavController()
    }

    private fun FragmentDashboardBinding.setupNavController() {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.dashboardNavHost) as NavHostFragment

        bottomNavigation.setupWithNavController(navHostFragment.navController)
    }
}
