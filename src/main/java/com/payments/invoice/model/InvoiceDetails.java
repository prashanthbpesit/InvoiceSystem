package com.payments.invoice.model;

import lombok.Getter;
import lombok.Setter;
/**
 * This is the model class to access api parameter details.
 */
@Getter
@Setter
public class InvoiceDetails {
    private String id;
    private String amount;
    private String due_date;
    private String paidamount;
    private String status;
    private String latefee;
    private String overduedays;
}
