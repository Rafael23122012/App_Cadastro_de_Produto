package com.example.atividade1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.atividade1.databinding.ActivityNovoProdutoBinding
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovoProduto : AppCompatActivity() {

    private val b by lazy {
        ActivityNovoProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnSalvarProduto.setOnClickListener {

            val produto = Produto(
                b.edtnome.text.toString(),
                b.edtvalor.text.toString().toDouble()

            )

            CoroutineScope(Dispatchers.IO).launch {
                // executa paralelo à UI thread
                ProdutoDatabase.getInstance(this@NovoProduto).ProdutoDao().salvar(produto)

                withContext(Dispatchers.Main) {
                    // executa na UI thread
                    Toast.makeText(this@NovoProduto, "Salvo com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

            }
        }

        }
    }





