package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import util.DatabaseCleaner;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {


	@LocalServerPort
	private int port;

	//@Autowired
	private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setup(){
		//no caso de haver falha na validação da requisição irá exibir logs
		//mostrando no terminal o que era esperado da requisição e o que foi recebido.
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();
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
	public void deveConter2Cozinhas_QuandoConsultarCozinhas(){
		//no caso de haver falha na validação da requisição irá exibir logs
		//mostrando no terminal o que era esperado da requisição e o que foi recebido.
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given()
			.accept(JSON)
		.when()
			.get()
		.then()
				.body("", hasSize(2));
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

	private void prepararDados(){
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha1.setNome("Americana");
		cozinhaRepository.save(cozinha1);
	}

}