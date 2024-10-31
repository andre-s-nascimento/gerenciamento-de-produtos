package dev.andrenascimento.gerenciamento_de_produtos.exceptions

class ProdutoNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor() : super("Produto Não Encontrado!")
}