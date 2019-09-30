package dao.mappers;

import model.ClientDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

import static enums.Fields.*;

/**
 * The object used for getting client details data from result set.
 */
public class ClientDetailsMapper implements Mapper<ClientDetails> {

    /**
     * Method to get client details entity from result set.
     *
     * @param resultSet The result set object.
     * @return The ClientDetails object.
     * @throws SQLException If sql exception occurred while processing this request.
     * @see ResultSet
     * @see ClientDetails
     */
    @Override
    public ClientDetails getEntity(ResultSet resultSet) throws SQLException {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserId(resultSet.getInt(USER_ID.getName()));
        clientDetails.setName(resultSet.getString(NAME.getName()));
        clientDetails.setSurname(resultSet.getString(SURNAME.getName()));
        clientDetails.setPhoneNumber(resultSet.getString(MOBILE_PHONE.getName()));
        clientDetails.setUsername(resultSet.getString(USERNAME.getName()));
        clientDetails.setBirthday(resultSet.getDate(BIRTHDAY.getName()));

        return clientDetails;
    }
}
