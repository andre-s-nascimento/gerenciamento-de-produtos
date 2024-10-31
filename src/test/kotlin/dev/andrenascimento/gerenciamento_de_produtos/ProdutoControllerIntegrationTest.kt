package dev.andrenascimento.gerenciamento_de_produtos

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import dev.andrenascimento.gerenciamento_de_produtos.services.ProdutoService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.mockito.kotlin.any

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var produtoService: ProdutoService

    @Test
    fun shouldListProdutos() {
        // Preparação
        val produtoResponse = ProdutoResponseBuilder()
            .withId(1L)
            .withNome("Produto A")
            .withPreco(10.0)
            .withDescricao("Descrição do Produto A")
            .withQuantidadeEmEstoque(100)
            .build()

        val produtos = listOf(produtoResponse)
        `when`(produtoService.listarProdutos()).thenReturn(produtos)

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(get("/produtos"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nome").value("Produto A"))
            .andExpect(jsonPath("$[0].preco").value(10))
            .andExpect(jsonPath("$[0].descricao").value("Descrição do Produto A"))
            .andExpect(jsonPath("$[0].quantidadeEmEstoque").value(100))
    }

    @Test
    fun shouldCreateProduto() {
        // Preparação
        val novoProduto = ProdutoRequestBuilder()
            .withNome("Produto Teste E")
            .withDescricao("Descrição Teste E")
            .withPreco(50.0)
            .withQuantidadeEmEstoque(500)
            .build()

        val produtoResponse = ProdutoResponseBuilder()
            .withId(5L)
            .withNome("Produto Teste E")
            .withDescricao("Descrição Teste E")
            .withPreco(50.0)
            .withQuantidadeEmEstoque(500)
            .build()

        `when`(produtoService.criarProduto(any())).thenReturn(produtoResponse)

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(
            post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoProduto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.nome").value("Produto Teste E"))
            .andExpect(jsonPath("$.preco").value(50.0))
            .andExpect(jsonPath("$.descricao").value("Descrição Teste E"))
            .andExpect(jsonPath("$.quantidadeEmEstoque").value(500))
    }

    @Test
    fun shouldUpdateProduto() {
        // Preparação
        val produtoId = 5L
        val produtoAtualizado = ProdutoRequestBuilder()
            .withNome("Produto Teste E Atualizado")
            .withDescricao("Descrição Teste E Atualizada")
            .withPreco(25.0)
            .withQuantidadeEmEstoque(250)
            .build()

        val produtoResponse = ProdutoResponseBuilder()
            .withId(produtoId)
            .withNome("Produto Teste E Atualizado")
            .withDescricao("Descrição Teste E Atualizada")
            .withPreco(25.0)
            .withQuantidadeEmEstoque(250)
            .build()

        `when`(produtoService.atualizarProduto(any(), any())).thenReturn(produtoResponse)

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(
            put("/produtos/{id}", produtoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoAtualizado))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.nome").value("Produto Teste E Atualizado"))
            .andExpect(jsonPath("$.preco").value(25.0))
            .andExpect(jsonPath("$.descricao").value("Descrição Teste E Atualizada"))
            .andExpect(jsonPath("$.quantidadeEmEstoque").value(250))
    }

    @Test
    fun shouldDeleteProduto() {
        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(delete("/produtos/{id}", 1))
            .andExpect(status().isNoContent)
        verify(produtoService, times(1)).excluirProduto(1L)
    }

    @Test
    fun shouldReturnNotFoundWhenDeleteAProdutoThatIsNotFound() {
        // Preparação
        doThrow(ProdutoNotFoundException("Produto Não Encontrado!"))
            .`when`(produtoService).excluirProduto(129L)

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(delete("/produtos/{id}", 129L))
            .andExpect(status().isNotFound)
            .andExpect(content().string("Produto Não Encontrado!"))
        verify(produtoService, times(1)).excluirProduto(eq(129L))
    }

    @Test
    fun shouldReturnNotFoundWhenUpdateAProdutoThatIsNotFound() {
        // Preparação
        val produtoAtualizado = ProdutoRequestBuilder()
            .withNome("Produto Atualizado")
            .withDescricao("Nova Descrição")
            .withPreco(20.0)
            .withQuantidadeEmEstoque(200)
            .build()

        `when`(produtoService.atualizarProduto(eq(129L), any()))
            .thenThrow(ProdutoNotFoundException("Produto Não Encontrado!"))

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(
            put("/produtos/{id}", 129L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoAtualizado))
        )
            .andExpect(status().isNotFound)
            .andExpect(content().string("Produto Não Encontrado!"))

        verify(produtoService, times(1)).atualizarProduto(eq(129L), any())
    }
}