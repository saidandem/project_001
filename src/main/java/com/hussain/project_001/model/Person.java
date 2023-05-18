package com.hussain.project_001.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty address1;
    private StringProperty address2;
    private StringProperty city;
    private IntegerProperty zip;

    private ObjectProperty<LocalDate> dateOfBirth;
    private ObjectProperty<Level> level;

    public Person() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.address1 = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.zip = new SimpleIntegerProperty();
        this.dateOfBirth = new SimpleObjectProperty<>();
        this.level = new SimpleObjectProperty<>(Level.LEVEL1);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress1() {
        return address1.get();
    }

    public StringProperty address1Property() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1.set(address1);
    }

    public String getAddress2() {
        return address2.get();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2.set(address2);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(final String city) {
        this.city.set(city);
    }

    public int getZip() {
        return zip.get();
    }

    public IntegerProperty zipProperty() {
        return zip;
    }

    public void setZip(final int zip) {
        this.zip.set(zip);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public Level getLevel() {
        return level.get();
    }

    public ObjectProperty<Level> levelProperty() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level.set(level);
    }
}
