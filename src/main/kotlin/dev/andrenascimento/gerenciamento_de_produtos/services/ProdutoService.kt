package dev.andrenascimento.gerenciamento_de_produtos.services

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse

interface ProdutoService {
    fun listarProdutos(): List<ProdutoResponse>

    fun criarProduto(produtoRequest: ProdutoRequest): ProdutoResponse

    fun atualizarProduto(id: Long, produtoRequest: ProdutoRequest): ProdutoResponse

    fun excluirProduto(id: Long)

}