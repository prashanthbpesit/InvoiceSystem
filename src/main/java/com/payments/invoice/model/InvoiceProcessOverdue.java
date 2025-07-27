package com.payments.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceProcessOverdue {
    private String amount;
    private String late_fee;
    private String overdue_days;
}
