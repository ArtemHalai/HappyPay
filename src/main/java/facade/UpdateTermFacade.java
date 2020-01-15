package facade;

import factories.JDBCConnectionFactory;
import lombok.extern.log4j.Log4j;
import model.UserAccount;
import service.UserAccountService;
import util.UserAccountGetter;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class UpdateTermFacade {

    private UserAccountService userAccountService;
    private JDBCConnectionFactory connectionFactory;

    public UpdateTermFacade() {
        connectionFactory = JDBCConnectionFactory.getInstance();
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public boolean updateTerm(int userId) {
        try(Connection connection = connectionFactory.getConnection()) {
            userAccountService.setDefaultUserAccountDAO(connection);
            boolean updated = userAccountService.updateTerm(userId);
            if (updated) {
                return true;
            }
        }catch (SQLException e){
            log.error("Could not update term", e);
        }
        return false;
    }

    public UserAccount getUserAccount(int userId) {
        return UserAccountGetter.getUserAccount(userId);
    }
}
