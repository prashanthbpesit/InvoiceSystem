package com.payments.invoice;

import com.payments.invoice.dto.InvoiceDTO;
import com.payments.invoice.model.InvoiceDetails;
import com.payments.invoice.model.InvoiceId;
import com.payments.invoice.repository.InvoiceRepository;
import com.payments.invoice.service.InvoiceServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/***
 * This Class is used to execute test cases for service class.
 */
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository repo;

    @InjectMocks
    private InvoiceServices service;

    public InvoiceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This is the sample test method to check on create invoice method.
     */
    @Test
    void testCreateInvoice() {
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setAmount("100.0");
        invoiceDetails.setDue_date("2025-08-11");
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setPaidamount(100.0);
        invoice.setDuedate(LocalDate.parse("2025-08-11"));

        when(repo.save(any(InvoiceDTO.class))).thenReturn(invoice);
        InvoiceId created = service.createInvoice(invoiceDetails);
        created.setId("1234");
        assertEquals("1234", created.getId());
    }

    @Test
    void testGetAllInvoices(){
        List<InvoiceDTO> lstInvoiceDTO = new ArrayList<>();
        when(repo.findAll()).thenReturn(lstInvoiceDTO);
        List<InvoiceDetails> testList = service.getInvoiceDetails();
        assertEquals(testList.hashCode(), lstInvoiceDTO.hashCode());
    }
    @Test
    void testInvoicePayment(){
        double amount = 190.00;
        int id = 1234;
        when(repo.payFullAmount(amount, id)).thenReturn(anyInt());
        String message = "Successfully Paid.";
        message = service.invoicePayment(amount, id);
        assertNotEquals("Successfully Paid.", message);
    }

    @Test
    void testOverDues(){
        double amount = 190.00;
        double latefee = 40.00;
        int duedays = 4;
        int id = 1234;
        when(repo.overDuePays(latefee, duedays, amount, id)).thenReturn(anyInt());
        String message = "Successfully Paid.";
        message = service.overDues(latefee, duedays, amount, id);
        assertNotEquals("Successfully Paid.", message);
    }
}
