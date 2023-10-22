package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public interface UserRepository {
    void createUserForTest(AuthUserEntity user);

    void removeUser(AuthUserEntity user);

    void createUsersFriends(UserDataEntity user, UserDataEntity anotherUser);

    void addInvitation(UserDataEntity userReceivedInvitation, UserDataEntity userSentInvitation);
}
