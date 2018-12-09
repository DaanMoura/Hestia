package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.R
import kotlinx.android.synthetic.main.fragment_informacoes_cadastro.*

class InformacoesCadastroFragment : Fragment() {

    companion object {
        private val ARG_MAP = "arg_map"
        private val ARG_ENDERECO_MAP = "arg_map2"
        fun newInstance(infoMap: HashMap<String,Any>?, enderecoMap: HashMap<String, Any>?) =
                InformacoesCadastroFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_MAP, infoMap)
                        putSerializable(ARG_ENDERECO_MAP, enderecoMap)
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
            activity?.let {
                Toast.makeText(it, argMap.toString(), Toast.LENGTH_SHORT).show()
            }
            edtTitle.setText(argMap.getValue("title").toString())
            edtTipo.setText(argMap.getValue("tipo").toString())
            edtPreco.setText(argMap.getValue("preco").toString())
            edtMoradores.setText(argMap.getValue("moradores").toString())
            edtQuartos.setText(argMap.getValue("quartos").toString())
            edtBanheiros.setText(argMap.getValue("banheiros").toString())
            edtSalas.setText(argMap.getValue("sala").toString())
            edtVagas.setText(argMap.getValue("vagas").toString())
        }

        prosseguir.setOnClickListener {
            val infoMap:HashMap<String,Any> = HashMap()
            if(verifyFields()) {
                infoMap["title"] = edtTitle.text ?: " "
                infoMap["tipo"] = edtTipo.text ?: " "
                infoMap["preco"] = edtPreco.text.toString().toInt()
                infoMap["moradores"] = edtMoradores.text.toString().toInt()
                infoMap["quartos"] = edtQuartos.text.toString().toInt()
                infoMap["banheiros"] = edtBanheiros.text.toString().toInt()
                infoMap["sala"] = edtSalas.text.toString().toInt()
                infoMap["vagas"] = edtVagas.text.toString().toInt()
                val argMap = arguments?.getSerializable(ARG_ENDERECO_MAP) as HashMap<String, Any>?
                listener?.onProsseguirEnderecoInteraction(infoMap, argMap)
            }
        }
    }

    fun verifyFields(): Boolean {
        var result = true
        if (TextUtils.isEmpty(edtPreco.text)) {
            edtPreco.error = REQUIRED_FIELD
            result = false
        }
        if (TextUtils.isEmpty(edtTipo.text)) {
            edtTipo.error = REQUIRED_FIELD
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
        fun onProsseguirEnderecoInteraction(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>?)
    }
}
