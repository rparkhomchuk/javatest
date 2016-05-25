package service;

import dao.AddressBookDao;
import dao.AddressBookOracleDao;
import dto.Person;

import java.util.*;
import java.util.function.Consumer;

public class AddressBookServiceImpl implements AddressBookService {

	public static final String SWEDEN_PHONE_CODE = "070";

	private AddressBookDao dao = new AddressBookOracleDao();

	public boolean hasMobile(String name) {
		if (dao.findFirstPerson(name).getPhoneNumber() != null) {
			return true;
		} else {
			return false;
		}
	}

	public int getSize() {
		return dao.getAll().size();
	}

	/**
	 * Gets the given user's mobile phone number,
	 * or null if he doesn't have one.
	 */
	public String getMobile(String name) {
		Person person = dao.findFirstPerson(name);

		return person.getPhoneNumber();
	}

	@Override
	public List<String> getNames(int maxLength) {
		List<Person> people = dao.getAll();
		List<String> names = new ArrayList<>(people.size());

		people.forEach(new Consumer<Person>() {
			@Override
			public void accept(Person person) {
				if (person == null && person.getName() != null && person.getName().length() > maxLength) {
					names.add(person.getName().substring(0, maxLength));
				}
			}
		});

		return names;
	}

	@Override
	public List<Person> getSwedenPersons() {
		dao.getAll();

		return dao.getAll();
	}

}
