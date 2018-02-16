package es.ntun.customerlistscreener.customer;

import static es.ntun.customerlistscreener.TestHelper.generateCustomerList;
import static es.ntun.customerlistscreener.TestHelper.generateLocation;
import static es.ntun.customerlistscreener.TestHelper.throwsA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.hamcrest.comparator.ComparatorMatcherBuilder;
import org.junit.Test;

public class CustomerTests {

	@Test
	public void constructor_should_throw_illegal_argument_exception_if_given_null_location_or_name() {
		assertThat(() -> new Customer(0, null, generateLocation()),
		           throwsA(IllegalArgumentException.class));
		assertThat(() -> new Customer(0, "", null), throwsA(IllegalArgumentException.class));
		assertThat(() -> new Customer(0, null, null), throwsA(IllegalArgumentException.class));
	}

	@Test
	public void sorting_customer_should_order_by_id_then_name() {
		int customerCount = 1000;
		Customer[] customers = generateCustomerList(customerCount).toArray(new Customer[customerCount]);

		// ensure we have matching IDs/names
		Customer toDuplicate = customers[0];
		customers[1] = new Customer(toDuplicate.getId(),
		                            toDuplicate.getName()
		                                       .substring(0, toDuplicate.getName().length() - 2),
		                            toDuplicate.getLocation());
		customers[2] = toDuplicate;

		Arrays.sort(customers);

		for (int i = 0; i < customers.length - 1; i++) {
			int currentId = customers[i].getId();
			int nextId = customers[i + 1].getId();

			if (currentId > nextId) {
				fail("Customer with higher ID came first");
			} else if (currentId == nextId) {
				assertThat(customers[i].getName(),
				           ComparatorMatcherBuilder.<String>usingNaturalOrdering()
				                                   .lessThanOrEqualTo(customers[i + 1].getName()));
			}
		}
	}
}
