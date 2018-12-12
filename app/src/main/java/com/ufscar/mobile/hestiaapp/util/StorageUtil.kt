package com.ufscar.mobile.hestiaapp.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().uid ?: throw NullPointerException("UID is null"))

    private val imoveisRef: StorageReference
        get() = storageInstance.reference
                .child("imoveis")

    fun uploadProfilePhoto(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        val uploadTask = ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun uploadImovelPhoto(imageBytes: ByteArray,
                          onSuccess: (imagePath: String) -> Unit) {
        val ref = imoveisRef.child("${UUID.nameUUIDFromBytes(imageBytes)}")
        val uploadTask = ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
}