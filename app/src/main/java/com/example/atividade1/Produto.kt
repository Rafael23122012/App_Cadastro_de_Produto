package com.example.atividade1

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Produto(
    val nome: String,
    val valor: Double,
        @PrimaryKey(autoGenerate = true)
    val id: Int = 0
){
    override fun toString(): String {
        return "$id / $nome / R$ $valor"
    }
}