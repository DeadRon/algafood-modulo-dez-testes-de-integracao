package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		//ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarFalharAoCadastrarQuandoSemNome(){
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		ConstraintViolationException erroEsperado = assertThrows(ConstraintViolationException.class, () -> cadastroCozinhaService.salvar(novaCozinha));
		assertThat(erroEsperado).isNotNull();

	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso(){
		EntidadeEmUsoException erroEsperado = assertThrows(EntidadeEmUsoException.class, () -> cadastroCozinhaService.excluir(1L));
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente(){
		CozinhaNaoEncontradaException erroEsperado = assertThrows(CozinhaNaoEncontradaException.class, () -> cadastroCozinhaService.excluir(10L));
		assertThat(erroEsperado).isNotNull();
	}

}