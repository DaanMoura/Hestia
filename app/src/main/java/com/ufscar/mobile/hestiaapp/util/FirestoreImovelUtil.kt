package com.ufscar.mobile.hestiaapp.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ufscar.mobile.hestiaapp.model.Imovel

object FirestoreImovelUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}
    private val imoveisCollectionReference: CollectionReference
        get() = firestoreInstance.collection("/imoveis")

    fun insertImovel(title: String = "",
                     tipo: String = "",
                     cidade: String = "",
                     rua: String = "",
                     numero: Int = 0,
                     complemento: String = "",
                     referencia: String = "",
                     max: Int = 0,
                     min: Int = 0,
                     preco: Int = 0,
                     quartos: Int = 0,
                     banheiros: Int = 0,
                     salas: Int = 0,
                     cozinhas: Int = 0,
                     vaga: Int = 0,
                     descricao: String = "",
                     uidDono: String = "",
                     picturePath: String?) {
        val imovelFieldMap = mutableMapOf<String, Any>()

        imovelFieldMap["title"] = title
        imovelFieldMap["tipo"] = tipo
        imovelFieldMap["cidade"] = cidade
        imovelFieldMap["rua"] = rua
        imovelFieldMap["numero"] = numero
        imovelFieldMap["complemento"] = complemento
        imovelFieldMap["referencia"] = referencia
        imovelFieldMap["max"] = max
        imovelFieldMap["min"] = min
        imovelFieldMap["preco"] = preco
        imovelFieldMap["quartos"] = quartos
        imovelFieldMap["banheiros"] = banheiros
        imovelFieldMap["salas"] = salas
        imovelFieldMap["cozinhas"] = cozinhas
        imovelFieldMap["vaga"] = vaga
        imovelFieldMap["descricao"] = descricao
        imovelFieldMap["uidDono"] = uidDono
        if(picturePath != null) imovelFieldMap["picturePath"] = picturePath

        imoveisCollectionReference.add(imovelFieldMap).addOnCompleteListener {task ->
            val result = task.result
            result?.update("id", result.id)
        }
    }

    fun updateInteressados(id: String, increment: Int) {
        imoveisCollectionReference.get().addOnCompleteListener { task ->
            for(document in task.result!!) {
                if(document.id == id) {
                    val imovel = document.toObject(Imovel::class.java)
                    document.reference.update("interessados", imovel.interessados + increment)
                }
            }
        }
    }

    fun getAll(onComplete: (ArrayList<Imovel>) -> Unit) {
        val imoveis = ArrayList<Imovel>()
        imoveisCollectionReference.get().addOnCompleteListener {task ->
            if(task.isSuccessful) {
                for(document in task.result!!) {
                    imoveis.add(document.toObject(Imovel::class.java))
                }
                onComplete(imoveis)
            } else {
            }
        }
    }

}