package com.algaworks.algafood;

import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private Flyway flyway;

	@BeforeEach
	public void setup(){
		//no caso de haver falha na validação da requisição irá exibir logs
		//mostrando no terminal o que era esperado da requisição e o que foi recebido.
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		flyway.migrate();
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
		//no caso de haver falha na validação da requisição irá exibir logs
		//mostrando no terminal o que era esperado da requisição e o que foi recebido.
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given()
			.accept(JSON)
		.when()
			.get()
		.then()
				.body("", hasSize(4));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinhas(){
		given()
				.body("{ \"nome\": \"Chinesa\" }")
				.contentType(JSON)
				.accept(JSON)
		.when()
				.post()
		.then()
				.statusCode(CREATED.value());

	}

}