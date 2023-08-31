package guru.qa.niffler.test;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.dao.impl.AuthUserDAOSpringJdbc;
import guru.qa.niffler.db.dao.impl.UserDataDAOSpringJdbc;
import guru.qa.niffler.db.jupiter.annotations.DBUser;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    AuthUserDAO authUserDAO = new AuthUserDAOSpringJdbc();
    UserDataUserDAO userDataUserDAO = new UserDataDAOSpringJdbc();

    @DBUser
    @Test
    void mainPageShouldBeVisibleAfterLogin(AuthUserEntity user) {
        open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();
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
}
