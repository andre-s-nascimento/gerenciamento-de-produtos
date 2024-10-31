package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProdutoResponseTest {

    @Test
    fun `should create ProdutoResponse with all fields`() {
        // Preparação e Execução
        val response = ProdutoResponseBuilder()
            .withId(1L)
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Expectativas - Asserções
        assertNotNull(response)
        assertEquals(1L, response.id)
        assertEquals("Produto 1", response.nome)
        assertEquals(10.0, response.preco)
        assertEquals("Descrição do Produto 1", response.descricao)
        assertEquals(5, response.quantidadeEmEstoque)
    }

    @Test
    fun `should set and get fields correctly`() {
        // Preparação e Execução
        val response = ProdutoResponse(
            id = 2L,
            nome = "Produto 2",
            preco = 20.0,
            descricao = "Descrição do Produto 2",
            quantidadeEmEstoque = 10
        )

        // Expectativas - Asserções
        assertEquals(2L, response.id)
        assertEquals("Produto 2", response.nome)
        assertEquals(20.0, response.preco)
        assertEquals("Descrição do Produto 2", response.descricao)
        assertEquals(10, response.quantidadeEmEstoque)
    }
}