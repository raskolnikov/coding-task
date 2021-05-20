package com.oodlefinance.mehmet.aktas.external.service;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.internal.InternalApplication;
import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.internal.repository.MessageInMemoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.graph.MessageGatewayNode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InternalApplication.class)
class ITMessageInMemoryRepositoryTest {

	@Autowired
	@Qualifier("messageStore")
	private Map<Long, Message> messageStore;

	@Autowired
	@Qualifier("indexSeq")
	private AtomicLong indexSeq;

	@Autowired
	private MessageInMemoryRepository messageInMemoryRepository;

	@AfterEach
	void afterEach() {

		messageStore.clear();
		indexSeq.set(0);

	}

	@Test
	void createMessage_whenValidRequestIsGivenThenItShouldCreateNewMessage() {

		NewMessageRequest newMessageRequest = new NewMessageRequest();
		newMessageRequest.setMessage("This is the message");

		Message response = messageInMemoryRepository.createMessage(newMessageRequest);

		Message actual = messageStore.get(response.getId());

		assertThat(actual.getMessage()).isEqualTo(newMessageRequest.getMessage());

	}

	@Test
	void getMessage_whenInvalidMessageIdIsGivenThenItShouldThrowException() {

		NewMessageRequest newMessageRequest = new NewMessageRequest();
		newMessageRequest.setMessage("This is the message");

		Message response = messageInMemoryRepository.createMessage(newMessageRequest);

		Message actual = messageInMemoryRepository.getMessage(response.getId());

		assertThat(actual.getMessage()).isEqualTo(newMessageRequest.getMessage());

	}

	@Test
	void getMessages_whenWhenAllMessageRequestedThenItShouldReturnAllMessages() {

		NewMessageRequest newMessageRequest = new NewMessageRequest();
		newMessageRequest.setMessage("Message 1");

		messageInMemoryRepository.createMessage(newMessageRequest);

		NewMessageRequest newMessageRequest2 = new NewMessageRequest();
		newMessageRequest2.setMessage("Message 2");

		messageInMemoryRepository.createMessage(newMessageRequest2);

		Collection<Message> actual = messageInMemoryRepository.getMessages();

		assertThat(actual).extracting("message").contains("Message 1", "Message 2");

	}

	@Test
	void deleteMessage_whenValidMessageIdIsGivenThenItShouldDeleteMessage() {

		NewMessageRequest newMessageRequest = new NewMessageRequest();
		newMessageRequest.setMessage("This is the message");

		Message response = messageInMemoryRepository.createMessage(newMessageRequest);

		messageInMemoryRepository.deleteMessage(response.getId());

		Message actual = messageStore.get(response.getId());

		assertThat(actual).isNull();

	}

}