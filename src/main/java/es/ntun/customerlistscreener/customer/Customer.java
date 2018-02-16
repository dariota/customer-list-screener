package es.ntun.customerlistscreener.customer;

import es.ntun.customerlistscreener.EarthPoint;

public class Customer {

	private int id;
	private String name;
	private EarthPoint location;

	public Customer(int id, String name, EarthPoint location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getLatitude() {
		return location.getLatitude();
	}

	public double getLongitude() {
		return location.getLongitude();
	}
}
