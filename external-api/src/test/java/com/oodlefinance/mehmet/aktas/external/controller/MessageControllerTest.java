package com.oodlefinance.mehmet.aktas.external.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.external.response.MessageCreatedDto;
import com.oodlefinance.mehmet.aktas.external.service.MessageFromInternalService;
import com.oodlefinance.mehmet.aktas.external.util.MessageCreatedMapper;
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
	private MessageFromInternalService messageFromInternalService;

	@MockBean
	private MessageCreatedMapper messageCreatedMapper;

	private String messageControllerPath = "/message/";

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void createMessage_whenValidMessagesDetailsAreProvidedThenItShouldCreateMessage() throws Exception {

		NewMessageRequest newMessageRequest = new NewMessageRequest();

		newMessageRequest.setMessage("Message1");

		MessageDto expectedMessage = mock(MessageDto.class);

		MessageCreatedDto messageCreatedDto = new MessageCreatedDto();

		when(messageFromInternalService.createMessage(any(NewMessageRequest.class))).thenReturn(expectedMessage);
		when(messageCreatedMapper.convertToMessageCreatedDto(expectedMessage)).thenReturn(messageCreatedDto);

		MvcResult mvcResult = this.mockMvc.perform(post(messageControllerPath).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(newMessageRequest))).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		MessageDto actualMessage = objectMapper.readValue(content, MessageDto.class);

		ArgumentCaptor<NewMessageRequest> argumentCaptor = ArgumentCaptor.forClass(NewMessageRequest.class);

		verify(messageFromInternalService, times(1)).createMessage(argumentCaptor.capture());

		assertThat(newMessageRequest).usingRecursiveComparison().isEqualTo(argumentCaptor.getValue());

		verifyNoMoreInteractions(messageFromInternalService);

	}


	@Test
	void getMessage_whenValidMessageIdIsGivenThenItShouldReturnMessage() throws Exception {

		Long messageId = 22L;

		MessageDto messageDto = new MessageDto();
		messageDto.setId(messageId);

		when(messageFromInternalService.getMessage(messageId)).thenReturn(messageDto);

		MvcResult mvcResult = this.mockMvc.perform(get(messageControllerPath + "/{id}",
				messageId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		MessageDto actualMessageDto = objectMapper.readValue(content, MessageDto.class);

		assertThat(actualMessageDto).usingRecursiveComparison().isEqualTo(messageDto);

	}

	@Test
	void getMessageMessages_whenValidParamsAreGivenThenItShouldReturnMessageList() throws Exception {

		Long messageId = 22L;
		int limit = 10;

		MessageDto messageDto = new MessageDto();
		messageDto.setId(12L);

		List<MessageDto> messageList = Collections.nCopies(limit, messageDto);

		when(messageFromInternalService.getMessages()).thenReturn(messageList);

		MvcResult mvcResult = this.mockMvc.perform(get(messageControllerPath,
				messageId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		List<MessageDto> actualMessages = objectMapper.readValue(content, new TypeReference<List<MessageDto>>() {
		});

		assertThat(actualMessages).usingFieldByFieldElementComparator().containsAll(messageList);

	}

}
