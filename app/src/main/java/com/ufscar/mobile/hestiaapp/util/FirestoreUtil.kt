package com.ufscar.mobile.hestiaapp.util

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
                        null)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else onComplete()
        }
    }

    fun updateCurrentUser(name: String = "", email: String = "", bio: String = "", picturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (email.isNotBlank()) userFieldMap["email"] = email
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (picturePath != null) userFieldMap["picturePath"] = picturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java)!!)
                }
    }
}