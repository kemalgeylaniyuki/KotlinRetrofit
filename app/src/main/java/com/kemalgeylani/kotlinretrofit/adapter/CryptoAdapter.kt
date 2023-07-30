package com.kemalgeylani.kotlinretrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kemalgeylani.kotlinretrofit.databinding.RecyclerRowBinding
import com.kemalgeylani.kotlinretrofit.model.CryptoModel


class CryptoAdapter(var cryptoModelList: ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#DFFF00","#FFBF00","#FF7F50","#DE3163","#9FE2BF","#40E0D0","#6495ED","#CCCCFF")

    class CryptoHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoModel : CryptoModel, colors : Array<String>, position : Int, listener: Listener){

            binding.currencyText.text = cryptoModel.currency
            binding.priceText.text = cryptoModel.price
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return cryptoModelList.count()
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(cryptoModelList[position],colors,position,listener)
    }

}