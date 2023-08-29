package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    SelenideElement
            usernameField = $("input[name='username']"),
            passwordField = $("input[name='password']"),
            signInButton = $("button[type='submit']");

    public NavigationPage signIn(UserJson userForTest) {
        setUsername(userForTest.getUsername());
        setPassword(userForTest.getPassword());
        clickOnSignInButton();
        return new NavigationPage();
    }

    public NavigationPage signIn(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickOnSignInButton();
        return new NavigationPage();
    }

    @Step("Ввести имя пользователя: {username}")
    public void setUsername(String username) {
        usernameField.setValue(username);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        passwordField.setValue(password);
    }

    @Step("Кликнуть на кнопку 'Sign in'")
    public void clickOnSignInButton() {
        signInButton.click();
    }
}
