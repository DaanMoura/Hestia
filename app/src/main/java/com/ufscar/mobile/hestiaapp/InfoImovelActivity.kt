package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import kotlinx.android.synthetic.main.activity_info_imovel.*

class InfoImovelActivity : AppCompatActivity() {
    private val EXTRA_IMOVEL = "Imovel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_imovel)
        FirestoreUserUtil.getCurrentUser({ user ->
            if(user.dono) fab_edit.visibility = View.VISIBLE
            else fab_favorite.visibility = View.VISIBLE
        }, this)


        val imovel = getIntent().getExtras().getSerializable(EXTRA_IMOVEL) as? Imovel

        loadInfo(imovel)

        info_end_line.setOnClickListener {
            val showOnMap = Intent(Intent.ACTION_VIEW)
            showOnMap.data = Uri.parse("geo:0,0?q=${imovel?.endereco}")
            if(showOnMap.resolveActivity(packageManager) != null) {
                startActivity(showOnMap)
            } else {
                Toast.makeText(this, "Impossível mostrar no mapa", Toast.LENGTH_SHORT).show()
            }
        }
        //TODO: fazer ação "Conversar com o Dono"
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
