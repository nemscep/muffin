package com.nemscep.muffin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.databinding.FragmentOverviewBinding
import viewBinding

class OverviewFragment : Fragment(R.layout.fragment_overview) {
    private val binding by viewBinding(FragmentOverviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSeeDetails.setOnClickListener {
            findNavController().navigate(R.id.balanceDetails)
        }
    }
}
