package com.ufscar.mobile.hestiaapp.cenarios.info_imovel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.main.MainActivity
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.GlideApp
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_info_imovel.*

class InfoImovelActivity : AppCompatActivity(), InfoImovelContract.View {
    override fun fillFavorite() {
        fab_favorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite))
    }

    override fun unfillFavorite() {
        fab_favorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_favorite_border))
    }

    private val EXTRA_IMOVEL = "Imovel"
    private val EXTRA_SHOW_EDIT = "ShowEdit"
    val presenter: InfoImovelContract.Presenter = InfoImovelPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_imovel)

        val imovel = getIntent().getExtras().getSerializable(EXTRA_IMOVEL) as? Imovel

        val showEdit = intent.extras.getSerializable(EXTRA_SHOW_EDIT) as Boolean
        presenter.onLoadFab(this, showEdit, imovel)

        loadInfo(imovel)

        info_end_text.setOnClickListener {
            val showOnMap = Intent(Intent.ACTION_VIEW)
            showOnMap.data = Uri.parse("geo:0,0?q=${imovel?.rua} ${imovel?.numero} ${imovel?.cidade}")
            if(showOnMap.resolveActivity(packageManager) != null) {
                startActivity(showOnMap)
            } else {
                Toast.makeText(this, "Impossível mostrar no mapa", Toast.LENGTH_SHORT).show()
            }
        }
        //TODO: fazer ação "Conversar com o Dono"

        fab_favorite.setOnClickListener {
            presenter.onFavorite(imovel, this)
        }
    }

    private fun loadInfo(imovel: Imovel?) {
        if (imovel != null) {
            if(!imovel.title.isNullOrEmpty()) {
                info_title.text = imovel.title
                info_tipo.text = imovel.tipo
            } else {
                info_tipo.visibility = View.INVISIBLE
                info_title.text = imovel.tipo
            }
            info_price.text = "R$ ${imovel.preco},00"
            info_moradores.text = "Moradores: ${imovel.min}/${imovel.max}"
            info_interessados.text = "Interessados: ${imovel.interessados}"
            info_comodos.text = "${imovel.quartos} quartos, ${imovel.banheiros} banheiros, ${imovel.salas} salas, ${imovel.cozinhas} cozinhas"
            info_desc.text = imovel.descricao
            if(!imovel.referencia.isNullOrEmpty()) {
                info_referencia.text = "Ponto de referência: ${imovel.referencia}"
            } else {
                info_referencia.visibility = View.INVISIBLE
            }
            info_end_text.text = "${imovel.rua}, nº ${imovel.numero} ${imovel.complemento} - ${imovel.cidade}"

            if(imovel.picturePath != null) {
                GlideApp.with(this)
                        .load(StorageUtil.pathToReference(imovel.picturePath))
                        .centerCrop()
                        .into(imageImovel)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val volta = Intent(this, MainActivity::class.java)
//        val options = ActivityOptions.makeCustomAnimation(this, R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
        setResult(Activity.RESULT_OK, volta)
        finish()
    }

    override fun showFabEdit() {
        fab_edit.visibility = View.VISIBLE
    }

    override fun showFabFavorite() {
        fab_favorite.visibility = View.VISIBLE
    }
}
