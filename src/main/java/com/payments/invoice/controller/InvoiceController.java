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

import java.util.ArrayList;
import java.util.List;

/**
 * This Restcontroller class is used to communicate api details with services.
 * This class has all the POST and GET call for payment invoice api
 */
@RestController
@Slf4j
public class InvoiceController {
    private static final Logger loggerController = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceServices services;

    /**
     * This is the test api to verify api call
     * @return - Hard coded message for testing
     */
    @GetMapping("/testAPI")
    public String testAPIDetails() {
        try{
            loggerController.info("Inside Test API Call");
            return "Test API to check the application status!";
        } catch (Exception Ex) {
            loggerController.error("Error in testAPIDetails, message : "+Ex.getMessage());
            return "There is an issue with Test API, please verify it.";
        }
    }

    /**
     * This api call to add payment details on the invoice
     * @param invoice : Invoice object get Amount and due date details
     * @return : This method will return Invoice Id details.
     */
    @PostMapping("/invoices")
    public ResponseEntity<InvoiceId> createInvoices(@RequestBody InvoiceDetails invoice) {
      try {
          loggerController.info("Submitted amount details, amount:" + invoice.getAmount());
          loggerController.info("Submitted amount details, due_date:" + invoice.getDue_date());
          InvoiceId invoiceId = services.createInvoice(invoice);
          return ResponseEntity.status(HttpStatus.CREATED).body(invoiceId);
      } catch (Exception Ex) {
          InvoiceId invoiceId = new InvoiceId();
          invoiceId.setId("-");
          loggerController.error("Error in createInvoices, message : "+Ex.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(invoiceId);
      }
    }

    /***
     * This api is used to verify all the invoices.
     * @return : This method will return list of Invoice details.
     */
    @GetMapping("/getAllInvoices")
    public ResponseEntity<List<InvoiceDetails>> getAllInvoices() {
        try {
            List<InvoiceDetails> lstInvoices = services.getInvoiceDetails();
            return ResponseEntity.status(HttpStatus.OK).body(lstInvoices);
        } catch (Exception Ex) {
            List<InvoiceDetails> lstInvoices = new ArrayList<>() ;
            loggerController.error("Error in getAllInvoices, message : "+Ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lstInvoices);
        }
    }

    /***
     * This api details to verify full pay
     * @param amount : The Invoice Amount object for updating on the database.
     * @param id : This is the Invoice id to update against the amount
     * @return : The method will return the success or failure messages
     */
    @PostMapping("/invoices/{id}/payments")
    public ResponseEntity<Messages> fullPay(@RequestBody InvoiceAmount amount, @PathVariable int id) {
        try {
            loggerController.info("Submitted amount details, amount:" + amount.getAmount());
            loggerController.info("Submitted amount details, id:" + id);
            Messages messages = new Messages();
            String message = services.invoicePayment(Double.valueOf(amount.getAmount()), id);
            messages.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        } catch (Exception Ex) {
            Messages messages = new Messages();
            loggerController.error("Error in getAllInvoices, message : "+Ex.getMessage());
            messages.setMessage("There is an issue, please try after sometime.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messages);
        }
    }

    /***
     * This api details to process overdues.
     * @param overdues : This object to get the over due details.
     * @param id  : This is the Invoice id to update against the amount
     * @return : The method will return the success or failure messages
     */
    @PostMapping("/invoices/{id}/process-overdue")
    public ResponseEntity<Messages> proceeOverdues(@RequestBody InvoiceProcessOverdue overdues, @PathVariable int id) {
        try {
        loggerController.info("Submitted amount details, late fee:"+overdues.getLate_fee());
        loggerController.info("Over due days details, Over due days:"+overdues.getOverdue_days());
        loggerController.info("Submitted amount details, id:"+id);
        Messages messages = new Messages();
        String message = services.overDues(Double.valueOf(overdues.getLate_fee()), Integer.valueOf(overdues.getOverdue_days()), Double.valueOf(overdues.getAmount()), id);
        messages.setMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
        } catch (Exception Ex) {
            Messages messages = new Messages();
            loggerController.error("Error in getAllInvoices, message : "+Ex.getMessage());
            messages.setMessage("There is an issue, please try after sometime.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messages);
        }
    }
}
