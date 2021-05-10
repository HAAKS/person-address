package com.local.objects;

public class Person {
	final static String id = "id";
	final static String tableName ="person";

	public static String getTablename() {
		return tableName;
	}

	public static String getFname() {
		return fName;
	}

	public static String getLname() {
		return lName;
	}

	public static String getId() {
		return id;
	}

	public static String getfName() {
		return fName;
	}

	public static String getlName() {
		return lName;
	}

	final static String fName = "first_name";
	final static String lName = "last_name";
}
