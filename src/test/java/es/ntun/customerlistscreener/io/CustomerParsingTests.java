package es.ntun.customerlistscreener.io;

import static es.ntun.customerlistscreener.TestHelper.generateCustomer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import es.ntun.customerlistscreener.customer.Customer;

public class CustomerParsingTests {

	private static final String ID_KEY = "user_id", NAME_KEY = "name", LATITUDE_KEY = "latitude",
	        LONGITUDE_KEY = "longitude";

	private Gson gson = CustomerParser.getCustomerParsingGson();

	@Test
	public void customer_to_json_should_keep_all_fields_correctly() {
		Customer customer = generateCustomer();

		JsonObject json = gson.toJsonTree(customer).getAsJsonObject();

		assertThat(json.get(ID_KEY).getAsInt(), equalTo(customer.getId()));
		assertThat(json.get(NAME_KEY).getAsString(), equalTo(customer.getName()));
		assertThat(json.get(LATITUDE_KEY).getAsDouble(),
		           equalTo(Math.toDegrees(customer.getLatitude())));
		assertThat(json.get(LONGITUDE_KEY).getAsDouble(),
		           equalTo(Math.toDegrees(customer.getLongitude())));
	}

	@Test
	public void json_to_customer_should_parse_all_fields_correctly() {
		Customer expected = generateCustomer();
		JsonObject json = new JsonObject();
		json.addProperty(ID_KEY, expected.getId());
		json.addProperty(NAME_KEY, expected.getName());
		json.addProperty(LATITUDE_KEY, Math.toDegrees(expected.getLatitude()));
		json.addProperty(LONGITUDE_KEY, Math.toDegrees(expected.getLongitude()));

		Customer actual = gson.fromJson(json.toString(), Customer.class);

		assertThat(actual.getId(), equalTo(expected.getId()));
		assertThat(actual.getName(), equalTo(expected.getName()));
		assertThat(actual.getLatitude(), equalTo(expected.getLatitude()));
		assertThat(actual.getLongitude(), equalTo(expected.getLongitude()));
	}

}
