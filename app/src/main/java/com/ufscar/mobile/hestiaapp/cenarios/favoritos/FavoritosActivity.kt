package com.ufscar.mobile.hestiaapp.cenarios.favoritos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.adapter.CardAdapter
import com.ufscar.mobile.hestiaapp.cenarios.info_imovel.InfoImovelActivity
import com.ufscar.mobile.hestiaapp.model.Imovel
import kotlinx.android.synthetic.main.content_main.*

class FavoritosActivity : AppCompatActivity(), FavoritosContract.View {
    override fun showMessage(m: String) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
    }

    private val REQUEST_INFO = 3
    private val EXTRA_IMOVEL = "Imovel"
    private val EXTRA_SHOW_EDIT = "ShowEdit"

    val presenter: FavoritosContract.Presenter = FavoritosPresenter(this)
    var imoveis = ArrayList<Imovel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
    }

    override fun onResume() {
        super.onResume()
        presenter.onListUpdate(this)
    }

    override fun showList(list: ArrayList<Imovel>) {
        imoveis = list
        //Instantiating RecyclerView
        //Setting adapter
        val adapter = CardAdapter(this, imoveis)
        adapter.setOnClick { imovel, index ->
            val openInfo = Intent(this, InfoImovelActivity::class.java)
            openInfo.putExtra(EXTRA_IMOVEL, imovel)
            openInfo.putExtra(EXTRA_SHOW_EDIT, false)
            //Teste de animação (Apenas para >= Android 5.0)
//            val options = ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in, R.anim.abc_fade_out)
            startActivityForResult(openInfo, REQUEST_INFO)
        }
        val recyclerView = rvCard as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun hideLoading() {
        loading.visibility = View.INVISIBLE
    }
}
