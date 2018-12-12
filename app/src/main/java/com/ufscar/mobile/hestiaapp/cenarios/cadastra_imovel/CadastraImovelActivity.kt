package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis.MeusImoveisActivity
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments.EnderecoCadastroFragment
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments.FotosCadastroFragment
import com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments.InformacoesCadastroFragment
import com.ufscar.mobile.hestiaapp.cenarios.main.MainActivity
import com.ufscar.mobile.hestiaapp.util.GlideApp
import kotlinx.android.synthetic.main.fragment_fotos_cadastro.*
import org.jetbrains.anko.act
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import java.io.ByteArrayOutputStream

class CadastraImovelActivity : AppCompatActivity(), CadastraImovelContract.View,
    InformacoesCadastroFragment.onFragmentInteractionListener,
    EnderecoCadastroFragment.onFragmentInteractionListener,
    FotosCadastroFragment.onFragmentInteractionListener {

    val presenter: CadastraImovelContract.Presenter = CadastraImovelPresenter(this)
    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_imovel)

        val fragment = InformacoesCadastroFragment.newInstance(null, null)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, fragment)
                .commit()
    }

    override fun onProsseguirEnderecoInteraction(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>?) {
        val fragment = EnderecoCadastroFragment.newInstance(infoMap, enderecoMap)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onProsseguirFotosInteraction(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>) {

        val fragment = FotosCadastroFragment.newInstance(infoMap, enderecoMap)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, fragment)
                .addToBackStack(null)
                .commit()
    }


    override fun onVoltarInfoInteraction(infoMap: HashMap<String, Any>, enderecoMap: HashMap<String, Any>?) {
        val fragment = InformacoesCadastroFragment.newInstance(infoMap, enderecoMap)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fmMaster, fragment)
                .commit()
    }

    override fun insertFinished() {
        val intentDono = Intent(this, MeusImoveisActivity::class.java)
        intentDono.newTask().clearTask()
        startActivity(intentDono)
        finish()
    }

    override fun onEscolherFoto() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), RC_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media.getBitmap(act.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectedImageBytes)
                    .centerCrop()
                    .into(fotoImovel)
        }
    }

    override fun onFinalizar(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>) {
        if(::selectedImageBytes.isInitialized) {
            presenter.onInsertImovelWithPhoto(infoMap.getValue("title").toString(),
                    infoMap.getValue("tipo").toString(),
                    enderecoMap.getValue("cidade").toString(),
                    enderecoMap.getValue("rua").toString(),
                    enderecoMap.getValue("numero").toString().toInt(),
                    enderecoMap.getValue("complemento").toString(),
                    enderecoMap.getValue("referencia").toString(),
                    infoMap.getValue("moradores").toString().toInt(),
                    infoMap.getValue("moradores_atual").toString().toInt(),
                    infoMap.getValue("preco").toString().toInt(),
                    infoMap.getValue("quartos").toString().toInt(),
                    infoMap.getValue("banheiros").toString().toInt(),
                    infoMap.getValue("sala").toString().toInt(),
                    infoMap.getValue("cozinhas").toString().toInt(),
                    infoMap.getValue("vagas").toString().toInt(),
                    enderecoMap.getValue("descricao").toString(),
                    selectedImageBytes)
        } else {
            presenter.onInsertImovel(infoMap.getValue("title").toString(),
                    infoMap.getValue("tipo").toString(),
                    enderecoMap.getValue("cidade").toString(),
                    enderecoMap.getValue("rua").toString(),
                    enderecoMap.getValue("numero").toString().toInt(),
                    enderecoMap.getValue("complemento").toString(),
                    enderecoMap.getValue("referencia").toString(),
                    infoMap.getValue("moradores").toString().toInt(),
                    0,
                    infoMap.getValue("preco").toString().toInt(),
                    infoMap.getValue("quartos").toString().toInt(),
                    infoMap.getValue("banheiros").toString().toInt(),
                    infoMap.getValue("sala").toString().toInt(),
                    infoMap.getValue("cozinhas").toString().toInt(),
                    infoMap.getValue("vagas").toString().toInt(),
                    enderecoMap.getValue("descricao").toString())
        }
    }

    override fun saveSuccess() {
        startMainActivity()
    }

    override fun showProgressBar() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_circular.visibility = View.INVISIBLE
    }

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent)
        intent.newTask().clearTask()
        startActivity(intent)
        finish()
    }
}
