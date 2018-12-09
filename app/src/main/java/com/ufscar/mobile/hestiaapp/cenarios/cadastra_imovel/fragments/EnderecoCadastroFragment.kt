package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ufscar.mobile.hestiaapp.R
import kotlinx.android.synthetic.main.fragment_endereco_cadastro.*

class EnderecoCadastroFragment : Fragment() {

    private val REQUIRED_FIELD = "Campo obrigatório"
    var listener: EnderecoCadastroFragment.onFragmentInteractionListener? = null

    companion object {
        private val ARG_INFO_MAP = "arg_map"
        private val ARG_ENDERECO_MAP = "arg_map2"
        fun newInstance(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>?) =
            EnderecoCadastroFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_INFO_MAP, infoMap)
                    putSerializable(ARG_ENDERECO_MAP, enderecoMap)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endereco_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argMap = arguments?.getSerializable(ARG_ENDERECO_MAP) as HashMap<String,Any>?
        if(argMap != null) {
            edtCidade.setText(argMap.getValue("cidade").toString())
            edtRua.setText(argMap.getValue("rua").toString())
            if("numero" in argMap) {
                edtNumero.setText(argMap.getValue("numero").toString())
            }
            edtComplemento.setText(argMap.getValue("complemento").toString())
            edtReferencia.setText(argMap.getValue("referencia").toString())
            edtDesc.setText(argMap.getValue("descricao").toString())
        }

        voltar.setOnClickListener {
            val enderecoMap: HashMap<String, Any> = HashMap()
            enderecoMap["cidade"] = edtCidade.text ?: ""
            enderecoMap["rua"] = edtRua.text ?: ""
            if(!edtNumero.text.isNullOrEmpty()) {
                enderecoMap["numero"] = edtNumero.text.toString().toInt()
            }
            enderecoMap["complemento"] = edtComplemento.text ?: ""
            enderecoMap["referencia"] = edtReferencia.text ?: ""
            enderecoMap["descricao"] = edtDesc.text ?: ""
            listener?.onVoltarInfoInteraction(getInfoMap(), enderecoMap)
        }

        prosseguir.setOnClickListener {
            if(verifyFields()) {
                listener?.onProsseguirFotosInteraction()
            }
        }
    }

    fun verifyFields(): Boolean {
        var result = true
        if (TextUtils.isEmpty(edtRua.text)) {
            edtRua.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtCidade.text)) {
            edtCidade.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtNumero.text)) {
            edtNumero.error = REQUIRED_FIELD
            result = false
        }
        return result
    }

    fun getInfoMap(): HashMap<String,Any> {
        val map = arguments?.getSerializable(ARG_INFO_MAP) as HashMap<String,Any>?
        if(map == null) {
            throw NullPointerException("Info Map can not be null")
        }
        return map
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is EnderecoCadastroFragment.onFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException (
                    context.toString() + "must EnderecoCadastroFragment.onFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface onFragmentInteractionListener {
        fun onProsseguirFotosInteraction()
        fun onVoltarInfoInteraction(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>?)
    }
}
