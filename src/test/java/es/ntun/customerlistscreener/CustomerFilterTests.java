package es.ntun.customerlistscreener;

import static es.ntun.customerlistscreener.TestHelper.generateCustomer;
import static es.ntun.customerlistscreener.TestHelper.generateLocation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIn.in;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.junit.Test;

import es.ntun.customerlistscreener.customer.Customer;
import es.ntun.customerlistscreener.customer.EarthPoint;

public class CustomerFilterTests {

	@Test
	public void filter_should_return_customers_if_and_only_if_they_are_within_distance() {
		ArrayList<Customer> allCustomers = generateCustomerList();
		EarthPoint randomLocation = generateLocation();
		// ensure we get at least one back
		double distance = randomLocation.distanceTo(allCustomers.get(0).getLocation()) + 1;

		ArrayList<Customer> filteredCustomers = CustomerFilter.findCustomersWithinOf(allCustomers,
		                                                                             distance,
		                                                                             randomLocation);

		// check our special case
		assertThat(allCustomers.get(0), in(filteredCustomers));

		for (Customer c : allCustomers) {
			double customerDistance = randomLocation.distanceTo(c.getLocation());
			Matcher<Double> shouldBe;
			if (filteredCustomers.contains(c)) {
				shouldBe = lessThanOrEqualTo(distance);
			} else {
				shouldBe = greaterThan(distance);
			}
			assertThat(customerDistance, shouldBe);
		}
	}

	@Test
	public void filter_should_return_empty_if_given_negative_distance() {
		ArrayList<Customer> allCustomers = generateCustomerList();
		EarthPoint location = allCustomers.get(0).getLocation();

		assertThat(CustomerFilter.findCustomersWithinOf(allCustomers, -0.01, location), empty());
	}

	@Test
	public void filter_should_return_empty_if_given_null() {
		ArrayList<Customer> allCustomers = generateCustomerList();
		EarthPoint location = allCustomers.get(0).getLocation();

		assertThat(CustomerFilter.findCustomersWithinOf(null, Double.MAX_VALUE, location), empty());
		assertThat(CustomerFilter.findCustomersWithinOf(allCustomers, Double.MAX_VALUE, null),
		           empty());
	}

	private static ArrayList<Customer> generateCustomerList() {
		int customerCount = 100;

		ArrayList<Customer> allCustomers = new ArrayList<>(customerCount);
		for (int i = 0; i < customerCount; i++) {
			allCustomers.add(generateCustomer());
		}

		return allCustomers;
	}

}
