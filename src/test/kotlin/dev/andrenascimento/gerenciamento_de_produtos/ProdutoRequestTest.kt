package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoRequest
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProdutoRequestTest {
    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `should be valid when all fields are valid`() {
        // Preparação
        val request = ProdutoRequest(
            nome = "Produto 1",
            preco = 10.0,
            descricao = "Descrição do Produto 1",
            quantidadeEmEstoque = 5
        )

        // Executar o teste
        val violations = validator.validate(request)

        // Expectativas - Asserções
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should be invalid when nome is null`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome(null)
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Executar o teste
        val violations = validator.validate(request)

        // Expectativas - Asserções
        assertEquals(1, violations.size)
        assertEquals("Nome do produto é obrigatório", violations.first().message)
    }

    @Test
    fun `should be invalid when nome is shorter than two characters`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("A")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Executar o teste
        val violations = validator.validate(request)

        // Expectativas - Asserções
        assertEquals(1, violations.size)
        assertEquals("O nome do produto deve ter no mínimo 2 caracteres", violations.first().message)
    }

    @Test
    fun `should be invalid when preco is negative`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("Produto 1")
            .withPreco(-10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        // Executar o teste
        val violations = validator.validate(request)

        // Expectativas - Asserções
        assertEquals(1, violations.size)
        assertEquals("O preço não pode ser menor do que 0", violations.first().message)
    }

    @Test
    fun `should be invalid when quantidade is negative`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(-5)
            .build()

        // Executar o teste
        val violations = validator.validate(request)

        // Expectativas - Asserções
        assertEquals(1, violations.size)
        assertEquals("A quantidade não pode ser menor do que 0", violations.first().message)
    }
}