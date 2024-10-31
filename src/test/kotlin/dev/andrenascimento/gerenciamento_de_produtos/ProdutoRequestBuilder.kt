package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest

class ProdutoRequestBuilder {
    private var nome: String? = "Produto 1"
    private var preco: Double? = 10.0
    private var descricao: String? = "Descrição Produto 1"
    private var quantidadeEmEstoque: Int? = 10

    fun withNome(nome: String?) = apply { this.nome = nome }

    fun withPreco(preco: Double?) = apply { this.preco = preco }

    fun withDescricao(descricao: String) = apply { this.descricao = descricao }

    fun withQuantidadeEmEstoque(quantidadeEmEstoque: Int?) = apply { this.quantidadeEmEstoque = quantidadeEmEstoque }

    fun build() = ProdutoRequest(nome, preco, descricao, quantidadeEmEstoque)
}