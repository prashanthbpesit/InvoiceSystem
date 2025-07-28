package com.payments.invoice.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/***
 * This is the DTO class to access database table.
 */
@Entity
@Table(name = "tblpayments")
@Getter
@Setter
public class InvoiceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldamount")
    private double invoiceamount;

    @Column(name = "fldduedate")
    private LocalDate duedate;

    @Column(name = "fldstatus") // Maps 'name' field to 'product_name' column
    private String status;

    @Column(name = "fldpaidamount")
    private double paidamount;

    @Column(name = "fldlatefee")
    private double latefee;

    @Column(name = "fldoverduedays")
    private int overduedays;

}
