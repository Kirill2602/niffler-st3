package guru.qa.niffler.test.web;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.dao.impl.AuthUserDAOHibernate;
import guru.qa.niffler.db.dao.impl.UserDataUserDAOHibernate;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;
import guru.qa.niffler.jupiter.annotations.ApiLogin;
import guru.qa.niffler.jupiter.annotations.DBUser;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseWebTest {

    private final AuthUserDAO authUserDAO = new AuthUserDAOHibernate();
    private final UserDataUserDAO userDataUserDAO = new UserDataUserDAOHibernate();
    private static final String defaultPassword = "12345";

    @ApiLogin
    @DBUser
    @Test
    void mainPageShouldBeVisibleAfterLogin() {
        open(CFG.baseUrl() + "/main");
        $(".main-content__section-stats").shouldBe(visible);
    }

    @DBUser
    @Test
    void shouldUpdateUserInAuthDB(AuthUserEntity createdUser) {
        createdUser.setEnabled(false);
        createdUser.setAccountNonExpired(false);
        createdUser.setAccountNonLocked(false);
        createdUser.setCredentialsNonExpired(false);
        authUserDAO.updateUser(createdUser);

        AuthUserEntity user = authUserDAO.getUserFromAuthUserById(createdUser.getId());
        assertAll(
                () -> assertFalse(user.getEnabled()),
                () -> assertFalse(user.getAccountNonExpired()),
                () -> assertFalse(user.getAccountNonLocked()),
                () -> assertFalse(user.getCredentialsNonExpired())
        );
    }

    @DBUser
    @Test
    void shouldUpdateUserInUserDataDB(AuthUserEntity createdUser) {
        UserDataEntity user = userDataUserDAO.getUserFromUserDataByUsername(createdUser.getUsername());
        user.setFirstname("Ivan");
        user.setSurname("Ivanov");
        user.setCurrency(CurrencyValues.USD);
        userDataUserDAO.updateUserInUserData(user);

        user = userDataUserDAO.getUserFromUserDataByUsername(createdUser.getUsername());

        UserDataEntity updatedUser = user;
        assertAll(
                () -> assertEquals("Ivan", updatedUser.getFirstname(), "FirstName not updated"),
                () -> assertEquals("Ivanov", updatedUser.getSurname(), "Surname not updated"),
                () -> assertEquals(CurrencyValues.USD, updatedUser.getCurrency(), "Currency not updated")
        );
    }

    @Test
    @DBUser(password = defaultPassword)
    void loginWithInvalidPassword(AuthUserEntity user) {
        open("http://127.0.0.1:9000/login");
        loginPage
                .signIn(user.getUsername(), "1111");
        loginPage
                .checkErrorText("Неверные учетные данные пользователя");
    }

    @Test
    @DBUser(password = defaultPassword)
    void loginWithInvalidUsername() {
        open("http://127.0.0.1:9000/login");
        loginPage
                .signIn("InvalidUsername", defaultPassword);
        loginPage
                .checkErrorText("Неверные учетные данные пользователя");
    }
}
