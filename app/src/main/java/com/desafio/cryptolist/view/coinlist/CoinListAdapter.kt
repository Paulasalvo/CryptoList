package com.desafio.cryptolist.view.coinlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desafio.cryptolist.databinding.ItemCoinBinding
import com.desafio.cryptolist.model.database.CoinEntity
import com.desafio.cryptolist.model.dto.CoinDTO
import com.desafio.cryptolist.util.UtilFormat

class CoinListAdapter: RecyclerView.Adapter<CoinListAdapter.CoinItemViewHolder>() {
    var coinList:List<CoinEntity> = mutableListOf()
    var onClickItem: (String)->Unit={}

    class CoinItemViewHolder(val binding: ItemCoinBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CoinItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return coinList.size
    }

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        val coin =coinList[position]
        holder.binding.tvNameCoin.text = coin.name
        holder.binding.tvPriceCoin.text = UtilFormat.getPriceFormatted(coin.priceUsd.toDouble())
        Glide
            .with(holder.binding.root.context)
            .load(UtilFormat.getImageURLFormatted(coin.symbol))
            .into(holder.binding.ivCoin)

        holder.binding.llCoinItem.setOnClickListener{
            onClickItem(coin.id)
        }

    }


    fun updateList(list: List<CoinEntity>){
        coinList = list
        notifyDataSetChanged()
    }
}