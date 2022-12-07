package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelUpdatedEvent {
	private String message;
	private String modelId;
	private String brandId;
	private String modelName;
	private String brandName;
}
