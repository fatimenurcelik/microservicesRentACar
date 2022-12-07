package com.kodlamaio.inventoryService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.BrandDeletedEvent;
import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdatedEvent;
import com.kodlamaio.common.events.ModelDeletedEvent;
import com.kodlamaio.common.events.ModelUpdatedEvent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FilterProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterProducer.class);

	private NewTopic topic;

	private KafkaTemplate<String, CarCreatedEvent> kafkaTemplateCreated;

	public void sendMessage(CarCreatedEvent carCreatedEvent) {
		LOGGER.info(String.format("Payment created event => %s", carCreatedEvent.toString()));

		Message<CarCreatedEvent> message = MessageBuilder.withPayload(carCreatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateCreated.send(message);
	}

	private KafkaTemplate<String, CarUpdatedEvent> kafkaTemplateUpdated;

	public void sendMessage(CarUpdatedEvent carUpdatedEvent) {
		LOGGER.info(String.format("Payment created event => %s", carUpdatedEvent.toString()));

		Message<CarUpdatedEvent> message = MessageBuilder.withPayload(carUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateUpdated.send(message);
	}

	private KafkaTemplate<String, CarDeletedEvent> kafkaTemplateDeleted;

	public void sendMessage(CarDeletedEvent carDeletedEvent) {
		LOGGER.info(String.format("Payment created event => %s", carDeletedEvent.toString()));

		Message<CarDeletedEvent> message = MessageBuilder.withPayload(carDeletedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateDeleted.send(message);
	}

	private KafkaTemplate<String, BrandUpdatedEvent> kafkaTemplateBrandUpdated;

	public void sendMessage(BrandUpdatedEvent brandUpdatedEvent) {
		LOGGER.info(String.format("Payment created event => %s", brandUpdatedEvent.toString()));

		Message<BrandUpdatedEvent> message = MessageBuilder.withPayload(brandUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateBrandUpdated.send(message);
	}

	private KafkaTemplate<String, ModelUpdatedEvent> kafkaTemplateModelUpdated;

	public void sendMessage(ModelUpdatedEvent modelUpdatedEvent) {
		LOGGER.info(String.format("Payment created event => %s", modelUpdatedEvent.toString()));

		Message<ModelUpdatedEvent> message = MessageBuilder.withPayload(modelUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateModelUpdated.send(message);
	}
	
	
	
	
	
	private KafkaTemplate<String, BrandDeletedEvent> kafkaTemplateBrandDeleted;
	
	public void sendMessage(BrandDeletedEvent brandDeletedEvent) {
		LOGGER.info(String.format("Payment created event => %s", brandDeletedEvent.toString()));
		
		Message<BrandDeletedEvent> message = MessageBuilder.withPayload(brandDeletedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateBrandDeleted.send(message);
	}
	
	private KafkaTemplate<String, ModelDeletedEvent> kafkaTemplateModelDeleted;
	
	public void sendMessage(ModelDeletedEvent modelDeletedEvent) {
		LOGGER.info(String.format("Payment created event => %s", modelDeletedEvent.toString()));
		
		Message<ModelDeletedEvent> message = MessageBuilder.withPayload(modelDeletedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplateModelDeleted.send(message);
	}
}
