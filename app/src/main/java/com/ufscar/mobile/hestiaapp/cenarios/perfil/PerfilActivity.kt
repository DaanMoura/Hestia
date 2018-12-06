package com.ufscar.mobile.hestiaapp.cenarios.perfil

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.cenarios.main.MainActivity
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_perfil.*
import org.jetbrains.anko.act
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.io.ByteArrayOutputStream
import com.bumptech.glide.Glide
import com.ufscar.mobile.hestiaapp.util.GlideApp

class PerfilActivity : AppCompatActivity(), PerfilContract.View {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false
    var atual: Boolean = false

    val presenter: PerfilContract.Presenter = PerfilPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)


        //Changing the picture
        profilePicture.setOnClickListener {
            presenter.onChangePhoto(editNome.text.toString(), editBio.text.toString())
        }

        //Saving
        btnSave.setOnClickListener {
            //FIXME: nao carrega direito na outra activity
            if (::selectedImageBytes.isInitialized) {
                presenter.onSaveWithPhoto(this, selectedImageBytes, editNome.text.toString(),
                        editBio.text.toString())
            } else {
                presenter.onSaveWithoutPhoto(editNome.text.toString(), editBio.text.toString())
            }
        }

        //Logging out
        btnLogout.setOnClickListener {
            presenter.onLogout(this)
        }
    }

    override fun saveSucces() {
        val intent = Intent(this, MainActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent)
        intent.newTask().clearTask()
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Selecting image and loading with Glide
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media.getBitmap(act.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectedImageBytes)
                    .circleCrop()
                    .into(profilePicture)

            pictureJustChanged = true
        }
    }

    //Place the right data
    override fun onStart() {
        super.onStart()
        presenter.onSetFields(this)

    }

    override fun changePhotoSuccess() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), RC_SELECT_IMAGE)
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun setUploadProgress(progress: Int) {
        progress_bar.progress = progress
    }

    override fun logoutSucces() {
        startActivity(intentFor<MainActivity>().newTask().clearTask())
    }

    override fun setFieldsSucces(nome: String, bio: String, picturePath: String?) {
        editNome.setText(nome)
        editBio.setText(bio)
        if (!pictureJustChanged && picturePath != null)
            GlideApp.with(this)
                    .load(StorageUtil.pathToReference(picturePath))
                    .circleCrop()
                    .into(profilePicture)
    }
}
