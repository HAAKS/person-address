package com.local.main;

import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;

import com.local.database.DatabaseService;
import com.local.objects.Address;
import com.local.objects.AddressStatements;
import com.local.objects.PersonStatements;

public class MainClass {

	public static void main(String[] args) {
		PersonStatements personStmt = new PersonStatements();
		AddressStatements addrStmt = new AddressStatements();
		Address addr = new Address();
		DatabaseService dbService = new DatabaseService();
		createTables(personStmt, addrStmt, dbService);
		String stmt = "Choose from the following options: \n"
				+ "-Type 'Add Person' to add person or 'Add Address' to add address to person \n"
				+ "-Type 'Edit Person' to edit person or 'Edit Address' to edit address \n"
				+ "-Type 'Delete Person' to delete person or 'Delete Address' to delete address \n"
				+ "-Type 'Count' to display Person's count  \n" + " Type 'List' to list person \n"
				+ "-Type 'quit' to quit";
		System.out.println(stmt);
		Scanner scanner = new Scanner(System.in);
		String line = "";
		while (!(line = scanner.nextLine()).equalsIgnoreCase("quit")) {
			if (line.equalsIgnoreCase("add person"))
				addPerson(personStmt, dbService, scanner);
			if (line.equalsIgnoreCase("add address"))
				addAddress(addr, addrStmt, dbService, scanner);
			if (line.equalsIgnoreCase("edit person"))
				editPerson(personStmt, dbService, scanner);
			if (line.equalsIgnoreCase("edit address"))
				editAddress(addr, addrStmt, dbService, scanner);
			if (line.equalsIgnoreCase("delete person"))
				deletePerson(personStmt, dbService, scanner);
			if (line.equalsIgnoreCase("delete address"))
				deleteAddress(addrStmt, dbService, scanner);
			if (line.equalsIgnoreCase("list"))
				listPerson(personStmt, dbService);
			if (line.equalsIgnoreCase("count"))
				countPerson(personStmt, dbService);
			System.out.println(stmt);
		}
		System.out.println("Bye");

	}

	static void countPerson(PersonStatements personStmt, DatabaseService dbService) {
		String result = "";
		result = dbService.getObjects(personStmt.getCountPerson()).getJSONObject(0).getString("COUNT(*)");
		System.out.println("The count of person(s) is: " + result);
	}

	static void listPerson(PersonStatements personStmt, DatabaseService dbService) {
		JSONArray array = dbService.getObjects(personStmt.getList());
		array.forEach(i -> {
			System.out.println(i);
		});
	}

	static void deleteAddress(AddressStatements addrStmt, DatabaseService dbService, Scanner scanner) {
		System.out.println("Type id of the address to be deleted");
		String id = null;
		String result = "";
		int idVal;
		while (!result.equalsIgnoreCase("success")) {
			id = scanner.nextLine();
			try {
				idVal = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				continue;
			}
			result = dbService.executeQuery(addrStmt.deleteAddress(idVal));
		}
	}

	static void deletePerson(PersonStatements personStmt, DatabaseService dbService, Scanner scanner) {
		System.out.println("Type id of the person to be deleted");
		String id = null;
		String result = "";
		int idVal;
		while (!result.equalsIgnoreCase("success")) {
			id = scanner.nextLine();
			try {
				idVal = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				continue;
			}
			result = dbService.executeQuery(personStmt.deletePerson(idVal));
		}
	}

