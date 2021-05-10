package com.local.objects;

import java.util.HashMap;

public class AddressStatements {
	public String createAddressTable() {
		String stmt = "CREATE TABLE  IF NOT EXISTS " + Address.getTablename() + " (id INT PRIMARY KEY,"
				+ " street VARCHAR(255) , " + " city VARCHAR(255) NOT NULL, "
				+ "state VARCHAR(255), postal_code VARCHAR(255) NOT NULL, person_id INT NOT NULL,"
				+ " FOREIGN KEY(person_id) REFERENCES person (id) ON UPDATE CASCADE ON DELETE CASCADE)";
		return stmt;
	}

	public String createAddressTableIndex() {
		String stmt = "CREATE INDEX IF NOT EXISTS  idx_address_person_id ON " + Address.getTablename() + " ("
				+ Address.getPersonid() + ")";
		return stmt;
	}

	public String getAddress() {
		String stmt = "SELECT * FROM " + Address.getTablename();
		return stmt;
	}

	public String editAddress(HashMap<String, String> map, String clause, String clauseVal) {
		String stmt = "UPDATE " + Address.getTablename() + " SET ";
		StringBuilder attributes = new StringBuilder();
		map.forEach((key, value) -> {
			attributes.append(key + "='" + value + "',");
		});
		stmt = stmt + attributes.substring(0, attributes.length() - 1).toString() + " WHERE " + clause + " ='"
				+ clauseVal + "'";
		return stmt;
	}

	public String addAddressToPerson(int id, String street, String city, String state, String postalCode,
			int personID) {
		String stmt = "INSERT INTO " + Address.getTablename() + " (" + Address.getId() + "," + Address.getStreet()
				+ ", " + Address.getCity() + ", " + Address.getState() + ", " + Address.getPostalcode() + ", "
				+ Address.getPersonid() + ") VALUES(" + id + ",'" + street + "','" + city + "', '" + state + "','"
				+ postalCode + "'," + personID + ")";
		return stmt;

	}

	public String deleteAddress(int id) {
		String stmt = "DELETE FROM " + Address.getTablename() + " WHERE " + Address.getId() + "=" + id;
		return stmt;

	}

}
