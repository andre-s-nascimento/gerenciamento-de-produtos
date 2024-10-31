package dev.andrenascimento.gerenciamento_de_produtos.controllers

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.services.ProdutoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProdutoController(private val produtoService: ProdutoService) {

    @GetMapping
    fun listarProdutos(): ResponseEntity<List<ProdutoResponse>> {
        val produtos = produtoService.listarProdutos()
        return ResponseEntity.ok(produtos)
    }

    @PostMapping
    fun criarProduto(@Valid @RequestBody produtoRequest: ProdutoRequest): ResponseEntity<ProdutoResponse> {
        val produtoResponse = produtoService.criarProduto(produtoRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse)
    }

    @PutMapping("/{id}")
    fun atualizarProduto(
        @PathVariable id: Long,
        @RequestBody produtoRequest: ProdutoRequest
    ): ResponseEntity<ProdutoResponse> {
        val produtoResponse = produtoService.atualizarProduto(id, produtoRequest)
        return ResponseEntity.ok(produtoResponse)
    }

    @DeleteMapping("/{id}")
    fun excluirProduto(@PathVariable id: Long): ResponseEntity<Void> {
        produtoService.excluirProduto(id)
        return ResponseEntity.noContent().build()
    }
}