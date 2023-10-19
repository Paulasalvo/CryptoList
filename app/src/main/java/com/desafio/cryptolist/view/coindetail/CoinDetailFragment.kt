package com.desafio.cryptolist.view.coindetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.desafio.cryptolist.R
import com.desafio.cryptolist.databinding.FragmentCoinDetailBinding
import com.desafio.cryptolist.databinding.FragmentCoinListBinding
import com.desafio.cryptolist.model.dto.CoinDTO
import com.desafio.cryptolist.model.dto.CryptoDetailDTO
import com.desafio.cryptolist.util.UtilFormat
import com.desafio.cryptolist.view.coinlist.CoinListViewModel
import java.text.SimpleDateFormat
import java.util.Date

class CoinDetailFragment : Fragment() {
    val viewModel: CoinDetailViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        val id = bundle?.getString("id")
        if (id != null) {
            viewModel.fetchCoinDetail(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCoinDetailBinding.inflate(inflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val id = bundle?.getString("id")
        if (id != null) {
            viewModel.getCoinDetail(id)
        }
        viewModel.coinDetailLiveData.observe(viewLifecycleOwner){coinEntity ->

            if (coinEntity!=null){
                binding.tvSupplyDetail.text=coinEntity.supply.toString()
                binding.tvMarketCapDetail.text=coinEntity.marketCapUsd.toString()
                binding.tvNameDetailCoin.text=coinEntity.name.toString()
                binding.tvPriceDetail.text=UtilFormat.getPriceFormatted(coinEntity.priceUsd.toDouble())
                binding.tvTimeDetail.text= convertirTimestampAHora(coinEntity.timestamp)
                Glide
                    .with(binding.root.context)
                    .load(UtilFormat.getImageURLFormatted(coinEntity.symbol))
                    .into(binding.ivDetailCoin)

            }



        }

    }

    fun convertirTimestampAHora(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("HH:mm:ss") // Formato de hora deseado
        return sdf.format(date)
    }


}