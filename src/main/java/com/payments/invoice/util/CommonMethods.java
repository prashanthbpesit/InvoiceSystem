package com.payments.invoice.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/***
 * This class is used to access common methods across the applications.
 * This class method will contain only static variables and methods.
 */
@Slf4j
public class CommonMethods {
    /***
     * This is the util method to convert the string to Date
     * @param inDateStr : This is the String variable of date
     * @return : This method will return local date
     */
    public static LocalDate converStringToDate(String inDateStr) {
        LocalDate date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(inDateStr, formatter);
            log.info("input date conversion,  date:" + date);
        } catch (Exception Ex) {
            log.info("Exception in converStringToDate,  Error message:" + Ex.getMessage());
        }
        return date;
    }
}
