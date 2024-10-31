package dev.andrenascimento.gerenciamento_de_produtos

import dev.andrenascimento.gerenciamento_de_produtos.dtos.ProdutoResponse
import dev.andrenascimento.gerenciamento_de_produtos.exceptions.ProdutoNotFoundException
import dev.andrenascimento.gerenciamento_de_produtos.mappers.ProdutoMapper
import dev.andrenascimento.gerenciamento_de_produtos.repositories.ProdutoRepository
import dev.andrenascimento.gerenciamento_de_produtos.services.ProdutoServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

class ProdutoServiceTest {

    @InjectMocks
    private lateinit var produtoService: ProdutoServiceImpl

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @Mock
    private lateinit var produtoMapper: ProdutoMapper

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should update produto when id is valid`() {
        // Preparação
        val produtoId = 1L
        val request = ProdutoRequestBuilder()
            .withNome("Produto Atualizado")
            .withPreco(15.0)
            .withDescricao("Descrição Atualizada")
            .withQuantidadeEmEstoque(10)
            .build()
        val produtoExistente = ProdutoBuilder()
            .withId(produtoId)
            .withNome("Produto Antigo")
            .withPreco(10.0)
            .withDescricao("Descrição Antiga")
            .withQuantidadeEmEstoque(5)
            .build()
        val produtoAtualizado = ProdutoBuilder()
            .withId(produtoId)
            .withNome("Produto Atualizado")
            .withPreco(15.0)
            .withDescricao("Descrição Atualizada")
            .withQuantidadeEmEstoque(10)
            .build()

        `when`(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente))
        `when`(produtoRepository.existsById(produtoId)).thenReturn(true)
        `when`(produtoMapper.toEntity(request)).thenReturn(produtoAtualizado)
        `when`(produtoRepository.save(produtoAtualizado)).thenReturn(produtoAtualizado)
        `when`(produtoMapper.toResponse(produtoAtualizado)).thenReturn(
            ProdutoResponseBuilder()
                .withId(produtoId)
                .withNome("Produto Atualizado")
                .withPreco(15.0)
                .withDescricao("Descrição Atualizada")
                .withQuantidadeEmEstoque(10)
                .build()
        )

        // Executar o teste
        val response = produtoService.atualizarProduto(produtoId, request)

        // Expectativas - Asserções
        assertNotNull(response)
        assertEquals("Produto Atualizado", response.nome)
        assertEquals(15.0, response.preco)
    }

    @Test
    fun `should throw ProdutoNotFoundException when id does not exist`() {
        // Preparação
        val produtoId = 1L
        val request = ProdutoRequestBuilder()
            .withNome("Produto Atualizado")
            .withPreco(15.0)
            .withDescricao("Descrição Atualizada")
            .withQuantidadeEmEstoque(10)
            .build()

        `when`(produtoRepository.findById(produtoId)).thenReturn(Optional.empty())

        // Executar com Expectativas - Asserções
        assertThrows<ProdutoNotFoundException> { produtoService.atualizarProduto(produtoId, request) }
    }

    @Test
    fun `should create produto successfully`() {
        // Preparação
        val request = ProdutoRequestBuilder()
            .withNome("Produto Novo")
            .withPreco(20.0)
            .withDescricao("Descrição do Produto Novo")
            .withQuantidadeEmEstoque(5)
            .build()
        val produto = ProdutoBuilder()
            .withId(null)
            .withNome("Produto Novo")
            .withPreco(20.0)
            .withDescricao("Descrição do Produto Novo")
            .withQuantidadeEmEstoque(5)
            .build()
        val produtoSalvo = ProdutoBuilder()
            .withId(1L)
            .withNome("Produto Novo")
            .withPreco(20.0)
            .withDescricao("Descrição do Produto Novo")
            .withQuantidadeEmEstoque(5)
            .build()

        `when`(produtoMapper.toEntity(request)).thenReturn(produto)
        `when`(produtoRepository.save(produto)).thenReturn(produtoSalvo)
        `when`(produtoMapper.toResponse(produtoSalvo)).thenReturn(
            ProdutoResponse(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5)
        )

        // Executar o teste
        val response = produtoService.criarProduto(request)

        // Expectativas - Asserções
        assertNotNull(response)
        assertEquals("Produto Novo", response.nome)
    }

    @Test
    fun `should delete produto when id is valid`() {
        // Preparação
        val produtoId = 1L

        `when`(produtoRepository.existsById(produtoId)).thenReturn(true)

        // Executar o teste - Expectativas - Asserções
        assertDoesNotThrow { produtoService.excluirProduto(produtoId) }
        verify(produtoRepository).deleteById(produtoId)
    }

    @Test
    fun `should throw ProdutoNotFoundException when deleting non-existent produto`() {
        // Preparação
        val produtoId = 1L

        `when`(produtoRepository.existsById(produtoId)).thenReturn(false)

        // Executar o teste - Expectativas - Asserções
        assertThrows<ProdutoNotFoundException> { produtoService.excluirProduto(produtoId) }
    }

    @Test
    fun `should return list of produtos`() {
        // Preparação
        val produto1 = ProdutoBuilder()
            .withId(1L)
            .withNome("Produto 1")
            .withPreco(10.0)
            .withDescricao("Descrição 1")
            .withQuantidadeEmEstoque(5)
            .build()
        val produto2 = ProdutoBuilder()
            .withId(2L)
            .withNome("Produto 2")
            .withPreco(20.0)
            .withDescricao("Descrição 2")
            .withQuantidadeEmEstoque(10)
            .build()

        val produtos = listOf(produto1, produto2)

        `when`(produtoRepository.findAll()).thenReturn(produtos)
        `when`(produtoMapper.toResponse(produto1)).thenReturn(
            ProdutoResponseBuilder()
                .withId(1L)
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição 1")
                .withQuantidadeEmEstoque(5)
                .build()
        )
        `when`(produtoMapper.toResponse(produto2)).thenReturn(
            ProdutoResponseBuilder()
                .withId(2L)
                .withNome("Produto 2")
                .withPreco(20.0)
                .withDescricao("Descrição 2")
                .withQuantidadeEmEstoque(10)
                .build()
        )

        // Executar o teste
        val response = produtoService.listarProdutos()

        // Expectativas - Asserções
        assertNotNull(response)
        assertEquals(2, response.size)
    }
}