package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ufscar.mobile.hestiaapp.model.Imovel
import kotlinx.android.synthetic.main.activity_info_imovel.*

class InfoImovelActivity : AppCompatActivity() {
    private val EXTRA_IMOVEL = "Imovel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_imovel)

        val imovel = getIntent().getExtras().getSerializable(EXTRA_IMOVEL) as? Imovel

        loadInfo(imovel)
    }

    private fun loadInfo(imovel: Imovel?) {
        if (imovel != null) {
            info_title.text = imovel.title
            info_price.text = "R$ ${imovel.preco},00"
            info_moradores.text = "Moradores: ${imovel.min}/${imovel.max}"
            info_interessados.text = "Interessados: ${imovel.interessados}"
            info_comodos.text = "${imovel.quartos} quartos, ${imovel.banheiros} banheiros, ${imovel.salas} salas"
            info_desc.text = imovel.descricao
            info_end_text.text = imovel.endereco
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val volta = Intent(this, MainActivity::class.java)
//        val options = ActivityOptions.makeCustomAnimation(this, R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
        setResult(Activity.RESULT_OK, volta)
        finish()

    }
}
