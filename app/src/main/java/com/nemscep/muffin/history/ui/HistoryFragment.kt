package com.nemscep.muffin.history.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.FragmentHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {
    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel by viewModel<HistoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llNoTransactions.isVisible = true
    }

}
