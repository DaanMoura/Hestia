package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ufscar.mobile.hestiaapp.R
import kotlinx.android.synthetic.main.fragment_informacoes_cadastro.*

class InformacoesCadastroFragment : Fragment() {

    private val REQUIRED_FIELD = "Campo obrigatório"
    var listener: onFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacoes_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoMap:HashMap<String,Any> = HashMap()

        infoMap["title"] = edtTitle.text
        infoMap["tipo"] = spinnerTipo.prompt
        infoMap["preco"] = edtPreco.text.toString().toInt()
        infoMap["moradores"] = edtMoradores.text.toString().toInt()
        infoMap["quartos"] = edtQuartos.text.toString().toInt()
        infoMap["banheiros"] = edtBanheiros.text.toString().toInt()
        infoMap["sala"] = edtSalas.text.toString().toInt()
        infoMap["vagas"] = edtVagas.text.toString().toInt()

        prosseguir.setOnClickListener {
            if(verifyPreco()) {
                listener?.onProsseguirInteraction(infoMap)
            }
        }
    }

    fun verifyPreco(): Boolean {
        var result = true
        if (TextUtils.isEmpty(edtPreco.text)) {
            edtPreco.error = REQUIRED_FIELD
            result = false
        }
        return result
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is InformacoesCadastroFragment.onFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException (
                    context.toString() + "must InformacoesCadastroFragment.onFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface onFragmentInteractionListener {
        fun onProsseguirInteraction(infoMap: HashMap<String,Any>)
    }



}
