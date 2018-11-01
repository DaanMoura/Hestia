package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ufscar.mobile.hestiaapp.fragments.CadastraInfoImovelFragment
import com.ufscar.mobile.hestiaapp.fragments.CadastroFotosImovelFragment
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import kotlinx.android.synthetic.main.fragment_cadastra_info_imovel.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask

class CadastraImovelActivity : AppCompatActivity() {

//    lateinit var partOneFragment: CadastraInfoImovelFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_imovel)

//        prosseguir.setOnClickListener {
//            val cadastraFotos = CadastroFotosImovelFragment()
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, cadastraFotos)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }

        prosseguir.setOnClickListener {
            FirestoreImovelUtil.insertImovel(
                    edtTipo.text.toString(),
                    edtMoradores.text.toString().toInt(),
                    0,
                    0,
                    edtPreco.text.toString().toInt(),
                    edtQuartos.text.toString().toInt(),
                    edtBanheiros.text.toString().toInt(),
                    edtSalas.text.toString().toInt(),
                    edtVagas.text.toString().toInt(),
                    edtDesc.text.toString(),
                    edtEnd.text.toString())

            val intentDono = Intent(this, DonoMainActivity::class.java)
            intentDono.newTask().clearTask()
            startActivity(intentDono)
            finish()
        }

    }
}
