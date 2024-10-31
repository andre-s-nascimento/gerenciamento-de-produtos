package dev.andrenascimento.gerenciamento_de_produtos.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ProdutoNotFoundException::class)
    fun handleProdutoNotFoundException(ex: ProdutoNotFoundException): ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}