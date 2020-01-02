package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientDetails {
    private int userId;
    private String name;
    private String surname;
    private String phoneNumber;
    private String username;
    private String password;
    private Date birthday;
    private List<UserAccount> list = new ArrayList<>();
}
