package com.ufscar.mobile.hestiaapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.ufscar.mobile.hestiaapp.model.Imovel
import kotlinx.android.synthetic.main.card_layout.view.*

// imovelList is the data source of this adapter
class CardAdapter(val imovelList: ArrayList<Imovel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    //Just link the view holder to the actual view of xml layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    //This return the size of list
    override fun getItemCount(): Int {
        return imovelList.size
    }

    //This is to bind data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imovel: Imovel = imovelList[position]
        holder.title.text = "$position ${imovel.title}"
        holder.moradores.text = "Moradores: ${imovel.min}/${imovel.max}"
        holder.interessados.text = "Interessados: ${imovel.interessados}"
        holder.foto.setImageBitmap(imovel.bmFoto)
        holder.preco.text = "R$ ${imovel.preco},00"
        holder.comodos.text = "${imovel.quartos} quartos, ${imovel.banheiros} banheiros, ${imovel.salas} salas"
        holder.descricao.text = imovel.descricao

        //Fixing margin of the first card
        if (position == 0) {
            val params: ViewGroup.MarginLayoutParams = holder.card.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(32, 72, 32, 80) // I dunno why worked with top: 72 and bottom: 80
            holder.card.layoutParams = params
        }

    }

    //Create a view holder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.tvTitle as TextView
        val moradores = itemView.tvMoradores as TextView
        val interessados = itemView.tvInteressados as TextView
        val foto = itemView.cardFoto as ImageView
        val preco = itemView.tvPreco as TextView
        val comodos = itemView.tvComodos as TextView
        val descricao = itemView.tvDesc as TextView
        val interessado = itemView.checkInteressados as CheckBox
        val card = itemView.card as ViewGroup
    }

    //TODO: create onItemClickListener()
}