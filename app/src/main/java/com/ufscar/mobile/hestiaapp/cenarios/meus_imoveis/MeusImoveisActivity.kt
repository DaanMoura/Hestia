package com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.adapter.CardAdapter
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.CadastraImovelActivity
import com.ufscar.mobile.hestiaapp.cenarios.info_imovel.InfoImovelActivity
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_dono.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import kotlin.collections.ArrayList

class MeusImoveisActivity : AppCompatActivity(), MeusImoveisContract.View {
    //Sign in request code
    private val RC_SIGN_IN = 1
    private val REQUEST_INFO = 3
    private val REQUEST_PERFIL = 4
    private val EXTRA_IMOVEL = "Imovel"

    var imoveis = ArrayList<Imovel>()
    val presenter: MeusImoveisContract.Presenter = MeusImoveisPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dono_main)
        setSupportActionBar(toolbar_dono)

        fab_add.setOnClickListener {
            startActivity(intentFor<CadastraImovelActivity>())
        }
    }

    override fun showList(list: ArrayList<Imovel>) {
        imoveis = list
        //Instantiating RecyclerView
        val recyclerView = rvCard as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //Setting adapter
        val adapter = CardAdapter(imoveis)
        adapter.setOnClick { imovel, index ->
            val openInfo = Intent(this, InfoImovelActivity::class.java)
            openInfo.putExtra(EXTRA_IMOVEL, imovel)
            //Teste de animação (Apenas para >= Android 5.0)
//            val options = ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in, R.anim.abc_fade_out)
            startActivityForResult(openInfo, REQUEST_INFO)
        }
        recyclerView.adapter = adapter
    }

    override fun hideLoading() {
        loading.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        presenter.onListUpdate()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //Hadling the options item (We should keep it?)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
