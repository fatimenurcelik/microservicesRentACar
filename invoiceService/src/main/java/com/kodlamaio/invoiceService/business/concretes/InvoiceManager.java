package com.kodlamaio.invoiceService.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.PaymentCreatedEvent;
import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.dataAccess.InvoiceRepository;
import com.kodlamaio.invoiceService.entities.Invoice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
	private InvoiceRepository invoiceRepository;

	@Override
	public Invoice add(PaymentCreatedEvent event) {
		Invoice invoice = new Invoice();
		invoice.setId(UUID.randomUUID().toString());
		invoice.setRentalId(event.getRentalId());

		PaymentCreatedEvent createdEvent = new PaymentCreatedEvent();
		createdEvent.setRentalId(event.getRentalId());
		createdEvent.setMessage("invoice created");

		Invoice result = this.invoiceRepository.save(invoice);
		return result;
	}
}