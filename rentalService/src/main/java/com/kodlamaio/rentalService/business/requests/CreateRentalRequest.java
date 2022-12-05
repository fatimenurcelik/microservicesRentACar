package com.kodlamaio.rentalService.business.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	@NotNull
	private String carId;
	@NotNull
	@Min(1)
	private int rentedForDays;
	@NotNull
	@Min(100)
	private double dailyPrice;
	
	private String cardHolder;
	
	private String cardNo;
	
	private double Balance;
}
