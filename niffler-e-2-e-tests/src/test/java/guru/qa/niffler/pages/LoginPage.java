package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement
            usernameField = $("input[name='username']"),
            passwordField = $("input[name='password']"),
            signInButton = $("button[type='submit']"),
            signInText = $(byText("Please sign in")),
            invalidUserDataError = $("p.form__error");

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

    @Step("Проверить видимость текста 'Please sign in' на странице")
    public void checkVisibilityOfSignInText() {
        signInText.shouldBe(visible);
    }

    @Step("Проверить отображение ошибки {error}")
    public void checkErrorText(String error) {
        invalidUserDataError.shouldHave(text(error));
    }
}
