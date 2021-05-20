package com.oodlefinance.mehmet.aktas.internal.controller;

import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.internal.repository.MessageInMemoryRepository;
import com.oodlefinance.mehmet.aktas.internal.util.MessageMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageInMemoryRepository messageInMemoryRepository;

	@MockBean
	private MessageMapper messageMapper;

	private String messageControllerPath = "/message/";

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void createMessage_whenValidMessagesDetailsAreProvidedThenItShouldCreateMessage() throws Exception {

		NewMessageRequest newMessageRequest = new NewMessageRequest();

		newMessageRequest.setMessage("Message1");

		Message expectedMessage = mock(Message.class);

		MessageDto messageDto = new MessageDto();

		when(messageInMemoryRepository.createMessage(any(NewMessageRequest.class))).thenReturn(expectedMessage);
		when(messageMapper.convertToMessageDto(expectedMessage)).thenReturn(messageDto);

		MvcResult mvcResult = this.mockMvc.perform(post(messageControllerPath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(newMessageRequest))).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		MessageDto actualMessage = objectMapper.readValue(content, MessageDto.class);

		ArgumentCaptor<NewMessageRequest> argumentCaptor = ArgumentCaptor.forClass(NewMessageRequest.class);

		verify(messageInMemoryRepository, times(1)).createMessage(argumentCaptor.capture());

		assertThat(newMessageRequest).usingRecursiveComparison().isEqualTo(argumentCaptor.getValue());

		verifyNoMoreInteractions(messageInMemoryRepository);

	}


	@Test
	void getMessage_whenValidMessageIdIsGivenThenItShouldReturnMessage() throws Exception {

		Long messageId = 22L;

		Message message = mock(Message.class);

		when(messageInMemoryRepository.getMessage(messageId)).thenReturn(message);

		MessageDto expectedMessageDto = new MessageDto();
		expectedMessageDto.setId(messageId);

		when(messageMapper.convertToMessageDto(message)).thenReturn(expectedMessageDto);

		MvcResult mvcResult = this.mockMvc.perform(get(messageControllerPath + "/{id}",
				messageId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		MessageDto actualMessageDto = objectMapper.readValue(content, MessageDto.class);

		assertThat(actualMessageDto).usingRecursiveComparison().isEqualTo(expectedMessageDto);

	}

	@Test
	void getMessageMessages_whenValidParamsAreGivenThenItShouldReturnMessageList() throws Exception {

		Long messageId = 22L;
		int limit = 10;

		Message message = mock(Message.class);
		List<Message> messageList = Collections.nCopies(limit, message);

		when(messageInMemoryRepository.getMessages()).thenReturn(messageList);

		MessageDto messageDto = new MessageDto();
		messageDto.setId(12L);

		List<MessageDto> expectedMessageDtoList = Collections.nCopies(limit, messageDto);

		when(messageMapper.convertToMessageDto(message)).thenReturn(messageDto);

		MvcResult mvcResult = this.mockMvc.perform(get(messageControllerPath,
				messageId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		List<MessageDto> actualMessages = objectMapper.readValue(content, new TypeReference<List<MessageDto>>() {
		});

		assertThat(actualMessages).usingFieldByFieldElementComparator().containsAll(expectedMessageDtoList);

	}

}
