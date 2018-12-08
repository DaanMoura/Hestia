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

    companion object {
        private val ARG_MAP = "arg_map"
        fun newInstance(infoMap: HashMap<String,Any>?) =
                InformacoesCadastroFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_MAP, infoMap)
                    }
                }
    }

    private val REQUIRED_FIELD = "Campo obrigatório"
    var listener: onFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacoes_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argMap = arguments?.getSerializable(ARG_MAP) as HashMap<String,Any>?
        if(argMap != null) {
            edtTitle.setText(argMap["title"] as String)
            spinnerTipo.prompt = argMap["tipo"] as String
            edtPreco.setText(argMap["preco"] as String)
            edtMoradores.setText(argMap["moradores"] as String)
            edtQuartos.setText(argMap["quartos"] as String)
            edtBanheiros.setText(argMap["banheiros"] as String)
            edtSalas.setText(argMap["sala"] as String)
            edtVagas.setText(argMap["vagas"] as String)
        }

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
                listener?.onProsseguirEnderecoInteraction(infoMap)
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
        fun onProsseguirEnderecoInteraction(infoMap: HashMap<String,Any>)
    }
}
