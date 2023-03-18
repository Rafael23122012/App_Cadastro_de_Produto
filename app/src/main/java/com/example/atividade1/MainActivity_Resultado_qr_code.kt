package com.example.atividade1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.atividade1.databinding.ActivityMainBinding
import com.example.atividade1.databinding.ActivityMainResultadoQrCodeBinding

class MainActivity_Resultado_qr_code : AppCompatActivity() {

    private val b by lazy {
        ActivityMainResultadoQrCodeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        val produtoid = intent.getIntExtra("id", 0)
        val produtoNome = intent.getStringExtra("nome")
        val produtoValor = intent.getDoubleExtra("valor", 0.00)

        b.txtId.text = produtoid.toString()
        b.txtNome.text = produtoNome.toString()
        b.txtValor.text = produtoValor.toString()


    }


}