package com.payments.invoice.controller;

import com.payments.invoice.model.*;
import com.payments.invoice.service.InvoiceServices;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@Slf4j
public class InvoiceController {
    private static final Logger loggerController = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceServices services;

    @GetMapping("/testAPI")
    public String hello() {
        loggerController.info("Inside Test API Call");
        return "Hello from Spring Boot!";
    }
    @PostMapping("/invoices")
    public ResponseEntity<InvoiceId> createInvoices(@RequestBody InvoiceDetails invoice) {
        loggerController.info("Submitted amount details, amount:"+invoice.getAmount());
        loggerController.info("Submitted amount details, due_date:"+invoice.getDue_date());
        InvoiceId invoiceId = services.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceId);
    }

    @GetMapping("/getAllInvoices")
    public ResponseEntity<List<InvoiceDetails>> getAllInvoices() {
        List<InvoiceDetails> lstInvoices = services.getInvoiceDetails();
        return ResponseEntity.status(HttpStatus.OK).body(lstInvoices);
    }

    @PostMapping("/invoices/{id}/payments")
    public ResponseEntity<Messages> fullPay(@RequestBody InvoiceAmount amount, @PathVariable int id) {
        loggerController.info("Submitted amount details, amount:"+amount.getAmount());
        loggerController.info("Submitted amount details, id:"+id);
        Messages messages = new Messages();
        String message =  services.invoicePayment(Double.valueOf(amount.getAmount()), id);
        messages.setMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @PostMapping("/invoices/{id}/process-overdue")
    public ResponseEntity<Messages> proceeOverdues(@RequestBody InvoiceProcessOverdue overdues, @PathVariable int id) {
        loggerController.info("Submitted amount details, late fee:"+overdues.getLate_fee());
        loggerController.info("Over due days details, Over due days:"+overdues.getOverdue_days());
        loggerController.info("Submitted amount details, id:"+id);
        Messages messages = new Messages();
        String message = services.overDues(Double.valueOf(overdues.getLate_fee()), Integer.valueOf(overdues.getOverdue_days()), Double.valueOf(overdues.getAmount()), id);
        messages.setMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
