package com.example.atividade1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProdutoDao {

    @Insert
    fun salvar(produto: Produto)

    @Delete
    fun deletar(produto: Produto)

    @Query("SELECT * FROM Produto")
    fun listar(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.valor")
    fun listarPorPrecoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.valor DESC")
    fun listarPorPrecoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.nome")
    fun listarPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.nome DESC")
    fun listarPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun getProdutoById(id: Int): Produto
    }
