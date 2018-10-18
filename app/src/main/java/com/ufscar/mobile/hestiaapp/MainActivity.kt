package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //For Sign in
    private val RC_SIGN_IN = 1

    private val signInProviders =
            listOf(AuthUI.IdpConfig.EmailBuilder()
                    .setAllowNewAccounts(true)
                    .setRequireName(true)
                    .build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val recyclerView = rvCard as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val imoveis = ArrayList<Imovel>()
        imoveis.add(Imovel("Apartamento", 3, 2, 5,
                700, 2, 1, 1, 1, "Perto do Centro",
                "Av São Carlos", null))
        imoveis.add(Imovel("Republica", 8, 5, 6,
                400, 4, 2, 2, 2, "No Kartodromo",
                "Sei la", null))
        imoveis.add(Imovel("Casa", 4, 2, 5,
                1000, 2, 2, 1, 1, "Perto do Centro",
                "Av São Carlos", null))

        imoveis.add(Imovel("Casa", 4, 2, 5,
                1000, 2, 2, 1, 1, "Perto do Centro",
                "Av São Carlos", null))

        val adapter = CardAdapter(imoveis)
        recyclerView.adapter = adapter

        //TODO: mudar nome e email na navigation drawer
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_entrar -> {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    val intent = AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(signInProviders)
                            .setLogo(R.drawable.ic_home) // TODO: Change this later
                            .build()
                    startActivityForResult(intent, RC_SIGN_IN)
                } else {
                    Toast.makeText(this, "Já conectado", Toast.LENGTH_SHORT).show() // TODO: Substituir por logout
                }
            }
            R.id.nav_perfil -> {
                startActivity(intentFor<PerfilActivity>())
            }
            R.id.nav_pref -> {
                Toast.makeText(this, "Preferências", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_list -> {
                Toast.makeText(this, "Minha Lista", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_faq -> {
                Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_contato -> {
                Toast.makeText(this, "Contato", Toast.LENGTH_SHORT).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("Configurando sua conta")

                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                    progressDialog.dismiss()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                        Toast.makeText(this, "Sem internet", Toast.LENGTH_SHORT).show()
                    ErrorCodes.UNKNOWN_ERROR ->
                        Toast.makeText(this, "Erro desconhecido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
