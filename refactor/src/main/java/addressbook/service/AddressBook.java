package addressbook.service;

import addressbook.dto.Person;

import java.util.List;

/**
 * Service to work with address book.
 *
 * Created by rparkhomchuk on 5/25/2016.
 */
public interface AddressBook {

    /**
     * @param name a person's name
     * @return true if person has phone number
     */
    boolean hasPhoneNumber(String name);

    /**
     * Check size of the address book.
     * @return address book persons count
     */
    int getSize();

    /**
     * Find phone number by person's name
     * @param name a person's name
     * @return phone number if exist, else return null.
     */
    String getPhoneNumber(String name);

    /**
     * Get all names in the address book
     * @param maxLength name max length
     * @return a list of trimmed names
     */
    List<String> getNames(int maxLength);

    /**
     * Find all Sweden persons in the address book
     * @return a list of persons
     */
    List<Person> getSwedenPersons();
}
