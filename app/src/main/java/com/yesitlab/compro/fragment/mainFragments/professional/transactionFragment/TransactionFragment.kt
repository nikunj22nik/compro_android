package com.yesitlab.compro.fragment.mainFragments.professional.transactionFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.TransactionAdapter
import com.yesitlab.compro.databinding.FragmentTransactionBinding
import com.yesitlab.compro.model.TransactionModel


class TransactionFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding
    private var transactionAdapter: TransactionAdapter? = null
    private var list: MutableList<TransactionModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        transactionAdapter = TransactionAdapter(mutableListOf(), requireActivity())
        binding.recyclerViewTransaction.setAdapter(transactionAdapter)
        lists()
        transactionAdapter?.updateItem(list)


    }

    private fun lists() {
        repeat(6) {
            list.add(TransactionModel("Phone pay", "5:13 Pm 24/Sep/2024", "$65"))
        }

    }


}