package com.oodlefinance.mehmet.aktas.external.service;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;

import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This interface class will be doing creation messages and retrieving message detail.
 */

public interface MessageService {

	/**
	 * This method will return all messages
	 *
	 * @return message list will return
	 */
	public List<MessageDto> getMessages();


	/**
	 * This method will create new message.
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	public MessageDto createMessage(NewMessageRequest newMessageRequest);


	/**
	 * This method will return message details for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	public MessageDto getMessage(Long id);

	/**
	 * This method will delete message for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	public String deleteMessage(Long id);

}
