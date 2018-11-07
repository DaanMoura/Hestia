package com.ufscar.mobile.hestiaapp.util

import android.content.Context
import android.graphics.Bitmap
import android.provider.ContactsContract
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.model.User

object FirestoreImovelUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}
    private val imoveisCollectionReference: CollectionReference
        get() = firestoreInstance.collection("/imoveis")

    fun insertImovel(title: String = "",
                     max: Int = 0,
                     min: Int = 0,
                     interessados: Int = 0,
                     preco: Int = 0,
                     quartos: Int = 0,
                     banheiros: Int = 0,
                     salas: Int = 0,
                     vaga: Int = 0,
                     descricao: String = "",
                     endereco: String = "",
                     uidDono: String = "",
                     bmFoto: Bitmap? = null) {
        val imovelFieldMap = mutableMapOf<String, Any>()

        imovelFieldMap["title"] = title
        imovelFieldMap["max"] = max
        imovelFieldMap["min"] = min
        imovelFieldMap["interessados"] = interessados
        imovelFieldMap["preco"] = preco
        imovelFieldMap["quartos"] = quartos
        imovelFieldMap["banheiros"] = banheiros
        imovelFieldMap["salas"] = salas
        imovelFieldMap["vaga"] = vaga
        imovelFieldMap["descricao"] = descricao
        imovelFieldMap["endereco"] = endereco
        imovelFieldMap["uidDono"] = uidDono
        //TODO: adcionar o campo da foto

        imoveisCollectionReference.add(imovelFieldMap)
    }

    fun getAll(onComplete: (ArrayList<Imovel>) -> Unit) {
        val imoveis = ArrayList<Imovel>()
        imoveisCollectionReference.get().addOnCompleteListener {task ->
            if(task.isSuccessful) {
                for(document in task.result) {
                    imoveis.add(document.toObject(Imovel::class.java))
                }
                onComplete(imoveis)
//                Toast.makeText(context, "Dados carregados com sucesso", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Falha ao carregar dados", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    fun getImovel(onComplete: ())
}