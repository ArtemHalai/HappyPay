package dao.mappers;

import model.ClientDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

public class ClientDetailsMapper implements Mapper<ClientDetails> {


    @Override
    public ClientDetails getEntity(ResultSet resultSet) throws SQLException {
        ClientDetails clientDetails = new ClientDetails();

        clientDetails.setUserId(resultSet.getInt(USER_ID.getName()));
        clientDetails.setName(resultSet.getString(NAME.getName()));
        clientDetails.setLastName(resultSet.getString(LAST_NAME.getName()));
        clientDetails.setMobilePhone(resultSet.getString(MOBILE_PHONE.getName()));
        clientDetails.setUsername(resultSet.getString(USERNAME.getName()));
        clientDetails.setBirthday(resultSet.getDate(BIRTHDAY.getName()));

        return clientDetails;
    }
}
