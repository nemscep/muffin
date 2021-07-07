package com.nemscep.muffin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.databinding.FragmentOnboardingBinding
import viewBinding

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {
    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSetup.setOnClickListener {
            findNavController().navigate(
                OnboardingFragmentDirections.actionOnboardingFragmentToSetupFragment()
            )
        }
    }
}
