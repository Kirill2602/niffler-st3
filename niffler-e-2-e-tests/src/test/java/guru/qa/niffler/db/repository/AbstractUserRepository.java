package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public abstract class AbstractUserRepository implements UserRepository {
    private final AuthUserDAO authUserDAO;
    private final UserDataUserDAO userDataUserDAO;

    protected AbstractUserRepository(AuthUserDAO authUserDAO, UserDataUserDAO userDataUserDAO) {
        this.authUserDAO = authUserDAO;
        this.userDataUserDAO = userDataUserDAO;
    }

    @Override
    public void createUserForTest(AuthUserEntity user) {
        authUserDAO.createUser(user);
        userDataUserDAO.createUserInUserData(authUser(user));

    }

    @Override
    public void removeUser(AuthUserEntity user) {
        UserDataEntity userFromUserData = userDataUserDAO.getUserFromUserDataByUsername(user.getUsername());
        userDataUserDAO.deleteUserByUsernameInUserData(userFromUserData.getUsername());
        authUserDAO.deleteUserByUsername(user.getUsername());
    }

    @Override
    public void createUsersFriends(UserDataEntity user, UserDataEntity anotherUser) {
        user.addFriends(false, anotherUser);
        anotherUser.addFriends(true, user);
        userDataUserDAO.updateUserInUserData(user);
        userDataUserDAO.updateUserInUserData(anotherUser);
    }

    @Override
    public void addInvitation(UserDataEntity userReceivedInvitation, UserDataEntity userSentInvitation) {
        userReceivedInvitation.addFriends(true, userSentInvitation);
        userDataUserDAO.updateUserInUserData(userReceivedInvitation);
    }

    private UserDataEntity authUser(AuthUserEntity user) {
        UserDataEntity userData = new UserDataEntity();
        userData.setUsername(user.getUsername());
        userData.setCurrency(CurrencyValues.RUB);
        return userData;
    }
}
