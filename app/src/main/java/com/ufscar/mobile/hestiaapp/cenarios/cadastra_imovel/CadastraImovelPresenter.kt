package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.R.id.*
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil

class CadastraImovelPresenter(val view: CadastraImovelContract.View): CadastraImovelContract.Presenter  {
    override fun onInsertImovel(tipo: String,moradores: Int,preco: Int,quartos: Int,
                       banheiros: Int,salas: Int,vagas: Int,desc: String,end:String) {
        FirestoreImovelUtil.insertImovel(
                tipo,moradores,
                0,
                0,
                preco,quartos,banheiros,salas,vagas,desc,end,
                FirebaseAuth.getInstance().uid ?: "")
        view.insertSuccess()
    }
}