package com.nemscep.muffin.setup

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.FragmentSetupBinding
import viewBinding

class SetupFragment : Fragment(R.layout.fragment_setup){
    private val binding by viewBinding(FragmentSetupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupCurrencyDropdown()
    }

    private fun FragmentSetupBinding.setupCurrencyDropdown(){
        val items = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_currency, items)
        (tilCurrency.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}
