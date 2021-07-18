package com.nemscep.muffin.balances.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.databinding.FragmentEditBalancesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class EditBalancesFragment : Fragment(layout.fragment_edit_balances) {
    private val binding by viewBinding(FragmentEditBalancesBinding::bind)
    private val viewModel by viewModel<EditBalancesViewModel>()

    private lateinit var balancesAdapter: BalancesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupViews()
        binding.setupLiveData()
    }

    private fun FragmentEditBalancesBinding.setupLiveData() {
        with(viewModel) {
            balances.observe(viewLifecycleOwner) {
                balancesAdapter.submitList(it)
            }
        }
    }

    private fun FragmentEditBalancesBinding.setupViews() {
        balancesAdapter = BalancesAdapter(
            onDeleteBalance = { viewModel.delete(it) },
            onEditBalance = {
                Toast.makeText(requireContext(), "Edit balance", Toast.LENGTH_SHORT).show()
            },
            onVisibleInOverviewChange = { isChecked, balanceId ->
                viewModel.visibilityChanged(isChecked, balanceId)
            }
        )
        rvBalancesList.adapter = balancesAdapter
    }
}
