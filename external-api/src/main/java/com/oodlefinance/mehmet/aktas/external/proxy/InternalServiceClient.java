package com.oodlefinance.mehmet.aktas.external.proxy;

import com.oodlefinance.mehmet.aktas.core.request.NewMessageRequest;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.external.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "InternalService", configuration = FeignClientConfiguration.class)
public interface InternalServiceClient {

	@RequestMapping(value = "/message/", method = RequestMethod.GET)
	List<MessageDto> getMessages();

	@RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
	MessageDto getMessage(@PathVariable("id") Long id);

	@RequestMapping(value = "/message/", method = RequestMethod.POST, consumes = "application/json")
	MessageDto createMessage(NewMessageRequest newMessageRequest);

	@RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
	String deleteMessage(@PathVariable("id") Long id);

}
