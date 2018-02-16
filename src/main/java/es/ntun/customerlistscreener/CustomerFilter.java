package es.ntun.customerlistscreener;

import java.util.ArrayList;

import es.ntun.customerlistscreener.customer.Customer;
import es.ntun.customerlistscreener.customer.EarthPoint;

public class CustomerFilter {

	// could be a stream, but that's not great for efficiency
	public static ArrayList<Customer> findCustomersWithinOf(Iterable<Customer> customers,
	                                                        double distanceMetres,
	                                                        EarthPoint location) {
		ArrayList<Customer> filtered = new ArrayList<>();

		if (customers == null || location == null) {
			return filtered;
		}

		for (Customer c : customers) {
			if (location.distanceTo(c.getLocation()) <= distanceMetres) {
				filtered.add(c);
			}
		}

		return filtered;
	}
}
