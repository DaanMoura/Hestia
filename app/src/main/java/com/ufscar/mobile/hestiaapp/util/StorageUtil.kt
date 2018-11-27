package com.ufscar.mobile.hestiaapp.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().uid ?: throw NullPointerException("UID is null"))

    fun uploadProfilePhoto(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit,
                           onProgress: (progress: Int) -> Unit,
                           context: Context) {
        val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        val uploadTask = ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
                .addOnProgressListener { uploadTask ->
                    val progress = (100.0 * uploadTask.bytesTransferred) / uploadTask.totalByteCount
                    onProgress(progress.toInt())
                }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
}