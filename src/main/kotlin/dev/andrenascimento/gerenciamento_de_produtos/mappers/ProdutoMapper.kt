package dev.andrenascimento.gerenciamento_de_produtos.mappers

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.models.Produto

interface ProdutoMapper {
    fun toResponse(produto: Produto): ProdutoResponse

    fun toEntity(produtoRequest: ProdutoRequest): Produto
}