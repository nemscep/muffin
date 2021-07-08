package com.nemscep.muffin.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentOverviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class OverviewFragment : Fragment(layout.fragment_overview) {
    private val binding by viewBinding(FragmentOverviewBinding::bind)
    private val viewModel by viewModel<OverviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupLiveData()
            setupViews()
        }
    }

    private fun FragmentOverviewBinding.setupViews() {
        binding.btnSeeDetails.setOnClickListener {
            findNavController().navigate(R.id.balanceDetails)
        }
    }

    private fun FragmentOverviewBinding.setupLiveData() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            tvName.text = profile.name
        }
    }
}
