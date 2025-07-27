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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository repo;

    @InjectMocks
    private InvoiceServices service;

    public InvoiceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateInvoice() {
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
}
