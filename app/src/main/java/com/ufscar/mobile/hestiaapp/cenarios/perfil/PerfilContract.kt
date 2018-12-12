package com.ufscar.mobile.hestiaapp.cenarios.perfil

import android.content.Context

interface PerfilContract {

    interface View {
        fun changePhotoSuccess()
        fun saveSucces()
        fun showProgressBar()
        fun setUploadProgress(progress: Int)
        fun logoutSucces()
        fun setFieldsSucces(nome: String, bio: String, picturePath: String?)
    }

    interface Presenter {
        fun onChangePhoto(nome: String, bio: String)
        fun onSaveWithPhoto(context: Context, imageBytes: ByteArray, nome: String, bio: String)
        fun onSaveWithoutPhoto(nome: String, bio: String)
        fun onLogout(context: Context)
        fun onSetFields(context: Context)
    }
}