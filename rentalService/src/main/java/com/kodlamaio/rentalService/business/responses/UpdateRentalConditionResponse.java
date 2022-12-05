package com.kodlamaio.rentalService.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalConditionResponse {
	private String rentalId;
	private int condition;
}
