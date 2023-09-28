package guru.qa.niffler.test;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.dao.impl.AuthUserDAOHibernate;
import guru.qa.niffler.db.dao.impl.UserDataUserDAOHibernate;
import guru.qa.niffler.jupiter.annotations.WebTest;
import guru.qa.niffler.pages.RegistrationPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
public class RegistrationWebTest extends BaseWebTest{
    private final AuthUserDAO authUserDAO = new AuthUserDAOHibernate();
    private final UserDataUserDAO userDataUserDAO = new UserDataUserDAOHibernate();

    @Test
    @DisplayName("Успешная регистрация")
    public void successRegistration() {
        open("http://127.0.0.1:9000/register");
        registrationPage
                .setUsername("RegistrationUser16")
                .setPassword("12345")
                .setSubmitPassword("12345")
                .clickOnSignUpButton()
                .checkVisibilityOfSuccessRegisterMessage()
                .clickOnSignInLink()
                .checkVisibilityOfSignInText();
        authUserDAO.deleteUserByUsername("RegistrationUser16");
        userDataUserDAO.deleteUserByUsernameInUserData("RegistrationUser16");
    }

    @Test
    @DisplayName("Проверить наличие аттрибута required у полей")
    public void registrationWithEmptyUsername() {
        open("http://127.0.0.1:9000/register");
        registrationPage
                .checkRequiredAttribute();
    }

    @Test
    @DisplayName("Попытка регистрации с разными значенями в полях Password и SubmitPassword")
    public void registrationWithDifferentPasswordFieldValues() {
        open("http://127.0.0.1:9000/register");
        registrationPage
                .setUsername("RegistrationUser")
                .setPassword("12345")
                .setSubmitPassword("123")
                .clickOnSignUpButton()
                .checkDifferentPasswordsErrorText("Passwords should be equal");
    }
}
