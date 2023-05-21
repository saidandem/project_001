package com.hussain.project_001.services;

import com.hussain.project_001.model.Level;
import com.hussain.project_001.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockPersonsDB {
    private static Random random = new Random();

    private static final String[] FIRST_NAMES = {
            "John", "Emma", "Michael", "Olivia", "William", "Sophia", "James", "Ava", "Robert", "Isabella",
            "David", "Mia", "Joseph", "Charlotte", "Daniel", "Amelia", "Matthew", "Harper", "Andrew", "Evelyn"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"
    };

    public static final String[] CITIES = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
            "San Antonio", "San Diego", "Dallas", "San Jose"
    };

    private static final String[] STREET_NAMES = {
            "Main St", "Park Ave", "Oak St", "Maple Ave", "Elm St", "Cedar Ave",
            "Washington St", "Broadway", "First St", "Second Ave"
    };

    private static final String[] ZIP_CODES = {
            "10001", "90001", "60601", "77001", "85001", "19101",
            "78201", "92101", "75201", "95101"
    };

    public static List<Person> persons = new ArrayList<>();
    static {
        for (int i = 0; i < 10; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String addressLine1 = random.nextInt(9999) + " " + STREET_NAMES[random.nextInt(STREET_NAMES.length)];
            String addressLine2 = "Apt " + (random.nextInt(100) + 1);
            String city = CITIES[random.nextInt(CITIES.length)];
            int zipCode = Integer.parseInt(ZIP_CODES[random.nextInt(ZIP_CODES.length)]);

            // Generate a random year between 1950 and 2000
            int year = random.nextInt(50) + 1950;
            // Generate a random month (1-12)
            int month = random.nextInt(12) + 1;
            // Generate a random day (1-28/30/31) depending on the month
            int day = random.nextInt(getDaysInMonth(year, month)) + 1;
            LocalDate dateOfBirth = LocalDate.of(year, month, day);

            Person person = new Person();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setAddress1(addressLine1);
            person.setAddress2(addressLine2);
            person.setCity(city);
            person.setZip(zipCode);
            person.setDateOfBirth(dateOfBirth);
            person.setLevel(Level.values()[random.nextInt(3)]);
            persons.add(person);
        }
    }

    // Helper method to get the number of days in a given month and year
    private static int getDaysInMonth(int year, int month) {
        if (month == 2) {
            return LocalDate.ofYearDay(year, 1).isLeapYear() ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }
}
