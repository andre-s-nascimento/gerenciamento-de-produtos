package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class ProdutoNotFoundExceptionTest {

    @Test
    fun `should create exception with custom message`() {
        // Preparação
        val customMessage = "Produto com ID 1 não encontrado!"

        // Executar o teste
        val exception = ProdutoNotFoundException(customMessage)

        // Expectativas - Asserções
        assertEquals(customMessage, exception.message)
    }

    @Test
    fun `should create exception with default message`() {
        // Executar o teste
        val exception = ProdutoNotFoundException()

        // Expectativas - Asserções
        assertEquals("Produto Não Encontrado!", exception.message)
    }
}