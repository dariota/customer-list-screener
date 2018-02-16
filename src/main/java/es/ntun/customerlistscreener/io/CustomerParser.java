package es.ntun.customerlistscreener.io;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import es.ntun.customerlistscreener.EarthPoint;
import es.ntun.customerlistscreener.customer.Customer;

public class CustomerParser implements JsonDeserializer<Customer>, JsonSerializer<Customer> {

	private static final String ID_KEY = "user_id", NAME_KEY = "name", LATITUDE_KEY = "latitude",
	        LONGITUDE_KEY = "longitude";

	@Override
	public Customer deserialize(JsonElement json, Type type,
	                            JsonDeserializationContext context) throws JsonParseException {
		return new Customer(0, "", 0, 0);
	}

	@Override
	public JsonElement serialize(Customer customer, Type type, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		
		json.addProperty(ID_KEY, 0);
		json.addProperty(NAME_KEY, "");
		json.addProperty(LATITUDE_KEY, 0);
		json.addProperty(LONGITUDE_KEY, 0);

		return json;
	}

}
