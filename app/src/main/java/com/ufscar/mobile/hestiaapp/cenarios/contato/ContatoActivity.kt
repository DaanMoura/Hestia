package com.ufscar.mobile.hestiaapp.cenarios.contato

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ufscar.mobile.hestiaapp.R
import kotlinx.android.synthetic.main.activity_contato.*

class ContatoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        email_daniel.setOnClickListener {
            enviarEmail(email_daniel.text as String)
        }

        email_lucas.setOnClickListener {
            enviarEmail(email_lucas.text as String)
        }

        email_pedro.setOnClickListener {
            enviarEmail(email_pedro.text as String)
        }
    }

    fun enviarEmail(email: String) {
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "[Hestia] Contato")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
