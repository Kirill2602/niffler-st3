package guru.qa.niffler.util;

import guru.qa.niffler.model.UserJson;

public class DataUtils {
    public static UserJson createUserJ(String username, String password) {
        UserJson user = new UserJson();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
