package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalUpdatedEvent {
	private String message;
	private String newCarId;	
	private String oldCarId;	
}
