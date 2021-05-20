package com.oodlefinance.mehmet.aktas.external.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.oodlefinance.mehmet.aktas.helper.FileLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITMessageControllerTest {

	@LocalServerPort
	private int port;

	private String apiEndPoint;

	private static WireMockServer wireMockServer;

	@BeforeAll
	static void beforeAll() {

		wireMockServer = new WireMockServer(
				options().port(8082));

	    wireMockServer.start();

	}

	@AfterAll
	static void afterAll() {

		wireMockServer.stop();
	}

	@BeforeEach
	void before() {

		apiEndPoint = String.format("http://localhost:%s/message/", port);

	}


	@Test
	void getMessage_whenValidMessageIdIsGivenThenItShouldReturnMessage() throws Exception {

		wireMockServer.stubFor(WireMock.get(urlEqualTo("/message/0"))
				.willReturn(aResponse().withBody(FileLoader.read("classpath:internalApiCreateResponse.json"))
						.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withStatus(200)));


		when().get(apiEndPoint + 0).then().statusCode(is(200)).body(containsString("my message"));


	}


}
