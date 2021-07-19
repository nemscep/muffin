package com.nemscep.muffin.track.ui.expense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentTrackExpenseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class TrackExpenseFragment : Fragment(layout.fragment_track_expense) {
    private val binding by viewBinding(FragmentTrackExpenseBinding::bind)
    private val viewModel by viewModel<TrackExpenseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
