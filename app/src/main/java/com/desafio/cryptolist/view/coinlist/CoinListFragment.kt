package com.desafio.cryptolist.view.coinlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafio.cryptolist.R
import com.desafio.cryptolist.databinding.FragmentCoinListBinding
import com.desafio.cryptolist.view.coindetail.CoinDetailFragment

class CoinListFragment : Fragment() {
    val viewModel: CoinListViewModel by viewModels()
    private lateinit var binding: FragmentCoinListBinding
    private lateinit var adapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCoinListBinding.inflate(inflater)
        binding.userName.doOnTextChanged { text, _, _, _ ->
            saveInPreferences(text.toString())
        }
        getFromPreferences()?.let {
            binding.userName.setText(it)
        }
        return binding.root
    }

    private fun saveInPreferences(text: String) {
        context?.let {
            val editor = it.getSharedPreferences("userPrefs", Context.MODE_PRIVATE).edit()
            editor.putString("username", text)
            editor.commit()

        }
    }
    private fun getFromPreferences(): String {
        val prefs = context?.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        return prefs?.getString("username", "Usuario") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= CoinListAdapter()
        binding.rvCoinList.layoutManager = LinearLayoutManager(context)
        binding.rvCoinList.adapter = adapter
        adapter.onClickItem ={id ->
            activity?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CoinDetailFragment().apply {
                        arguments = Bundle().apply { putString("id", id) }
                    })
                    .addToBackStack("detail").commit()
            }

        }

        viewModel.coinListLiveData.observe(viewLifecycleOwner){
            adapter.updateList(it)

        }
    }

}