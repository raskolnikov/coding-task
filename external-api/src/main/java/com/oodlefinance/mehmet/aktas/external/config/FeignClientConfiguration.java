package com.oodlefinance.mehmet.aktas.external.config;

import com.oodlefinance.mehmet.aktas.external.util.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

	@Bean
	public ErrorDecoder errorDecoder() {

		return new CustomErrorDecoder();

	}


}
