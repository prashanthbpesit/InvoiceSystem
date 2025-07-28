package com.payments.invoice.service;

import com.payments.invoice.dto.InvoiceDTO;
import com.payments.invoice.model.InvoiceDetails;
import com.payments.invoice.model.InvoiceId;
import com.payments.invoice.repository.InvoiceRepository;
import com.payments.invoice.util.CommonMethods;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***
 * This is used to access service classes from the api call.
 */
@Service
@Slf4j
public class InvoiceServices {

    private static final Logger loggerInvoiceServices = LoggerFactory.getLogger(InvoiceServices.class);

    @Autowired
    private InvoiceRepository repo;

    /**
     * This service method to create invoice details
     *
     * @param invoice : This object to update invoice amount and due date
     * @return : this will return the invoice id details
     */
    public InvoiceId createInvoice(InvoiceDetails invoice) {
        InvoiceId invoiceId = new InvoiceId();
        try {
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setInvoiceamount(Double.valueOf(invoice.getAmount()));
            LocalDate date = CommonMethods.converStringToDate(invoice.getDue_date());
            invoiceDTO.setDuedate(date);
            invoiceDTO.setStatus("Pending");
            invoiceDTO.setLatefee(0.0);
            invoiceDTO.setOverduedays(0);
            invoiceDTO.setPaidamount(0.0);
            InvoiceDTO savedInvoiceDTO = repo.save(invoiceDTO);
            invoiceId.setId(String.valueOf(savedInvoiceDTO.getId()));
        } catch (Exception Ex) {
            loggerInvoiceServices.error("Error in createInvoice: " + Ex.getMessage());
        }
        return invoiceId;
    }

    /**
     * This service method to get all the invoice details.
     *
     * @return : Method will return the list of objects
     */
    public List<InvoiceDetails> getInvoiceDetails() {
        List<InvoiceDetails> lstInvoiceDetails = new ArrayList<>();
        try {
            List<InvoiceDTO> lstInvoiceDTO = repo.findAll();
            lstInvoiceDetails = lstInvoiceDTO.stream().map(dto -> {
                InvoiceDetails entity = new InvoiceDetails();
                entity.setId(dto.getId().toString());
                entity.setAmount(String.valueOf(dto.getInvoiceamount()));
                entity.setPaidamount(String.valueOf(dto.getPaidamount()));
                entity.setDue_date(String.valueOf(dto.getDuedate()));
                entity.setStatus(dto.getStatus());
                entity.setOverduedays(String.valueOf(dto.getOverduedays()));
                entity.setLatefee(String.valueOf(dto.getLatefee()));
                return entity;
            }).collect(Collectors.toList());
        } catch (Exception Ex) {
            loggerInvoiceServices.error("Error in getInvoiceDetails: " + Ex.getMessage());
        }
        return lstInvoiceDetails;
    }

    /**
     * This service method is used to process invoice payment
     *
     * @param amount : the double variable of invoice amount
     * @param id     : the integer variable of id
     * @return : The method will return the string variables containing succcess or failure cases.
     */
    public String invoicePayment(double amount, int id) {
        String message = "Successfully Paid.";
        try {
            repo.payFullAmount(amount, id);
        } catch (Exception Ex) {
            message = "Couldnt pay it. Please try again.";
            loggerInvoiceServices.error("Error in invoicePayment: " + Ex.getMessage());
        }
        return message;
    }

    /**
     * This service method is used to process over due calcultion of invoice payment
     *
     * @param latefee : the double variable of late fee amount
     * @param duedays : the integer variable of due days calculations
     * @param amount  : the double variable of amount
     * @param id      : the integer variable of id
     * @return : The method will return the string variables containing succcess or failure cases.
     */
    public String overDues(double latefee, int duedays, double amount, int id) {
        String message = "Successfully Paid for the Overdues.";
        try {
            repo.overDuePays(latefee, duedays, amount, id);
        } catch (Exception Ex) {
            message = "Couldnt pay it. Please try again.";
            loggerInvoiceServices.error("Error in invoicePayment: " + Ex.getMessage());
        }
        return message;
    }

}
