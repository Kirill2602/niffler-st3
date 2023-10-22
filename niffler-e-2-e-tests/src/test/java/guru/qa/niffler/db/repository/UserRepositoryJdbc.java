package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.impl.AuthUserDAOJdbc;
import guru.qa.niffler.db.dao.impl.UserDataUserDAOJdbc;

public class UserRepositoryJdbc extends AbstractUserRepository {
    public UserRepositoryJdbc() {
        super(new AuthUserDAOJdbc(), new UserDataUserDAOJdbc());
    }
}
