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

#We can test the api details from Postman
#Create invoice request and response details,
API details: http://localhost:8080/invoices
#request, as a Post API call with request body
{
    "amount": 199.99,
    "due_date": "2021-09-11"
}
#response with 201 response code.
{
    "id": "1237"
}
#Get invoice info details api, 
API details: http://localhost:8080/getAllInvoices
#request as GET API call and response as below,
[
    {
        "id": "1233",
        "amount": "199.91",
        "due_date": "2021-09-11",
        "paidamount": "0.0",
        "status": "Pending",
        "latefee": "0.0",
        "overduedays": "0"
    }     
]

#For Full payments api,
API details: http://localhost:8080/invoices/1237/payments
#request, as a Post API call with request body as below.
{
"amount": 159.99
}
#response with 200 response code.
{
    "message": "Successfully Paid."
}
#For invoice overdues,
API details: http://localhost:8080/invoices/1237/process-overdue
#request, as a Post API call with request body as below.
{
 "amount": 159.99,
"late_fee": 10.5,
"overdue_days": 10
}
#response with 200 response code.
{
    "message": "Successfully Paid for the Overdues."
}
