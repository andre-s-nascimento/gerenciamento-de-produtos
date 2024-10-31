package dev.andrenascimento.gerenciamento_de_produtos

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GerenciamentoDeProdutosApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun applicationStarts() {
		// Este teste verifica se a aplicação pode ser iniciada sem erros
		GerenciamentoDeProdutosApplication()
	}

}
