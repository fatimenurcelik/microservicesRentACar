package com.kodlamaio.invoiceService.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.events.PaymentCreatedEvent;
import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.entities.Invoice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	private InvoiceService invoiceService;

//	@PostMapping()
//	public Invoice add(@RequestBody PaymentCreatedEvent event) {
//		return invoiceService.add(event);
//	}
}
