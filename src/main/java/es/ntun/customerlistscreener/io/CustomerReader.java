package es.ntun.customerlistscreener.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import com.google.gson.Gson;

import es.ntun.customerlistscreener.customer.Customer;

public class CustomerReader {

	// Avoid slurping so we can handle huge files
	public static Iterable<Customer> readCustomersFromFile(File file) throws FileNotFoundException {
		Gson gson = CustomerParser.getCustomerParsingGson();
		Scanner s = new Scanner(file);

		return () -> new Iterator<Customer>() {

			@Override
			public boolean hasNext() {
				if (s.hasNextLine()) {
					return true;
				} else {
					s.close();
					return false;
				}
			}

			@Override
			public Customer next() {
				String customerJson = s.nextLine();
				return gson.fromJson(customerJson, Customer.class);
			}
		};
	}

}
