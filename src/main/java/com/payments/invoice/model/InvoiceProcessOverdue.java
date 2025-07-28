package com.payments.invoice.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the model class to access api parameter details.
 */
@Getter
@Setter
public class InvoiceProcessOverdue {
    private String amount;
    private String late_fee;
    private String overdue_days;
}
