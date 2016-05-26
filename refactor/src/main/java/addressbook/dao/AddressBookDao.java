package addressbook.dao;

import addressbook.dto.Person;

import java.util.List;

/**
 * Class to work with database.
 */
public interface AddressBookDao {

	/**
	 * Save person to the database
	 * @param person the person to save
	 */
	void addPerson(Person person);

	/**
	 * @param name the name of person to search
	 * @return first found person with given name, null if not found.
	 */
	Person findFirstPerson(String name);

	/**
	 * Find all saved persons.
	 * @return a list of {@link Person} objects.
	 */
	List<Person> getAll();
}
