package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import com.ufscar.mobile.hestiaapp.util.StorageUtil

class CadastraImovelPresenter(val view: CadastraImovelContract.View): CadastraImovelContract.Presenter  {
    override fun onInsertImovelWithPhoto(title: String, tipo: String, cidade: String, rua: String, numero: Int, complemento: String, referencia: String, max: Int, min: Int, preco: Int, quartos: Int, banheiros: Int, salas: Int, cozinhas: Int, vaga: Int, descricao: String, imageByte: ByteArray) {
        view.showProgressBar()
        val uidDono = FirebaseAuth.getInstance().currentUser?.uid
        val emailDono = FirebaseAuth.getInstance().currentUser?.email
        StorageUtil.uploadImovelPhoto(imageByte) {imagePath ->
            FirestoreImovelUtil.insertImovel(title, tipo, cidade, rua, numero, complemento, referencia, max, min, preco, quartos, banheiros, salas, cozinhas, vaga, descricao, uidDono!!, emailDono!!, imagePath)
            view.hideProgressBar()
            view.saveSuccess()
        }
    }


    override fun onInsertImovel(title: String, tipo: String, cidade: String, rua: String, numero: Int, complemento: String, referencia: String, max: Int, min: Int, preco: Int, quartos: Int, banheiros: Int, salas: Int, cozinhas: Int, vaga: Int, descricao: String) {
        val uidDono = FirebaseAuth.getInstance().currentUser?.uid
        val emailDono = FirebaseAuth.getInstance().currentUser?.email
        FirestoreImovelUtil.insertImovel(title, tipo, cidade, rua, numero, complemento, referencia, max, min, preco, quartos, banheiros, salas, cozinhas, vaga, descricao, uidDono!!, emailDono!!, null)
    }

}