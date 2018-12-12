package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ufscar.mobile.hestiaapp.R
import kotlinx.android.synthetic.main.fragment_fotos_cadastro.*

class FotosCadastroFragment : Fragment() {

    companion object {
        private val ARG_INFO_MAP = "arg_info_map"
        private val ARG_END_MAP = "arg_end_map"
        fun newInstance(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>) =
                FotosCadastroFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_INFO_MAP, infoMap)
                        putSerializable(ARG_END_MAP, enderecoMap)
                    }
                }
    }

    var listener: FotosCadastroFragment.onFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fotos_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: carregar se ja tiver foto
        val infoMap = arguments?.getSerializable(ARG_INFO_MAP) as HashMap<String,Any>
        val enderecoMap = arguments?.getSerializable(ARG_END_MAP) as HashMap<String,Any>

        add_foto.setOnClickListener {
            listener?.onEscolherFoto()
        }

        tirar_foto.setOnClickListener {
            listener?.onTirarFoto()
        }

        finalizar.setOnClickListener {
            listener?.onFinalizar(infoMap, enderecoMap)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FotosCadastroFragment.onFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException (
                    context.toString() + "must FotosCadastroFragment.onFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface onFragmentInteractionListener {
        fun onEscolherFoto()
        fun onTirarFoto()
        fun onFinalizar(infoMap: HashMap<String,Any>, enderecoMap: HashMap<String, Any>)
    }
}
