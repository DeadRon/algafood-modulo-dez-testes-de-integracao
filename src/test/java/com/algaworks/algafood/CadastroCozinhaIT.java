package com.algaworks.algafood;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.OK;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setup(){
		//no caso de haver falha na validação da requisição irá exibir logs
		//mostrando no terminal o que era esperado da requisição e o que foi recebido.
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas(){
		given()
			.accept(JSON)
		.when()
			.get()
		.then()
			.statusCode(OK.value());
	}

	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas(){
		given()
			.accept(JSON)
		.when()
			.get()
		.then()
				.body("", hasSize(4))
				.body("nome", hasItems("Indiana", "Tailandesa"));
	}

}