package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.controllers.ProdutoController
import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import dev.andrenascimento.gerenciamento_de_produtos.services.ProdutoService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*

class ProdutoControllerTest {

    @InjectMocks
    private lateinit var produtoController: ProdutoController

    @Mock
    private lateinit var produtoService: ProdutoService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should return a list of produtos`() {
        // Preparação
        val produtos = generateProdutoResponse(2)
        `when`(produtoService.listarProdutos()).thenReturn(produtos)

        // Executar o teste
        val response = produtoController.listarProdutos()

        // Expectativas - Asserções
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(produtos, response.body)
        verify(produtoService, times(1)).listarProdutos()
    }

    @Test
    fun `should create produto when request is valid`() {
        // Preparação
        val produtoRequest = ProdutoRequestBuilder()
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()
        val produtoResponse = ProdutoResponseBuilder()
            .withId(5L)
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto 1")
            .withQuantidadeEmEstoque(5)
            .build()

        `when`(produtoService.criarProduto(produtoRequest)).thenReturn(produtoResponse)

        // Executar o teste
        val response = produtoController.criarProduto(produtoRequest)

        // Expectativas - Asserts
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(produtoResponse, response.body)
        verify(produtoService, times(1)).criarProduto(produtoRequest)
    }

    @Test
    fun `should update product when request is valid`() {
        // Preparação
        val produtoId = 1L
        val produtoRequest = ProdutoRequestBuilder()
            .withNome("Produto Atualizado")
            .withPreco(15.0)
            .withDescricao("Descrição Atualizada")
            .withQuantidadeEmEstoque(10)
            .build()
        val produtoResponse = ProdutoResponseBuilder()
            .withId(produtoId)
            .withNome("Produto Atualizado")
            .withPreco(15.0)
            .withDescricao("Descrição Atualizada")
            .withQuantidadeEmEstoque(10)
            .build()

        `when`(produtoService.atualizarProduto(produtoId, produtoRequest)).thenReturn(produtoResponse)

        // Execute o teste
        val response = produtoController.atualizarProduto(produtoId, produtoRequest)

        // Expectativas - Asserts
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(produtoResponse, response.body)
        verify(produtoService, times(1)).atualizarProduto(produtoId, produtoRequest)
    }

    @Test
    fun `should delete product when id is valid`() {
        // Preparação
        val produtoId = 1L

        doNothing().`when`(produtoService).excluirProduto(produtoId)

        // Execute o teste
        val response = produtoController.excluirProduto(produtoId)

        // Expectativas - Asserts
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(produtoService, times(1)).excluirProduto(produtoId)
    }

    @Test
    fun `should return not found when deleting non-existent produto`() {
        // Preparação
        val produtoId = 1L
        doThrow(ProdutoNotFoundException::class.java).`when`(produtoService).excluirProduto(produtoId)

        // Execute o teste
        assertThrows(ProdutoNotFoundException::class.java) { produtoController.excluirProduto(produtoId) }

        // Expectativas - Asserts
        verify(produtoService, times(1)).excluirProduto(produtoId)
    }

    @Test
    fun `should return internal server error when service throws exception`() {
        // Preparação
        val produtoId = 1L
        val produtoRequest = ProdutoRequestBuilder()
            .withNome("Produto Teste")
            .withPreco(10.0)
            .withDescricao("Descrição Produto Teste")
            .withQuantidadeEmEstoque(5)
            .build()
        doThrow(RuntimeException("Erro genérico")).`when`(produtoService).atualizarProduto(produtoId, produtoRequest)

        // Executar o teste
        val exception = assertThrows(RuntimeException::class.java) {
            produtoController.atualizarProduto(produtoId, produtoRequest)
        }

        // Expectativas - Asserções
        assertEquals("Erro genérico", exception.message)
    }

    @Test
    fun `should return not found when produto id does not exist`() {
        // Preparação
        val produtoId = 1L
        val produtoRequest = ProdutoRequestBuilder()
            .withNome("Produto Teste")
            .withPreco(10.0)
            .withDescricao("Descrição Produto Teste")
            .withQuantidadeEmEstoque(5)
            .build()
        doThrow(ProdutoNotFoundException::class.java).`when`(produtoService).atualizarProduto(produtoId, produtoRequest)

        // Executar o teste - Expectativas - Asserções
        assertThrows(ProdutoNotFoundException::class.java) {
            produtoController.atualizarProduto(produtoId, produtoRequest)
        }
    }

    private fun generateProdutoResponse(quantidade: Int): List<ProdutoResponse> {
        if (quantidade <= 0) {
            return emptyList() // Retorna lista vazia para quantidade 0 ou negativa
        }

        return (0 until quantidade).map { i ->
            ProdutoResponseBuilder()
                .withId(i.toLong())
                .withNome("Produto $i")
                .withPreco(i * 1.99)
                .withDescricao("Descrição do Produto $i")
                .withQuantidadeEmEstoque(i)
                .build()
        }
    }
}