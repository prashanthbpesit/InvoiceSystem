package com.payments.invoice.service;

import com.payments.invoice.controller.InvoiceController;
import com.payments.invoice.dto.InvoiceDTO;
import com.payments.invoice.model.InvoiceAmount;
import com.payments.invoice.model.InvoiceDetails;
import com.payments.invoice.model.InvoiceId;
import com.payments.invoice.repository.InvoiceRepository;
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

@Service
@Slf4j
public class InvoiceServices {

    private static final Logger loggerInvoiceServices = LoggerFactory.getLogger(InvoiceServices.class);

    @Autowired
    private InvoiceRepository repo;

    public InvoiceId createInvoice(InvoiceDetails invoice) {
        InvoiceId invoiceId = new InvoiceId();
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceamount(Double.valueOf(invoice.getAmount()));
        String dateStr = invoice.getDue_date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(dateStr, formatter);
        loggerInvoiceServices.info("input date conversion,  date:"+date);  // Output: 2025-07-27

        invoiceDTO.setDuedate(date);
        invoiceDTO.setStatus("Pending");
        invoiceDTO.setLatefee(0.0);
        invoiceDTO.setOverduedays(0);
        invoiceDTO.setPaidamount(0.0);
        InvoiceDTO savedInvoiceDTO = repo.save(invoiceDTO);
        invoiceId.setId(String.valueOf(savedInvoiceDTO.getId()));
        return invoiceId;
    }

    public List<InvoiceDetails> getInvoiceDetails(){
        List<InvoiceDetails> lstInvoiceDetails = new ArrayList<>();
        try{
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
        }catch (Exception Ex){
            loggerInvoiceServices.error("Error in getInvoiceDetails: "+Ex.getMessage());
        }
        return lstInvoiceDetails;
    }

    public String invoicePayment(double amount, int id){
        String message = "Successfully Paid.";
        try{
            repo.payFullAmount(amount, id);
        } catch (Exception Ex) {
            message = "Couldnt pay it. Please try again.";
            loggerInvoiceServices.error("Error in invoicePayment: "+Ex.getMessage());
        }
        return message;
    }

    public String overDues(double latefee, int duedays, double amount,  int id){
        String message = "Successfully Paid for the Overdues.";
        try{
            repo.overDuePays(latefee, duedays, amount, id);
        } catch (Exception Ex) {
            message = "Couldnt pay it. Please try again.";
            loggerInvoiceServices.error("Error in invoicePayment: "+Ex.getMessage());
        }
        return message;
    }

}
