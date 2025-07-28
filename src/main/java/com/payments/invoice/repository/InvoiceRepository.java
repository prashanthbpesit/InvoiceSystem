package com.payments.invoice.repository;

import com.payments.invoice.dto.InvoiceDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/***
 * This is the JPA Repository class to access the database
 */
public interface InvoiceRepository extends JpaRepository<InvoiceDTO, Long> {

    /**
     * This is the method used to interact with database
     *
     * @param paidamount : To update Paid amount
     * @param id         : To update particular invoice id
     * @return
     */
    @Modifying
    @Transactional // Essential for update operations
    @Query("UPDATE InvoiceDTO invoiceDTO SET invoiceDTO.paidamount = :paidamount, invoiceDTO.status = 'Paid'  WHERE invoiceDTO.id = :id")
    int payFullAmount(@Param("paidamount") double paidamount, @Param("id") Integer id);

    /**
     * This method to update overdue details to database
     *
     * @param latefee    : To update late fee details
     * @param duedays    : to update overdue numbers
     * @param paidamount : To update Paid amount
     * @param id         : To update particular invoice id
     * @return
     */
    @Modifying
    @Transactional // Essential for update operations
    @Query("UPDATE InvoiceDTO invoiceDTO SET invoiceDTO.paidamount = :paidamount, invoiceDTO.latefee =:latefee , invoiceDTO.overduedays =:duedays ,  invoiceDTO.status = 'Paid'  WHERE invoiceDTO.id = :id")
    int overDuePays(@Param("latefee") double latefee, @Param("duedays") double duedays, @Param("paidamount") double paidamount, @Param("id") Integer id);

}
