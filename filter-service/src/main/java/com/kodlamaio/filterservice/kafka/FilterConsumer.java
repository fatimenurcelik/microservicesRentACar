package com.kodlamaio.filterservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.BrandDeletedEvent;
import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdatedEvent;
import com.kodlamaio.common.events.ModelDeletedEvent;
import com.kodlamaio.common.events.ModelUpdatedEvent;
import com.kodlamaio.filterservice.business.abstracts.FilterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FilterConsumer {
	private FilterService filterService ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterConsumer.class);
	
		@KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            ,groupId = "createCar"
	    )
	    public void consume(CarCreatedEvent event){
	        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
	        	//write
	        filterService.addCar(event);
	    }
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "deleteCar"
				)
		public void consume(CarDeletedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.deleteCar(event);
		}
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "updateCar"
				)
		public void consume(CarUpdatedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.updateCar(event);
		}
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "updateBrand"
				)
		public void consume(BrandUpdatedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.updateBrand(event);
		}
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "updateModel"
				)
		public void consume(ModelUpdatedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.updateModel(event);
		}
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "deleteBrand"
				)
		public void consume(BrandDeletedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.deleteAllByBrandId(event.getBrandId());
		}
		
		@KafkaListener(
				topics = "${spring.kafka.topic.name}"
				,groupId = "deleteModel"
				)
		public void consume(ModelDeletedEvent event){
			LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
			//write
			filterService.deleteAllByModelId(event.getModelId());
		}

}
