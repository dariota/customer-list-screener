package es.ntun.customerlistscreener.io;

import static es.ntun.customerlistscreener.TestHelper.generateCustomer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIn.in;
import static org.hamcrest.number.OrderingComparison.lessThan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.junit.After;
import org.junit.Test;

import com.google.gson.Gson;

import es.ntun.customerlistscreener.customer.Customer;

public class CustomerReaderTests {

	private static class CustomerFileInfo {
		public File customerFile;
		public HashSet<Customer> customers;

		public CustomerFileInfo(File customerFile, HashSet<Customer> customers) {
			this.customerFile = customerFile;
			this.customers = customers;
		}
	}

	// allow a few megabytes of variance since GC is unpredictable
	private static final int BYTE_DIFFERENCE_ALLOWED = 5 * 1024 * 1024;

	File customerFile;

	@After
	// ensure we don't leave useless large files sitting around
	public void tearDown() {
		if (customerFile != null && customerFile.exists()) {
			customerFile.delete();
			customerFile = null;
		}
	}

	@Test
	public void consuming_large_file_should_not_consume_much_memory() throws IOException {
		int expectedCustomerCount = 500000;
		// works out to ~52MB
		customerFile = generateCustomerFile(expectedCustomerCount);

		long usedMemory = getApproximateMemoryUsage();

		Iterable<Customer> customers = CustomerReader.readCustomersFromFile(customerFile);

		assertThat(getApproximateMemoryUsage(), lessThan(usedMemory + BYTE_DIFFERENCE_ALLOWED));

		// give this something to do and make sure everything gets read in
		int actualCustomerCount = 0;
		for (Customer c : customers) {
			actualCustomerCount++;
		}

		assertThat(actualCustomerCount, equalTo(expectedCustomerCount));
		assertThat(getApproximateMemoryUsage(), lessThan(usedMemory + BYTE_DIFFERENCE_ALLOWED));
	}

	@Test
	public void reading_file_should_give_back_all_customers_identically() throws IOException {
		CustomerFileInfo customerInfo = generateCustomersAndFile(100);
		customerFile = customerInfo.customerFile;

		HashSet<Customer> customerCopy = new HashSet<>(customerInfo.customers);
		for (Customer c : CustomerReader.readCustomersFromFile(customerFile)) {
			assertThat(c, in(customerInfo.customers));
			customerCopy.remove(c);
		}

		assertThat(customerCopy, empty());
	}

	private static long getApproximateMemoryUsage() {
		return Runtime.getRuntime().totalMemory();
	}

	private static CustomerFileInfo generateCustomersAndFile(int customerCount) throws IOException {
		File customerFile = File.createTempFile("customers", ".json");
		FileWriter writer = new FileWriter(customerFile);
		HashSet<Customer> expectedCustomers = new HashSet<>(customerCount);
		Gson gson = CustomerParser.getCustomerParsingGson();

		for (int i = 0; i < customerCount; i++) {
			Customer c = generateCustomer();
			writer.write(gson.toJson(c));
			writer.write('\n');
			expectedCustomers.add(c);
		}

		writer.flush();
		writer.close();
		return new CustomerFileInfo(customerFile, expectedCustomers);
	}

	private static File generateCustomerFile(int customerCount) throws IOException {
		return generateCustomersAndFile(customerCount).customerFile;
	}
}
