package dev.andrenascimento.gerenciamento_de_produtos.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

@Entity
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres.")
    @field:NotNull(message = "O nome do produto é obrigatório!")
    var nome: String,

    @field:PositiveOrZero(message = "O preço não pode ser menor do que 0.")
    var preco: Double? = null,

    var descricao: String? = null,

    @field:PositiveOrZero(message = "A quantidade não pode ser menor do que 0.")
    var quantidadeEmEstoque: Int? = null
)
