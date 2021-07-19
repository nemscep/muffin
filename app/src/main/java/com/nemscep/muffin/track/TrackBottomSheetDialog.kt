package com.nemscep.muffin.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.DialogTrackBinding

class TrackBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogTrackBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupViews()
    }

    private fun DialogTrackBinding.setupViews() {
        btnTrackExpense.setOnClickListener {
            findNavController().navigate(R.id.trackExpenseActivity)
            dismiss()
        }
        btnTrackTopup.setOnClickListener {
            findNavController().navigate(R.id.trackTopupActivity)
            dismiss()
        }
    }

    companion object {
        val TAG = "TrackBottomSheetDialog"
    }
}
