package guru.qa.niffler.test;

import guru.qa.niffler.db.jupiter.annotations.DBUser;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotations.WebTest;
import guru.qa.niffler.pages.MainPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@WebTest
public class ProfileTest {
    private final MainPage mainPage = new MainPage();
    private final String defaultPassword = "12345";

    @Test
    @DBUser(password = "12345")
    void updateUserProfile(AuthUserEntity user) {
        open("http://127.0.0.1:3000/main");
        mainPage
                .clickOnLoginButton()
                .signIn(user.getUsername(), defaultPassword)
                .clickOnProfileLink()
                .setName("Test")
                .setSurname("Testov")
                .setCurrency("USD")
                .clickOnSubmitButton()
                .checkVisibilityOfSuccessProfileUpdateMessage("Profile updated!");
    }
}
