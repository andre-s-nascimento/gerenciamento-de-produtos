package dev.andrenascimento.gerenciamento_de_produtos.mappers

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.models.Produto
import org.springframework.stereotype.Component

@Component
class ProdutoMapperImpl : ProdutoMapper {

    override fun toResponse(produto: Produto?): ProdutoResponse {
        return ProdutoResponse(
            id = produto?.id,
            nome = produto?.nome,
            preco = produto?.preco,
            descricao = produto?.descricao,
            quantidadeEmEstoque = produto?.quantidadeEmEstoque
        )
    }

    override fun toEntity(produtoRequest: ProdutoRequest?): Produto {
        return Produto(
            id = -1L, // O id será gerado pelo banco de dados
            nome = produtoRequest?.nome,
            preco = produtoRequest?.preco,
            descricao = produtoRequest?.descricao,
            quantidadeEmEstoque = produtoRequest?.quantidadeEmEstoque
        )
    }
}