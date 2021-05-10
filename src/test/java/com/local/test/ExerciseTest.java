package test.java.com.local.test;

import java.util.HashMap;

import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.local.database.DatabaseService;
import com.local.objects.Address;
import com.local.objects.AddressStatements;
import com.local.objects.Person;
import com.local.objects.PersonStatements;

@TestMethodOrder(OrderAnnotation.class)
public class ExerciseTest {
	static DatabaseService dbService = new DatabaseService();
	static PersonStatements personStmt = new PersonStatements();
	static AddressStatements addrStmt = new AddressStatements();

	@BeforeAll
	static void createTablePersonandAddress() {
		dbService.executeQuery(personStmt.emptyTable());
		dbService.executeQuery(personStmt.createPersonTable());
		dbService.executeQuery(personStmt.createPersonTableIndex());
		dbService.executeQuery(addrStmt.createAddressTable());
		dbService.executeQuery(addrStmt.createAddressTableIndex());
		dbService.executeQuery(personStmt.addPerson(1, "Heba", "Salama"));
		dbService.executeQuery(personStmt.addPerson(2, "Jo", "Malon"));
		dbService.executeQuery(personStmt.addPerson(3, "Tommy", "Hilfiger"));
		dbService.executeQuery(personStmt.addPerson(4, "Zain", "Abaza"));
		dbService.executeQuery(addrStmt.addAddressToPerson(1, "dundrum", "dublin", "dublin", "D14", 1));
		dbService.executeQuery(addrStmt.addAddressToPerson(2, "wicklow", "wyckham", "wicklow", "D1", 2));

	}

	@Test
	@Order(1)
	void addPersonwithID() {
		String result = dbService.executeQuery(personStmt.addPerson(5, "Tommy", "Hilfiger"));
		Assertions.assertEquals("success", result);
	}

	@Test
	@Order(2)
	void editPerson() {
		dbService.executeQuery(personStmt.editPersonFirstName("Hilfigher", "Hilfiger"));
		JSONArray array = dbService.getObjects(personStmt.getPerson(3));
		String fName = array.getJSONObject(0).get(Person.getfName()).toString();
		Assertions.assertEquals("Hilfigher", fName);
	}

	@Test
	@Order(3)
	void deletePerson() {
		dbService.executeQuery(personStmt.deletePerson(5));
		String count = dbService.getObjects(personStmt.getCountPerson()).getJSONObject(0).getString("COUNT(*)");
		Assertions.assertEquals("4", count);
	}

	@Test
	@Order(4)
	void addAddresstoPersonwithValidID() {
		String result = dbService
				.executeQuery(addrStmt.addAddressToPerson(3, "waterdord", "bishops", "waterford", "X91", 3));
		Assertions.assertEquals("success", result);
	}

	@Test
	@Order(5)
	void editAddress() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Address.getStreet(), "street");
		map.put(Address.getCity(), "city");
		map.put(Address.getPostalcode(), "ED45");
		dbService.executeQuery(addrStmt.editAddress(map, Address.getState(), "wicklow"));
		String result = dbService.getObjects(personStmt.getPerson(2)).getJSONObject(0)
				.getString(Address.getPostalcode());
		Assertions.assertEquals("ED45", result);
	}

	@Test
	@Order(6)
	void deleteAddress() {
		dbService.executeQuery(addrStmt.deleteAddress(2));
		boolean bool = dbService.getObjects(personStmt.getPerson(2)).getJSONObject(0).has(Address.getCity());
		Assertions.assertEquals(false, bool);
	}

	@Test
	@Order(7)
	void countPerson() {
		String count = dbService.getObjects(personStmt.getCountPerson()).getJSONObject(0).getString("COUNT(*)");
		Assertions.assertEquals("4", count);
	}

	@Test
	@Order(8)
	void listPeron() {
		int count = dbService.getObjects(personStmt.getList()).length();
		Assertions.assertEquals(4, count);
	}
}
