package com.ufscar.mobile.hestiaapp.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ufscar.mobile.hestiaapp.model.User

object FirestoreUtil {
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
                        null, false)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else onComplete()
        }
    }

    fun updateCurrentUser(nome: String = "", email: String = "", bio: String = "", picturePath: String? = null, dono: Boolean = false) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (nome.isNotBlank()) userFieldMap["nome"] = nome
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (picturePath != null) userFieldMap["picturePath"] = picturePath
        userFieldMap["dono"] = dono
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit, context: Context) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    //                    Toast.makeText(context, "Usuário carregado", Toast.LENGTH_SHORT).show()
                    onComplete(it.toObject(User::class.java)!!)
                }
    }
}