package model;

import lombok.Data;

import static enums.Role.CLIENT;

@Data
public class User {

    private int userId;
    private String username;
    private String password;
    private int role;

    public User() {
        this.role = CLIENT.getRoleId();
    }
}
