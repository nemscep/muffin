package com.nemscep.muffin.auth.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.auth.ui.AuthEvent.IncorrectPin
import com.nemscep.muffin.auth.ui.AuthEvent.ToDashboard
import com.nemscep.muffin.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class AuthFragment : Fragment(layout.fragment_auth) {
    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModel<AuthViewModel>()

    private val incorrectPinText by lazy { getString(R.string.incorrect_pin) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupViews()
            setupLiveData()
        }
    }

    private fun FragmentAuthBinding.setupLiveData() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                IncorrectPin -> showIncorrectPinError()
                ToDashboard -> navigateToDashboard()
            }
        }
    }

    private fun FragmentAuthBinding.navigateToDashboard() {
        findNavController().navigate(AuthFragmentDirections.actionGlobalDashboardFragment())
    }

    private fun FragmentAuthBinding.showIncorrectPinError() {
        tilPin.error = incorrectPinText
    }

    private fun FragmentAuthBinding.setupViews() {
        tilPin.editText?.addTextChangedListener { text ->
            btnAuthenticate.isEnabled = !text.isNullOrEmpty()
        }
        btnAuthenticate.setOnClickListener {
            val pin = tilPin.editText?.text.toString().toInt()
            viewModel.authenticate(pin)
        }
    }
}
