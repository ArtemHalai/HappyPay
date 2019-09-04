package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDetails {

    private int userId;
    private String name;
    private String surname;
    private String phoneNumber;
    private String username;
    private String password;
    private Date birthday;
    private List<UserAccount> list = new ArrayList<>();

    public List<UserAccount> getList() {
        return list;
    }

    public void setList(List<UserAccount> list) {
        this.list = list;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

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
