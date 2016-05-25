package service;

import dto.Person;

import java.util.List;

/**
 * Created by rparkhomchuk on 5/25/2016.
 */
public interface AddressBookService {

	boolean hasMobile(String name);

	int getSize();

	String getMobile(String name);

	List<String> getNames(int maxLength);

	List<Person> getSwedenPersons();
}
