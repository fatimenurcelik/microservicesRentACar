package com.kodlamaio.paymentService.business.abstracts;

import com.kodlamaio.common.rentalPayment.PayMoneyRequest;
import com.kodlamaio.paymentService.business.responses.CreatePaymentResponse;

public interface PaymentService {
	CreatePaymentResponse add (PayMoneyRequest createPaymentRequest);
}