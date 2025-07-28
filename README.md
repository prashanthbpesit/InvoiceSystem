# InvoiceSystem
This is a simple invoice system that allows creating invoices, paying invoices, and processing overdue invoices.

#Scripts to create table details,

CREATE TABLE `dbinvoice`.`tblpayments` (
  `fldid` INT NOT NULL AUTO_INCREMENT DEFAULT 1234 COMMENT '',
  `fldamount` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `fldduedate` DATETIME NULL COMMENT '',
  `fldstatus` VARCHAR(45) NULL COMMENT '',
  `fldpaidamount` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `fldlatefee` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `fldoverduedays` INT NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`fldid`)  COMMENT '');
