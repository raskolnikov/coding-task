package com.oodlefinance.mehmet.aktas.internal.util;

import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.internal.db.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

	@InjectMocks
	private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

	@Test
	void convertToMessageDto_whenAccountIsGivenThenItShouldConvertToMessageDto() {

		Message expected = new Message();

		expected.setId(11L);
		expected.setMessage("This is the message");

		MessageDto actual = messageMapper.convertToMessageDto(expected);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


	}
}