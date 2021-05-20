package com.oodlefinance.mehmet.aktas.internal.repository;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.internal.db.Message;

import java.util.Collection;
import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This interface class will be doing crud operations for message detail.
 */

public interface MessageRepository {

	/**
	 * This method will return all messages
	 *
	 * @return message list will return
	 */
	public Collection<Message> getMessages();


	/**
	 * This method will create new message.
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	public Message createMessage(NewMessageRequest newMessageRequest);


	/**
	 * This method will return message details for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	public Message getMessage(Long id);

	/**
	 * This method will delete message for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	public String deleteMessage(Long id);

}