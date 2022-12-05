package com.kodlamaio.rentalService.business.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;

@FeignClient(value="carClient", url="http://localhost:9011/stock/api/cars")
public interface CarClient {
	@RequestMapping(method= RequestMethod.GET ,value = "/{carId}")
	@Headers(value="Content-Type: application/json")
	 void checkCarAvailabel(@PathVariable String carId);
}
