package dev.andrenascimento.gerenciamento_de_produtos.services

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import dev.andrenascimento.gerenciamento_de_produtos.mappers.ProdutoMapper
import dev.andrenascimento.gerenciamento_de_produtos.repositories.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoServiceImpl(
    private val produtoRepository: ProdutoRepository,
    private val produtoMapper: ProdutoMapper): ProdutoService {

    override fun listarProdutos(): List<ProdutoResponse> {
        return produtoRepository.findAll()
            .map(produtoMapper::toResponse)
    }

    override fun criarProduto(produtoRequest: ProdutoRequest): ProdutoResponse {
        val produto = produtoMapper.toEntity(produtoRequest)
        val savedProduto = produtoRepository.save(produto)
        return produtoMapper.toResponse(savedProduto)
    }

    override fun atualizarProduto(id: Long, produtoRequest: ProdutoRequest): ProdutoResponse {
        if(!produtoRepository.existsById(id)){
            throw ProdutoNotFoundException("Não existe o produto com o id: [$id]")
        }

        val produtoToSave = produtoMapper.toEntity(produtoRequest).apply { this.id = id }
        val updatedProduto = produtoRepository.save(produtoToSave)

        return produtoMapper.toResponse(updatedProduto)
    }

    override fun excluirProduto(id: Long) {
        if(!produtoRepository.existsById(id)){
            throw ProdutoNotFoundException("Não existe o produto com o id: [$id]")
        }
        produtoRepository.deleteById(id)
    }


}