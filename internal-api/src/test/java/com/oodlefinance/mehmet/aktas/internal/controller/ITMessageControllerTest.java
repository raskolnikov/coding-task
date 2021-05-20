package com.oodlefinance.mehmet.aktas.internal.controller;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.internal.InternalApplication;
import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.internal.repository.MessageInMemoryRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InternalApplication.class)
public class ITMessageControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	@Qualifier("messageStore")
	private Map<Long, Message> messageStore;

	@Autowired
	@Qualifier("indexSeq")
	private AtomicLong indexSeq;

	@Autowired
	private MessageInMemoryRepository messageInMemoryRepository;

	private String apiEndPoint;

	@AfterEach
	void afterEach() {

		messageStore.clear();
		indexSeq.set(0);

	}

	@BeforeEach
	void before() {

		apiEndPoint = String.format("http://localhost:%s/message/", port);

	}


	@Test
	void createMessage_whenValidMessagesDetailsAreProvidedThenItShouldCreateMessage() throws Exception {

		NewMessageRequest newMessageRequest = new NewMessageRequest();
		newMessageRequest.setMessage("Message1");

		Response response = given().contentType(ContentType.JSON).body(newMessageRequest).post(apiEndPoint);

		assertThat(response.statusCode()).isEqualTo(200);

		int id = response.jsonPath().get("id");

		Message actual = messageStore.get((long) id);

		assertThat(actual.getMessage()).isEqualTo(newMessageRequest.getMessage());

	}


	@Test
	void getMessage_whenValidMessageIdIsGivenThenItShouldReturnMessage() throws Exception {

		Message message = new Message();
		message.setMessage("Message1");
		message.setId(1L);

		messageStore.put(message.getId(), message);

		given().get(apiEndPoint + message.getId()).then().statusCode(is(200)).body(containsString("Message1"));
	}

	@Test
	void deleteMessage_whenValidMessageIdIsGivenThenItShouldDeleteTheMessage() throws Exception {

		Message message = new Message();
		message.setMessage("Message1");
		message.setId(1L);

		messageStore.put(message.getId(), message);

		given().delete(apiEndPoint + message.getId()).then().statusCode(is(200));

		Message actual = messageStore.get(message.getId());

		assertThat(actual).isNull();
	}


	@Test
	void getMessageMessages_whenValidParamsAreGivenThenItShouldReturnMessageList() throws Exception {

		Message message = new Message();
		message.setMessage("Message1");
		message.setId(1L);

		messageStore.put(message.getId(), message);

		Message message2 = new Message();
		message2.setMessage("Message1");
		message2.setId(2L);

		messageStore.put(message2.getId(), message2);

		Response response = given().get(apiEndPoint);

		assertThat(response.statusCode()).isEqualTo(200);

		List<Integer> idList = response.jsonPath().get("id");

		assertThat(idList).contains(message.getId().intValue(), message2.getId().intValue());

	}

}
