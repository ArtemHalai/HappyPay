package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a ClientDetails object.
 */
public class ClientDetails {

    private int userId;
    private String name;
    private String surname;
    private String phoneNumber;
    private String username;
    private String password;
    private Date birthday;
    private List<UserAccount> list = new ArrayList<>();

    /**
     * Gets the list of UserAccount objects {@link #list}.
     *
     * @return the list of UserAccount objects {@link #list}.
     */
    public List<UserAccount> getList() {
        return list;
    }

    /**
     * This is a setter which sets the list.
     *
     * @param list - the list to be set
     */
    public void setList(List<UserAccount> list) {
        this.list = list;
    }

    /**
     * Gets the value of {@link #password}.
     *
     * @return the value of {@link #password}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This is a setter which sets the password.
     *
     * @param password - the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the value of {@link #userId}.
     *
     * @return the value of {@link #userId}.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This is a setter which sets the user id.
     *
     * @param userId - the user id to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the value of {@link #name}.
     *
     * @return the value of {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * This is a setter which sets the name.
     *
     * @param name - the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of {@link #surname}.
     *
     * @return the value of {@link #surname}.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * This is a setter which sets the surname.
     *
     * @param surname - the surname to be set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the value of {@link #phoneNumber}.
     *
     * @return the value of {@link #phoneNumber}.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This is a setter which sets the phone number.
     *
     * @param phoneNumber - the phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the value of {@link #username}.
     *
     * @return the value of {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This is a setter which sets the username.
     *
     * @param username - the username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the value of {@link #birthday}.
     *
     * @return the value of {@link #birthday}.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This is a setter which sets the birthday.
     *
     * @param birthday - the birthday to be set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDetails client = (ClientDetails) o;
        return userId == client.userId &&
                Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(phoneNumber, client.phoneNumber) &&
                Objects.equals(username, client.username) &&
                Objects.equals(birthday, client.birthday) &&
                Objects.equals(list, client.list) &&
                Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname, phoneNumber, username, birthday, list, password);
    }
}
