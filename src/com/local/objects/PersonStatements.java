package com.local.objects;

public class PersonStatements {

	public String createPersonTable() {
		String stmt = "CREATE TABLE IF NOT EXISTS " + Person.getTablename() + " (id INT PRIMARY KEY,"
				+ " first_name VARCHAR(255) NOT NULL, " + " last_name VARCHAR(255) NOT NULL) ";
		return stmt;
	}

	public String createPersonTableIndex() {
		String stmt = "CREATE INDEX IF NOT EXISTS idx_person_first_name ON " + Person.getTablename() + " ("
				+ Person.getfName() + ")";
		return stmt;
	}

	public String emptyTable() {
		String stmt = "DELETE FROM " + Person.getTablename();
		return stmt;
	}

	public String addPerson(int id, String fName, String lName) {
		String stmt = "INSERT INTO " + Person.getTablename() + "(" + Person.getId() + "," + Person.getfName() + ", "
				+ Person.getlName() + ") VALUES(" + id + ",'" + fName + "', '" + lName + "')";
		return stmt;

	}

	public String editPersonFirstName(String fName, String lName) {
		String stmt = "Update " + Person.getTablename() + " SET " + Person.getfName() + " ='" + fName + "' WHERE "
				+ Person.getlName() + " ='" + lName + "'";
		return stmt;

	}

	public String getPerson(int id) {
		String stmt = "SELECT * FROM " + Person.getTablename() + " LEFT JOIN " + Address.getTablename() + " ON "
				+ Person.getTablename() + "." + Person.getId() + "=" + Address.getTablename() + "."
				+ Address.getPersonid() + " where " + Person.getTablename() + "." + Person.getId() + "=" + id;
		return stmt;

	}

	public String editPersonLastName(String fName, String lName) {
		String stmt = "Update " + Person.getTablename() + " SET " + Person.getlName() + " ='" + lName + "' WHERE "
				+ Person.getfName() + " ='" + fName + "'";
		return stmt;

	}

	public String addAddressToPerson(String street, String city, String state, String postalCode, String personID) {
		String stmt = "INSERT INTO " + Address.getTablename() + " (" + Address.getStreet() + ", " + Address.getCity()
				+ ", " + Address.getState() + ", " + Address.getPostalcode() + ", " + Address.getPersonid()
				+ ") VALUES('" + street + "'," + city + "', '" + state + "'," + postalCode + "'," + personID + "')";
		return stmt;

	}

	public String deletePerson(int id) {
		String stmt = "DELETE FROM " + Person.getTablename() + " WHERE " + Person.getId() + "=" + id;
		return stmt;

	}

	public String getCountPerson() {
		String stmt = "SELECT COUNT(*) FROM " + Person.getTablename();
		return stmt;

	}

	public String getList() {
		String stmt = "SELECT * FROM " + Person.getTablename() + " LEFT JOIN " + Address.getTablename() + " ON "
				+ Person.getTablename() + "." + Person.getId() + "=" + Address.getPersonid();
		return stmt;
	}
}
