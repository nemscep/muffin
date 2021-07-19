package com.nemscep.muffin.track.ui.topup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.FragmentTrackTopupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class TrackTopupFragment : Fragment(R.layout.fragment_track_topup) {
    private val binding by viewBinding(FragmentTrackTopupBinding::bind)
    private val viewModel by viewModel<TrackTopupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
