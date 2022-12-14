package com.kodlamaio.invoiceService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InvoiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceServiceApplication.class, args);
	}
	
//	@Bean
//	public ModelMapper getModelMapper() {
//		return new ModelMapper();
//	}
//	
//	@Bean
//	public ModelMapperService getMapperService(ModelMapper modelMapper) {
//		return new ModelMapperManager(modelMapper);
//	}


}
