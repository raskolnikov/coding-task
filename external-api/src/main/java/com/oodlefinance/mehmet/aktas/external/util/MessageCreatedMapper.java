package com.oodlefinance.mehmet.aktas.external.util;

import com.oodlefinance.mehmet.aktas.core.response.MessageDto;
import com.oodlefinance.mehmet.aktas.external.response.MessageCreatedDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MessageCreatedMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public MessageCreatedDto convertToMessageCreatedDto(MessageDto message);
}
