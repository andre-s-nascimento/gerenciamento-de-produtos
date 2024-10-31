package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.mappers.ProdutoMapperImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProdutoMapperTest {

    private val produtoMapper = ProdutoMapperImpl()

    @Test
    fun `should map ProdutoRequest to Produto`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Executar o teste
        val produto = produtoMapper.toEntity(request)

        // Expectativas - Asserções
        assertNotNull(produto)
        assertEquals("Produto 1", produto.nome)
        assertEquals(10.0, produto.preco)
        assertEquals("Descrição do Produto 1", produto.descricao)
        assertEquals(5, produto.quantidadeEmEstoque)
    }

    @Test
    fun `should map Produto to ProdutoResponse`() {
        // Preparação
        val produto = ProdutoBuilder()
            .withId(1L)
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Executar o teste
        val response = produtoMapper.toResponse(produto)

        // Expectativas - Asserções
        assertNotNull(response)
        assertEquals(1L, response.id)
        assertEquals("Produto 1", response.nome)
        assertEquals(10.0, response.preco)
        assertEquals("Descrição do Produto 1", response.descricao)
        assertEquals(5, response.quantidadeEmEstoque)
    }

    @Test
    fun `should return empty Produto when ProdutoRequest is null`() {
        // Executar o teste
        val produto = produtoMapper.toEntity(null)

        // Expectativas - Asserções
        assertNotNull(produto)
        assertEquals(-1, produto.id)
        assertNull(produto.nome)
        assertNull(produto.preco)
        assertNull(produto.descricao)
        assertNull(produto.quantidadeEmEstoque)
    }

    @Test
    fun `should return ProdutoResponse with null fields when Produto is null`() {
        // Executar o teste
        val response = produtoMapper.toResponse(null)

        // Expectativas - Asserções
        assertNotNull(response)
        assertNull(response.id)
        assertNull(response.nome)
        assertNull(response.preco)
        assertNull(response.descricao)
        assertNull(response.quantidadeEmEstoque)
    }

    @Test
    fun `should map optional fields correctly`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("Produto 1")
            .withPreco(null)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(null)
            .build()

        // Executar o teste
        val produto = produtoMapper.toEntity(request)

        // Expectativas - Asserções
        assertNotNull(produto)
        assertEquals("Produto 1", produto.nome)
        assertNull(produto.preco) // Verifica se o preço é null
        assertEquals("Descrição do Produto 1", produto.descricao)
        assertNull(produto.quantidadeEmEstoque) // Verifica se a quantidade é null
    }
}