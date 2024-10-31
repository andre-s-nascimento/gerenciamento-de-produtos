package dev.andrenascimento.gerenciamento_de_produtos.dtos

// Para enviar dados de produtos como resposta da API.
data class ProdutoResponse(
    var id: Long? = null,
    var nome: String? = null,
    var preco: Double? = null,
    var descricao: String? = null,
    var quantidadeEmEstoque: Int? = null
)