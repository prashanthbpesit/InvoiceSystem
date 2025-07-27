package com.payments.invoice.repository;

import com.payments.invoice.dto.InvoiceDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceDTO, Long> {

    @Modifying
    @Transactional // Essential for update operations
    @Query("UPDATE InvoiceDTO invoiceDTO SET invoiceDTO.paidamount = :paidamount, invoiceDTO.status = 'Paid'  WHERE invoiceDTO.id = :id")
    int payFullAmount(@Param("paidamount") double paidamount, @Param("id") Integer id);

    @Modifying
    @Transactional // Essential for update operations
    @Query("UPDATE InvoiceDTO invoiceDTO SET invoiceDTO.paidamount = :paidamount, invoiceDTO.latefee =:latefee , invoiceDTO.overduedays =:duedays ,  invoiceDTO.status = 'Paid'  WHERE invoiceDTO.id = :id")
    int overDuePays(@Param("latefee") double latefee, @Param("duedays") double duedays, @Param("paidamount") double paidamount, @Param("id") Integer id);

    List<InvoiceDTO> findByStatus(String status);
}
