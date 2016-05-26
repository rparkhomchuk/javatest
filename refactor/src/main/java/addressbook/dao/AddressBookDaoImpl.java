package addressbook.dao;

import addressbook.dto.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDaoImpl implements AddressBookDao {

	@Override
	public void addPerson(Person person) {
		try (
				Connection conn = Database.getConnection();
				PreparedStatement stmt = conn.prepareStatement("insert into AddressEntry values (?, ?, ?)");
		) {
			stmt.setLong(1, System.currentTimeMillis());
			stmt.setString(2, person.getName());
			stmt.setString(3, person.getPhoneNumber());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Person findFirstPerson(String name) {
		try (
				Connection conn = Database.getConnection();
				Statement stmt = conn.createStatement();
				) {
			ResultSet rs = stmt.executeQuery("select * from AddressEntry where name = '" + name + "'");

			while (rs.next()) {
				String foundName = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				Person person = new Person(foundName, phoneNumber);
				return person;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public List<Person> getAll() {
		try (
				Connection conn = Database.getConnection();
				Statement stmt = conn.createStatement();
				) {
			ResultSet rs = stmt.executeQuery("select * from AddressEntry");
			List<Person> entries = new ArrayList<>();

			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				entries.add(new Person(name, phoneNumber));
			}
			return entries;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
