package com.kodlamaio.invoiceService.business.abstracts;

import com.kodlamaio.common.events.PaymentCreatedEvent;
import com.kodlamaio.invoiceService.entities.Invoice;

public interface InvoiceService {

	Invoice add(PaymentCreatedEvent event);
}
