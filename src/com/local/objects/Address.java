package com.local.objects;

import java.util.HashMap;

public class Address {
	static final String id = "id";
	static final String street = "street";
	static final String city = "city";
	static final String state = "state";
	static final String postalCode = "postal_code";
	static final String personID = "person_id";
	static final String tableName = "address";
	static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> getMap() {
		return map;
	}

	public Address() {
		map.put("city", city);
		map.put("id", id);
		map.put("street", street);
		map.put("state", state);
		map.put("postalCode", postalCode);
		map.put("personID", personID);

	}

	public static String getTablename() {
		return tableName;
	}

	public static String getId() {
		return id;
	}

	public static String getStreet() {
		return street;
	}

	public static String getCity() {
		return city;
	}

	public static String getState() {
		return state;
	}

	public static String getPostalcode() {
		return postalCode;
	}

	public static String getPersonid() {
		return personID;
	}

}
