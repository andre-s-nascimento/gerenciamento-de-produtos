package dev.andrenascimento.gerenciamento_de_produtos.repositories

import dev.andrenascimento.gerenciamento_de_produtos.models.Produto
import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository : JpaRepository<Produto, Long>