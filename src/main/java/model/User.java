package model;

import java.util.Objects;

import static enums.Role.CLIENT;

/**
 * Represents a User object.
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private int role;

    /**
     * Creates a User object without params.
     */
    public User() {
        this.role = CLIENT.getRoleId();
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
     * Gets the value of {@link #role}.
     *
     * @return the value of {@link #role}.
     */
    public int getRole() {
        return role;
    }

    /**
     * This is a setter which sets the role.
     *
     * @param role - the role to be set
     */
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId &&
                role == user.role &&
                username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password);
    }

}
