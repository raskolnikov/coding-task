package com.oodlefinance.mehmet.aktas.internal.util;

import com.oodlefinance.mehmet.aktas.internal.db.Message;
import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Created by Mehmet Aktas on 2021-05-19
 */

@Mapper(componentModel = "spring")
public interface MessageMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public MessageDto convertToMessageDto(Message message);
}
