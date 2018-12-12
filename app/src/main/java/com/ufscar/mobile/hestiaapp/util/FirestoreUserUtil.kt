package com.ufscar.mobile.hestiaapp.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.ufscar.mobile.hestiaapp.model.User

object FirestoreUserUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null")}")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                        FirebaseAuth.getInstance().currentUser?.email ?: "",
                        "",
                        null,
                        arrayListOf())
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else onComplete()
        }
    }

    fun updateCurrentUser(nome: String = "", email: String = "", bio: String = "",
                          picturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (nome.isNotBlank()) userFieldMap["nome"] = nome
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (picturePath != null) userFieldMap["picturePath"] = picturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun addFavorite(favorito: String) {
        val addFavoriteToArrayMap = mutableMapOf<String, Any>()
        addFavoriteToArrayMap.put("favoritos", FieldValue.arrayUnion(favorito))

        currentUserDocRef.update(addFavoriteToArrayMap)
        FirestoreImovelUtil.updateInteressados(favorito, 1)

    }

    fun removeFavorite(favorito: String) {
        val removeFavoriteToMapArray = mutableMapOf<String, Any>()
        removeFavoriteToMapArray.put("favoritos", FieldValue.arrayRemove(favorito))
        currentUserDocRef.update(removeFavoriteToMapArray)
        FirestoreImovelUtil.updateInteressados(favorito, -1)
    }

    fun getCurrentUser(onComplete: (User) -> Unit, context: Context) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java)!!)
                }
    }
}