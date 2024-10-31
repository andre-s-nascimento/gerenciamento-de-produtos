package dev.andrenascimento.gerenciamento_de_produtos.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

// Para receber dados na criação e atualização de produtos.
data class ProdutoRequest(
    @field:NotNull(message = "Nome do produto é obrigatório")
    @field:Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres")
    var nome: String?,

    @field:PositiveOrZero(message = "O preço não pode ser menor do que 0")
    var preco: Double?,

    var descricao: String? = null,

    @field:PositiveOrZero(message = "A quantidade não pode ser menor do que 0")
    var quantidadeEmEstoque: Int? = null
)
