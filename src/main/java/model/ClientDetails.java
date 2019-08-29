package model;

import java.sql.Date;
import java.util.Objects;

public class ClientDetails {

    private int userId;
    private String name;
    private String lastName;
    private String mobilePhone;
    private String username;
    private Date birthday;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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
        if (!(o instanceof ClientDetails)) return false;
        ClientDetails client = (ClientDetails) o;
        return userId == client.userId &&
                name.equals(client.name) &&
                lastName.equals(client.lastName) &&
                mobilePhone.equals(client.mobilePhone) &&
                username.equals(client.username) &&
                birthday.equals(client.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, lastName, mobilePhone, username, birthday);
    }
}
