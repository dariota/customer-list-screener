package es.ntun.customerlistscreener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import es.ntun.customerlistscreener.customer.Customer;
import es.ntun.customerlistscreener.customer.EarthPoint;
import es.ntun.customerlistscreener.io.CustomerReader;

public class Main {

	private static final double MAXIMUM_DISTANCE_METRES = 100E3;
	private static final EarthPoint OFFICE_LOCATION = new EarthPoint(53.339428, -6.257664);

	public static void main(String[] args) {
		String filename = "customers.json";
		if (args.length > 0) {
			filename = args[0];
		}

		File customerFile = new File(filename);

		try {
			ArrayList<Customer> eligibleCustomers = CustomerFilter.findCustomersWithinOf(CustomerReader.readCustomersFromFile(customerFile),
			                                                                             MAXIMUM_DISTANCE_METRES,
			                                                                             OFFICE_LOCATION);
			Collections.sort(eligibleCustomers);

			for (Customer c : eligibleCustomers) {
				System.out.printf("%-10d %s%n", c.getId(), c.getName());
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not find " + filename);
			System.err.println("Please provide the customer list as customers.json in the current directory, or as the first argument");
			System.exit(1);
		}
	}
}
