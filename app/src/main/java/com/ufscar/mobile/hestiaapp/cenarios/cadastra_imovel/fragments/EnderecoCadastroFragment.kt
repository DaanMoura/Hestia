package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ufscar.mobile.hestiaapp.R

class EnderecoCadastroFragment : Fragment() {

    companion object {
        private val ARG_MAP = "arg_map"
        fun newInstance(cadastroMap: HashMap<String,Any>) =
                EnderecoCadastroFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_MAP, cadastroMap)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endereco_cadastro, container, false)
    }



}
