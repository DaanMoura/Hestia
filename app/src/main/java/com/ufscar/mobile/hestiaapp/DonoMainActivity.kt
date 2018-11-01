package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.model.User
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_dono.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class DonoMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Sign in request code
    private val RC_SIGN_IN = 1
    private val REQUEST_INFO = 3
    private val REQUEST_PERFIL = 4
    private val EXTRA_IMOVEL = "Imovel"

    //Sign in builder of firebase
    private val signInProviders =
            listOf(AuthUI.IdpConfig.EmailBuilder()
                    .setAllowNewAccounts(true)
                    .setRequireName(true)
                    .build())

    var imoveis = ArrayList<Imovel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dono_main)
        setSupportActionBar(toolbar_dono)

        fab_add.setOnClickListener {
            startActivity(intentFor<CadastraImovelActivity>())
        }

        //For the drawer toogle in action bar
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_dono, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //Marking the selected item
        nav_view.setNavigationItemSelectedListener(this)

        //Populating imoveisList

        /*FIXME: não está carregando direito(precisa ir para uma outra activity)
        Talvez de certo com doAsync()
         */
        FirestoreImovelUtil.getAll({
            imoveis = it
            loadList()
        }, this)

    }

    private fun updateDrawer() {
        //Trocando o nome e email da navigation drawer header
        val navigationView: NavigationView = nav_view
        val headerView: View = navigationView.getHeaderView(0);
        val nav_name = headerView.navdrawer_name
        val nav_email = headerView.navdrawer_email
        val nav_img = headerView.nav_imageView
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirestoreUserUtil.getCurrentUser({ user: User ->
                nav_name.text = "${user.nome} - Oferecendo"
                nav_email.text = user.email

                if (user.picturePath != null)
                    GlideApp.with(this)
                            .load(StorageUtil.pathToReference(user.picturePath!!))
                            .circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(nav_img)
            }, this)

        }
    }

    fun loadList() {
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

    override fun onResume() {
        super.onResume()
        loadList()
        updateDrawer()
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

    //Handling the navigation drawer items
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            //Logging in
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

            //Start PerfilActivity
            R.id.nav_perfil -> {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    // Talvez substituir por uma activity de "Sem conta :("
                    Toast.makeText(this, "Por favor, faça o log in primeiro", Toast.LENGTH_SHORT).show()
                } else {
                    val editPerfil = Intent(this, PerfilActivity::class.java)
                    startActivityForResult(editPerfil, REQUEST_PERFIL)
                }
            }

            //TODO: make the pref forms and put intent below
            R.id.nav_pref -> {
                Toast.makeText(this, "Preferências", Toast.LENGTH_SHORT).show()
            }

            //TODO: make "Minha Lista" and put intent below
            R.id.nav_list -> {
                Toast.makeText(this, "Minha Lista", Toast.LENGTH_SHORT).show()
            }

            //TODO: make FAQ and put intent below
            R.id.nav_faq -> {
                Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT).show()
            }

            //TODO: make "Contato" and put intent below
            R.id.nav_contato -> {
                Toast.makeText(this, "Contato", Toast.LENGTH_SHORT).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    //Here will be all the onActivityResult (when used startActivityForResult() )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //For Sign in
        when (requestCode) {
            RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)

                if (resultCode == Activity.RESULT_OK) {
                    //Placing progress dialog for feedback to user
                    val progressDialog = indeterminateProgressDialog("Configurando sua conta")

                    //If first time start MainActivity (?)
                    FirestoreUserUtil.initCurrentUserIfFirstTime {
                        startActivity(intentFor<MainActivity>().newTask().clearTask())
                        progressDialog.dismiss()
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) { // Exception treatment
                    if (response == null) return

                    when (response.error?.errorCode) {
                        ErrorCodes.NO_NETWORK ->
                            Toast.makeText(this, "Sem internet", Toast.LENGTH_SHORT).show()
                        ErrorCodes.UNKNOWN_ERROR ->
                            Toast.makeText(this, "Erro desconhecido", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            REQUEST_INFO -> {
                if (resultCode == Activity.RESULT_OK) {
                    //TODO: write code here
                }
            }

            REQUEST_PERFIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    //TODO: write code here
                }
            }
        }

    }
}
