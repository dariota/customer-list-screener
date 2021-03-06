package es.ntun.customerlistscreener.io;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import es.ntun.customerlistscreener.customer.Customer;
import es.ntun.customerlistscreener.customer.EarthPoint;

public class CustomerParser implements JsonDeserializer<Customer>, JsonSerializer<Customer> {

	private static final String ID_KEY = "user_id", NAME_KEY = "name", LATITUDE_KEY = "latitude",
	        LONGITUDE_KEY = "longitude";

	@Override
	public Customer deserialize(JsonElement json, Type type,
	                            JsonDeserializationContext context) throws JsonParseException {
		JsonObject customer = json.getAsJsonObject();

		return new Customer(getKeyAggressive(customer, ID_KEY).getAsInt(),
		                    getKeyAggressive(customer, NAME_KEY).getAsString(),
		                    new EarthPoint(getKeyAggressive(customer, LATITUDE_KEY).getAsDouble(),
		                                   getKeyAggressive(customer,
		                                                    LONGITUDE_KEY).getAsDouble()));
	}

	@Override
	public JsonElement serialize(Customer customer, Type type, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty(ID_KEY, customer.getId());
		json.addProperty(NAME_KEY, customer.getName());
		json.addProperty(LATITUDE_KEY, Math.toDegrees(customer.getLatitude()));
		json.addProperty(LONGITUDE_KEY, Math.toDegrees(customer.getLongitude()));

		return json;
	}

	private static JsonElement getKeyAggressive(JsonObject object,
	                                            String key) throws JsonParseException {
		JsonElement element = object.get(key);

		if (element != null) {
			return element;
		} else {
			throw new JsonParseException("Needed " + key + " but was not present");
		}
	}

	public static Gson getCustomerParsingGson() {
		return new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerParser()).create();
	}

}
