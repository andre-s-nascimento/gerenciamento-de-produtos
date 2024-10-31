package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.models.Produto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProdutoTest {

    @Test
    fun `should create Produto with all fields`() {
        // Preparação e Execução
        val produto = ProdutoBuilder()
            .withId(1L)
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Expectativas - Asserções
        assertNotNull(produto)
        assertEquals(1L, produto.id)
        assertEquals("Produto 1", produto.nome)
        assertEquals(10.0, produto.preco)
        assertEquals("Descrição do Produto 1", produto.descricao)
        assertEquals(5, produto.quantidadeEmEstoque)
    }

    @Test
    fun `should set and get fields correctly`() {
        // Preparação e Execução
        val produto = Produto().apply {
            id = 2L
            nome = "Produto 2"
            preco = 20.0
            descricao = "Descrição do Produto 2"
            quantidadeEmEstoque = 10
        }

        // Expectativas - Asserções
        assertEquals(2L, produto.id)
        assertEquals("Produto 2", produto.nome)
        assertEquals(20.0, produto.preco)
        assertEquals("Descrição do Produto 2", produto.descricao)
        assertEquals(10, produto.quantidadeEmEstoque)
    }
}