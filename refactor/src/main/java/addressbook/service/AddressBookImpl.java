package addressbook.service;

import addressbook.dao.AddressBookDao;
import addressbook.dao.AddressBookOracleDao;
import addressbook.dto.Person;

import java.util.*;
import java.util.function.Consumer;

public class AddressBookImpl implements AddressBook {

    public static final String SWEDEN_PHONE_CODE = "070";

    private AddressBookDao dao = new AddressBookOracleDao();

    @Override
    public boolean hasPhoneNumber(String name) {
        if (dao.findFirstPerson(name) != null && dao.findFirstPerson(name).getPhoneNumber() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getSize() {
        return dao.getAll().size();
    }

    @Override
    public String getPhoneNumber(String name) {
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
        List<Person> all = dao.getAll();
        ArrayList<Person> swedenGuys = new ArrayList<>();

        all.forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                if (person != null && person.getPhoneNumber() != null && person.getPhoneNumber().startsWith(SWEDEN_PHONE_CODE)) {
                    swedenGuys.add(person);
                }
            }
        });

        return swedenGuys;
    }

}
