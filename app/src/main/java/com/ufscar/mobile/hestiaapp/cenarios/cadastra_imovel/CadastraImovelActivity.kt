package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis.MeusImoveisActivity
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import kotlinx.android.synthetic.main.fragment_cadastra_info_imovel.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask

class CadastraImovelActivity : AppCompatActivity(), CadastraImovelContract.View {

    private val REQUIRED_FIELD = "Campo obrigatório"
    val presenter: CadastraImovelContract.Presenter = CadastraImovelPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_imovel)

        prosseguir.setOnClickListener {
           //FIXME: o erro só aparece no último campo do teste
            if(verifyRequiredFields()) {
                presenter.onInsertImovel(edtTipo.text.toString(),
                        edtMoradores.text.toString().toInt(),
                        edtPreco.text.toString().toInt(),
                        edtQuartos.text.toString().toInt(),
                        edtBanheiros.text.toString().toInt(),
                        edtSalas.text.toString().toInt(),
                        edtVagas.text.toString().toInt(),
                        edtDesc.text.toString(),
                        edtEnd.text.toString())
            }
        }
    }

    fun verifyRequiredFields(): Boolean {
        var result = true
        if(TextUtils.isEmpty(edtEnd.text)) {
            edtEnd.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtPreco.text)) {
            edtPreco.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtTipo.text)) {
            edtTipo.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtDesc.text)) {
            edtDesc.error = REQUIRED_FIELD
            result = false
        }
        return result
    }

    override fun insertSuccess() {
        val intentDono = Intent(this, MeusImoveisActivity::class.java)
        intentDono.newTask().clearTask()
        startActivity(intentDono)
        finish()
    }
}
