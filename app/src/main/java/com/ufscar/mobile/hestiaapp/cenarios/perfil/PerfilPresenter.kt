package com.ufscar.mobile.hestiaapp.cenarios.perfil

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import com.ufscar.mobile.hestiaapp.util.GlideApp
import com.ufscar.mobile.hestiaapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_perfil.*


class PerfilPresenter(val view: PerfilContract.View): PerfilContract.Presenter {

    override fun isDono(context: Context): Boolean {
        var status = false
        FirestoreUserUtil.getCurrentUser({ user ->
            status = user.dono
        }, context)
        return status
    }

    override fun onChangePhoto(nome: String, bio: String, oferecer: Boolean) {
        FirestoreUserUtil.updateCurrentUser(nome,
                FirebaseAuth.getInstance().currentUser?.email ?: "",
                bio, null, oferecer)
        view.changePhotoSuccess()
    }

    override fun onSaveWithPhoto(context: Context, imageBytes: ByteArray, nome: String, bio: String, oferecer: Boolean) {
        view.showProgressBar()
        StorageUtil.uploadProfilePhoto(imageBytes,
                { imagePath ->
                    FirestoreUserUtil.updateCurrentUser(nome,
                            FirebaseAuth.getInstance().currentUser?.email ?: "",
                            bio, imagePath, oferecer)
                    view.saveSucces()
                },
                { progress ->
                    view.setUploadProgress(progress)
                }, context)
    }

    override fun onSaveWithoutPhoto(nome: String, bio: String, oferecer: Boolean) {
        FirestoreUserUtil.updateCurrentUser(nome,
                FirebaseAuth.getInstance().currentUser?.email ?: "",
                bio, null, oferecer)
        view.saveSucces()
    }

    override fun onLogout(context: Context) {
        AuthUI.getInstance().signOut(context)
                .addOnCompleteListener {
                    view.logoutSucces()
                }
    }

    override fun onSetFields(context: Context) {
        FirestoreUserUtil.getCurrentUser({ user ->
            view.setFieldsSucces(user.dono, user.nome, user.bio, user.picturePath)
        }, context)
    }
}