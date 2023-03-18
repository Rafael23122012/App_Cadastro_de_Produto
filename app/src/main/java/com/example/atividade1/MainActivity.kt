@file:Suppress("UNUSED_LAMBDA_EXPRESSION")

package com.example.atividade1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade1.databinding.ActivityMainBinding
import com.example.atividade1.databinding.ActivityNovoProdutoBinding
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    var i = 1

    private val b by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    val listaDeProduto = ArrayList<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnNovoProduto.setOnClickListener {

            startActivity(Intent(this, NovoProduto::class.java))

        }

        b.lstProduto.setOnItemLongClickListener { adapterView, view, i, l ->

            val ProdutoDeletar: Produto = adapterView.adapter.getItem(i) as Produto
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Apagar Produto")
                .setMessage("Deseja realmente apagar o produto selecionado?")
                .setPositiveButton("Sim") { dialog, which ->
                    ProdutoDatabase.getInstance(this@MainActivity).ProdutoDao()
                        .deletar(ProdutoDeletar)
                    Toast.makeText(
                        this, "Produto removido com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    atuaizarLista()
                }
                .setNegativeButton("Não") { dialog, which ->
                    Toast.makeText(
                        this, "Produto não removido!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .show()
            true
        }
    }

    private fun atualizarLista() {
        val adp = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaDeProduto
        )

        b.lstProduto.adapter = adp
    }


    override fun onResume() {
        super.onResume()

        atuaizarLista()

    }

    fun atuaizarLista() {


        var produto: List<Produto>

        CoroutineScope(Dispatchers.IO).launch {
            // executa paralelo à UI thread
            produto = ProdutoDatabase.getInstance(this@MainActivity).ProdutoDao().listar()

            withContext(Dispatchers.Main) {
                // executa na UI thread
                b.lstProduto.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    produto
                )
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_principal, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuOrdenarValor) {

            var lista: List<Produto>? = null

            if (Math.floorMod(i, 2) == 0) {
                lista = ProdutoDatabase.getInstance(this).ProdutoDao().listarPorPrecoDesc()
            }
            else {
                lista = ProdutoDatabase.getInstance(this).ProdutoDao().listarPorPrecoAsc()
            }
            b.lstProduto.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                lista
            )
            i++
        }

        if (item.itemId == R.id.btnSair){
            finishAffinity()
        }

        if (item.itemId == R.id.btnQrCode) {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }


        if (item.itemId == R.id.menuOrdenaNome) {

            var lista: List<Produto>? = null

            if (Math.floorMod(i, 2) == 0) {
                lista = ProdutoDatabase.getInstance(this).ProdutoDao().listarPorNomeDesc()
            }
            else {
                lista = ProdutoDatabase.getInstance(this).ProdutoDao().listarPorNomeAsc()
            }
            b.lstProduto.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                lista
            )
            i++
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(
                        this, "Produto não encontrado!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val id = result.contents.trim().toInt()
                    val produto: Produto = ProdutoDatabase.getInstance(this@MainActivity).ProdutoDao().getProdutoById(id)

                    if (produto == null){
                        Toast.makeText(
                            this, "Produto não encontrado!",
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        val intent = Intent(this, MainActivity_Resultado_qr_code::class.java)
                        val nome = produto.nome
                        val valor = produto.valor
                        val id = produto.id
                        intent.putExtra("id", id)
                        intent.putExtra("nome", nome)
                        intent.putExtra("valor", valor)
                        startActivity(intent)

                    }


                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
}

}