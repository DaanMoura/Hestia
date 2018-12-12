package com.ufscar.mobile.hestiaapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.GlideApp
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.card_layout.view.*

// imovelList is the data source of this adapter
class CardAdapter(val context: Context, val imovelList: ArrayList<Imovel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

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
        holder.bindView(context, imovel, onClickListener)
    }

    //Create a view holder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context,imovel: Imovel, onClickListener: ((imovel: Imovel, index: Int) -> Unit)?) {
            if(!imovel.title.isNullOrEmpty()) {
                itemView.tvTitle.text = imovel.title
            } else {
                itemView.tvTitle.text = imovel.tipo
            }
            itemView.tvMoradores.text = "Moradores: ${imovel.min}/${imovel.max}"
            itemView.tvInteressados.text = "Interessados: ${imovel.interessados}"
            itemView.tvPreco.text = "R$ ${imovel.preco},00"
            itemView.tvComodos.text = formatarComodos(imovel)
            itemView.tvDesc.text = imovel.descricao
            itemView.checkInteressados as CheckBox

            if(imovel.picturePath != null) {
                GlideApp.with(context)
                        .load(StorageUtil.pathToReference(imovel.picturePath))
                        .centerCrop()
                        .into(itemView.cardFoto)
            }

            if (onClickListener != null) {
                itemView.setOnClickListener {
                    onClickListener.invoke(imovel, adapterPosition)
                }
            }
        }

        fun formatarComodos(imovel: Imovel): String {
            var str = ""

            if(imovel.quartos == 1)
                str = str + "1 quarto"
            else if(imovel.quartos > 1)
                str = str + "${imovel.quartos} quartos"

            if(imovel.banheiros == 1)
                str = str + ", 1 banheiro"
            else if(imovel.banheiros > 1)
                str = str + ", ${imovel.banheiros} banheiros"

            if(imovel.salas == 1)
                str = str + ", 1 sala"
            else if(imovel.salas > 1)
                str = str + ", ${imovel.salas} salas"

            if(imovel.cozinhas == 1)
                str = str + ", 1 cozinha"
            else if(imovel.cozinhas > 1)
                str = str + ", ${imovel.cozinhas} cozinhas"

            if(imovel.vaga == 1)
                str = str + " e 1 vaga na garagem"
            else if(imovel.vaga > 1)
                str = str + " e ${imovel.vaga} vagas na garagem"

            return str
        }
    }

    //TODO: create onItemClickListener()
}