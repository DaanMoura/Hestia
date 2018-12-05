package com.ufscar.mobile.hestiaapp.cenarios.perfil

import android.content.Context

interface PerfilContract {

    interface View {
        fun changePhotoSuccess()
        fun saveSucces()
        fun showProgressBar()
        fun setUploadProgress(progress: Int)
        fun logoutSucces()
        fun setFieldsSucces(dono: Boolean, nome: String, bio: String, picturePath: String?)
    }

    interface Presenter {
        fun isDono(context: Context): Boolean
        fun onChangePhoto(nome: String, bio: String, oferecer: Boolean)
        fun onSaveWithPhoto(context: Context, imageBytes: ByteArray, nome: String, bio: String, oferecer: Boolean)
        fun onSaveWithoutPhoto(nome: String, bio: String, oferecer: Boolean)
        fun onLogout(context: Context)
        fun onSetFields(context: Context)
    }
}