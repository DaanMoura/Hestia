package com.ufscar.mobile.hestiaapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.util.FirestoreUtil
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_perfil.*
import org.jetbrains.anko.act
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.io.ByteArrayOutputStream

class PerfilActivity : AppCompatActivity() {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        var atual: Boolean = false
        FirestoreUtil.getCurrentUser({ user ->
            atual = user.dono
//            Toast.makeText(this, "$atual", Toast.LENGTH_SHORT).show()
        }, this)

        //Changing the picture
        profilePicture.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), RC_SELECT_IMAGE)
        }

        //Saving
        btnSave.setOnClickListener {

            if (::selectedImageBytes.isInitialized)
                StorageUtil.uploadProfilePhoto(selectedImageBytes) { imagePath ->
                    FirestoreUtil.updateCurrentUser(editNome.text.toString(),
                            FirebaseAuth.getInstance().currentUser?.email ?: "",
                            editBio.text.toString(),
                            imagePath, oferecer.isChecked)
                }
            else
                FirestoreUtil.updateCurrentUser(editNome.text.toString(),
                        FirebaseAuth.getInstance().currentUser?.email ?: "",
                        editBio.text.toString(),
                        null, oferecer.isChecked)


            //NÃO MEXER NISSO A NÃO SER QUE SEJA EXTREMAMENTE NECESSÁRIO!!!!
            if (atual != oferecer.isChecked) {
                if (oferecer.isChecked) {
                    val intentDono = Intent(this, DonoMainActivity::class.java)
                    setResult(Activity.RESULT_CANCELED, intentDono)
                    intentDono.newTask().clearTask()
                    startActivity(intentDono)
                    finish()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    setResult(Activity.RESULT_CANCELED, intent)
                    intent.newTask().clearTask()
                    startActivity(intent)
                    finish()
                }
            } else {
                val intentDono = Intent(this, DonoMainActivity::class.java)
                setResult(Activity.RESULT_OK, intentDono)
                finish()
            }


        }

        //Logging out
        btnLogout.setOnClickListener {
            AuthUI.getInstance().signOut(this!!)
                    .addOnCompleteListener {
                        startActivity(intentFor<MainActivity>().newTask().clearTask())
                    }
        }


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
                    .into(profilePicture)

            pictureJustChanged = true
        }
    }

    //Place the right data
    override fun onStart() {
        super.onStart()

        FirestoreUtil.getCurrentUser({ user ->
            if (user.dono) radioGroup.check(R.id.oferecer)
            else radioGroup.check(R.id.procurar)
            editNome.setText(user.nome)
            editBio.setText(user.bio)
            if (!pictureJustChanged && user.picturePath != null)
                GlideApp.with(this)
                        .load(StorageUtil.pathToReference(user.picturePath))
                        .circleCrop()
                        .into(profilePicture)

        }, this)


    }
}
