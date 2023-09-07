package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public interface UserDataUserDAO {

    UserDataEntity createUserInUserData(AuthUserEntity user);

    UserDataEntity getUserFromUserDataByUsername(String username);

    void updateUserInUserData(UserDataEntity user);

    void deleteUserByUsernameInUserData(String username);
}
