package com.oodlefinance.mehmet.aktas.internal.repository;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.internal.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This service class will be doing creation messages and retrieving message detail.
 */

@Service
public class MessageInMemoryRepository implements MessageRepository {

	private final Map<Long, Message> messageStore;
	private final AtomicLong indexSeq;

	@Autowired
	public MessageInMemoryRepository(@Qualifier("messageStore") Map<Long, Message> messageStore,
			@Qualifier("indexSeq") AtomicLong indexSeq) {

		this.messageStore = messageStore;
		this.indexSeq = indexSeq;

	}

	/**
	 * This method will create new message.
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	@Override
	public Message createMessage(NewMessageRequest newMessageRequest) {

		Message message = new Message();

		message.setMessage(newMessageRequest.getMessage());
		message.setId(indexSeq.getAndIncrement());

		messageStore.put(message.getId(), message);

		return message;

	}

	/**
	 * This method will return all messages
	 *
	 * @return message list will return
	 */
	@Override
	public Collection<Message> getMessages() {

		return messageStore.values();

	}

	/**
	 * This method will return message details for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	@Override
	public Message getMessage(Long id) {

		return Optional.of(messageStore.get(id)).orElseThrow(() -> new NotFoundException("Message not found", id));

	}

	/**
	 * This method will delete message for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	@Override
	public String deleteMessage(Long id) {

		if (!messageStore.containsKey(id)) {

			throw new NotFoundException("Message not found, It can not be deleted", id);

		}

		messageStore.remove(id);

		return String.format("Message {%s} deleted", id);

	}

}
