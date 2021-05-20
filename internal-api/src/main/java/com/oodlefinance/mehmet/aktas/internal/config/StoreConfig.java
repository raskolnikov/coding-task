package com.oodlefinance.mehmet.aktas.internal.config;

import com.oodlefinance.mehmet.aktas.internal.db.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class StoreConfig {

	@Bean(name = "messageStore")
	public Map<Long, Message> getMessageStore(){

		return new HashMap<>();

	}

	@Bean(name = "indexSeq")
	public AtomicLong getIndexSeq(){

		return new AtomicLong(0L);

	}

}
