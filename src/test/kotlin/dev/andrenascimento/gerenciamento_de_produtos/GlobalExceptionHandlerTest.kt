package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.exceptions.GlobalExceptionHandler
import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalExceptionHandlerTest {
    private val exceptionHandler = GlobalExceptionHandler()

    @Test
    fun `handleProdutoNotFoundException should return NotFound status and message`() {
        // Preparação
        val errorMessage = "Produto não encontrado!"
        val exception = ProdutoNotFoundException(errorMessage)

        // Executar o teste
        val response = exceptionHandler.handleProdutoNotFoundException(exception)

        // Expectativas - Asserções
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).isEqualTo(errorMessage)
    }
}