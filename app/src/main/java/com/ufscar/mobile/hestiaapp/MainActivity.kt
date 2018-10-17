package com.ufscar.mobile.hestiaapp

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        val adapter = CardAdapter(imoveis)
        recyclerView.adapter = adapter
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
                Toast.makeText(this, "Entrar", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_perfil -> {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
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
}