	static void editAddress(Address addr, AddressStatements addrStmt, DatabaseService dbService, Scanner scanner) {
		String str = "";
		String result = "";
		String clause = null;
		String clauseVal = null;
		HashMap<String, String> map = new HashMap<String, String>();
		while (!result.equalsIgnoreCase("success")) {
			System.out.println("Street to edit");
			str = scanner.nextLine();
			if (!(str.equals(""))) {
				map.put(Address.getStreet(), str);
			}
			System.out.println("City to edit");
			str = scanner.nextLine();
			if (!(str.equals(""))) {
				map.put(Address.getCity(), str);
			}
			System.out.println("State to edit");
			str = scanner.nextLine();
			if (!(str.equals(""))) {
				map.put(Address.getState(), str);
			}
			System.out.println("PostalCode to edit");
			str = scanner.nextLine();
			if (!(str.equals(""))) {
				map.put(Address.getPostalcode(), str);
			}
			System.out.println("Clause on which the edit will occur: city, street,state,postalCode");
			str = scanner.nextLine();
			if (!(str.equals(""))) {
				clause = addr.getMap().get(str.toLowerCase());
				if (clause.equals("") || clause == null) {
					System.out.println("Choose a correct attribute name on which the edit will occur");
					continue;
				}
			}
			System.out.println("Value of the clause to edit");
			str = scanner.nextLine();
			if (!(str.length() == 0 || str.isEmpty())) {
				clauseVal = str;
			}
			result = dbService.executeQuery((addrStmt.editAddress(map, clause, clauseVal)));
		}
	}

	static void editPerson(PersonStatements personStmt, DatabaseService dbService, Scanner scanner) {
		String str = "";
		String result = "";
		String fName = null;
		String lName = null;
		String stmt = null;
		while (!result.equalsIgnoreCase("success")) {
			System.out.println("Type 'first' or 'last' to change the appropriate attribute");
			str = scanner.nextLine();
			if (str.equalsIgnoreCase("first")) {
				System.out.println("Type the value of first name to be changed");
				fName = scanner.nextLine();
				System.out.println("Type last name whose value will be changed upen");
				lName = scanner.nextLine();
				stmt = personStmt.editPersonFirstName(fName, lName);

			} else {
				if (str.equalsIgnoreCase("last")) {
					System.out.println("Type the value of last name to be changed");
					fName = scanner.nextLine();
					System.out.println("Type first name whose value will be changed upen");
					lName = scanner.nextLine();
					stmt = personStmt.editPersonLastName(fName, lName);

				} else {
					System.out.println("Type the correct input");
					continue;
				}
			}

			result = dbService.executeQuery(stmt);
		}
	}

	static void addAddress(Address addr, AddressStatements addrStmt, DatabaseService dbService, Scanner scanner) {
		int id;
		String street;
		String city;
		String state;
		String postalCode;
		int personID;
		String str;
		String result = "";
		while (!result.equalsIgnoreCase("success")) {
			System.out.println("Add new address's id");
			str = scanner.nextLine();
			try {
				id = Integer.parseInt(str);
				System.out.println("Add person's id");
				str = scanner.nextLine();
				personID = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println("Enter a correct number");
				continue;
			}
			System.out.println("Add address's street");
			street = scanner.nextLine();
			System.out.println("Add address's city");
			city = scanner.nextLine();
			System.out.println("Add address's state");
			state = scanner.nextLine();
			System.out.println("Add address's postalCode");
			postalCode = scanner.nextLine();
			result = dbService.executeQuery(addrStmt.addAddressToPerson(id, street, city, state, postalCode, personID));
		}
	}

	static void addPerson(PersonStatements personStmt, DatabaseService dbService, Scanner scanner) {
		int id;
		String fName;
		String lName;

		String str;
		String result = "";
		while (!result.equalsIgnoreCase("success")) {
			System.out.println("Add new person's id");
			str = scanner.nextLine();
			try {
				id = Integer.parseInt(str);

			} catch (NumberFormatException e) {
				System.out.println("Enter a correct number");
				continue;
			}
			System.out.println("Add person's first name");
			fName = scanner.nextLine();

			System.out.println("Add person's last name");
			lName = scanner.nextLine();
			if (fName.equals("") || lName.equals("")) {
				System.out.println("Type correct first and last name");
				continue;
			}
			result = dbService.executeQuery(personStmt.addPerson(id, fName, lName));
		}
	}

	private static void createTables(PersonStatements personStmt, AddressStatements addrStmt,
			DatabaseService dbService) {
//		dbService.executeQuery(personStmt.emptyTable());

		dbService.executeQuery(personStmt.createPersonTable());
		dbService.executeQuery(personStmt.createPersonTableIndex());
		dbService.executeQuery(addrStmt.createAddressTable());
		dbService.executeQuery(addrStmt.createAddressTableIndex());

	}
}
