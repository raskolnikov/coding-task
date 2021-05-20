package com.oodlefinance.mehmet.aktas.external.service;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.external.proxy.InternalServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mehmet Aktas on 2021-05-19
 * <p>
 * This service class will be doing creation messages and retrieving message detail.
 */

@Service
public class MessageFromInternalService implements MessageService {

	private final InternalServiceClient internalServiceProxy;

	@Autowired
	public MessageFromInternalService(InternalServiceClient internalServiceProxy) {

		this.internalServiceProxy = internalServiceProxy;
	}

	/**
	 * This method will return all messages
	 *
	 * @return message list will return
	 */
	@Override
	public List<MessageDto> getMessages() {

		return internalServiceProxy.getMessages();

	}

	/**
	 * This method will create new message.
	 *
	 * @param newMessageRequest
	 * 		new message details will be in here
	 * @return created message detail
	 */
	@Override
	public MessageDto createMessage(NewMessageRequest newMessageRequest) {

		return internalServiceProxy.createMessage(newMessageRequest);

	}

	/**
	 * This method will return message details for given id
	 *
	 * @param id
	 * 		message id
	 * @return message object will return
	 */
	@Override
	public MessageDto getMessage(Long id) {

		return internalServiceProxy.getMessage(id);

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

		return internalServiceProxy.deleteMessage(id);

	}

}
