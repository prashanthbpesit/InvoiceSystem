package com.payments.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class to access the springboot applications
 */
@SpringBootApplication
public class InvoiceApplication {

	/**
	 * Main method to get the entry to applications
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

}
