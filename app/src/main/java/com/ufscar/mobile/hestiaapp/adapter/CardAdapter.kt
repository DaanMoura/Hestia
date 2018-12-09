package com.ufscar.mobile.hestiaapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.model.Imovel
import kotlinx.android.synthetic.main.card_layout.view.*

// imovelList is the data source of this adapter
class CardAdapter(val imovelList: ArrayList<Imovel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    var onClickListener: ((imovel: Imovel, index: Int) -> Unit)? = null

    //Just link the view holder to the actual view of xml layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    //This return the size of list
    override fun getItemCount(): Int {
        return imovelList.size
    }

    fun setOnClick(click: ((imovel: Imovel, index: Int) -> Unit)) {
        this.onClickListener = click
    }

    //This is to bind data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imovel: Imovel = imovelList[position]
        holder.bindView(imovel, onClickListener)
    }

    //Create a view holder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(imovel: Imovel, onClickListener: ((imovel: Imovel, index: Int) -> Unit)?) {
            if(imovel.title != null) {
                itemView.tvTitle.text = imovel.title
            } else {
                itemView.tvTitle.text = imovel.tipo
            }
            itemView.tvMoradores.text = "Moradores: ${imovel.min}/${imovel.max}"
            itemView.tvInteressados.text = "Interessados: ${imovel.interessados}"
            itemView.cardFoto.setImageBitmap(imovel.bmFoto)
            itemView.tvPreco.text = "R$ ${imovel.preco},00"
            itemView.tvComodos.text = "${imovel.quartos} quartos, ${imovel.banheiros} banheiros, ${imovel.salas} salas, ${imovel.cozinhas} cozinhas"
            itemView.tvDesc.text = imovel.descricao
            itemView.checkInteressados as CheckBox

            if (onClickListener != null) {
                itemView.setOnClickListener {
                    onClickListener.invoke(imovel, adapterPosition)
                }
            }
        }
    }

    //TODO: create onItemClickListener()
}