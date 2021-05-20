package com.oodlefinance.mehmet.aktas.internal.controller;

import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.internal.repository.MessageInMemoryRepository;
import com.oodlefinance.mehmet.aktas.internal.repository.MessageRepository;
import com.oodlefinance.mehmet.aktas.internal.util.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This API Controller will be responsible for accepting message CRUD or related operations
 */


@RestController
@RequestMapping("/message")
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	private final MessageRepository messageRepository;
	private final MessageMapper messageMapper;

	@Autowired
	public MessageController(MessageInMemoryRepository messageInMemoryRepository,
			MessageMapper messageMapper) {

		this.messageRepository = messageInMemoryRepository;
		this.messageMapper = messageMapper;
	}

	/**
	 * This API method will retrieve all message detail
	 * <p>
	 *
	 * @return all message details
	 */
	@GetMapping(value = "/",  produces = "application/json")
	public Collection<MessageDto> getMessages() {

		Collection<Message> messageList = messageRepository.getMessages();

		return messageList.stream().map(messageMapper::convertToMessageDto).collect(
				Collectors.toList());

	}

	/**
	 * This API method will create new message.
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public MessageDto createMessage(@Valid @RequestBody NewMessageRequest newMessageRequest) {

		LOGGER.debug("New message creation request is received");

		Message message = messageRepository.createMessage(newMessageRequest);

		LOGGER.debug("New message created {}", message.getId());

		return messageMapper.convertToMessageDto(message);

	}

	/**
	 * This API method will retrieve message detail for given @param id
	 * <p>
	 * Message object will have  latest balance in it
	 *
	 * @param id
	 * 		message id
	 * @return message details
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public MessageDto getMessage(@PathVariable("id") long id) {

		Message message = messageRepository.getMessage(id);

		return messageMapper.convertToMessageDto(message);

	}


	/**
	 * This API method will delete message for given @param id
	 * <p>
	 *
	 * @param id message id
	 * @return message delete confirmation
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public String deleteMessage(
			@PathVariable("id") long id) {

		return messageRepository.deleteMessage(id);

	}
}
