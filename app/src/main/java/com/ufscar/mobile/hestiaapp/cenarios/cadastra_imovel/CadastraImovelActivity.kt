package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis.MeusImoveisActivity
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments.EnderecoCadastroFragment
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments.InformacoesCadastroFragment
import kotlinx.android.synthetic.main.fragment_cadastra_info_imovel.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask

class CadastraImovelActivity : AppCompatActivity(), CadastraImovelContract.View,
    InformacoesCadastroFragment.onFragmentInteractionListener {

    val presenter: CadastraImovelContract.Presenter = CadastraImovelPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_imovel)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, InformacoesCadastroFragment())
                .commit()
    }

    override fun onProsseguirInteraction(infoMap: HashMap<String,Any>) {
        val fragment = EnderecoCadastroFragment.newInstance(infoMap)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun insertSuccess() {
        val intentDono = Intent(this, MeusImoveisActivity::class.java)
        intentDono.newTask().clearTask()
        startActivity(intentDono)
        finish()
    }
}
