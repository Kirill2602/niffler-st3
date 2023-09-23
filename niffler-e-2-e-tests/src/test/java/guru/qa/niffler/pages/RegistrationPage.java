package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {
    private final SelenideElement
            usernameField = $("#username"),
            passwordField = $("#password"),
            submitPasswordField = $("#passwordSubmit"),
            signUpButton = $(byText("Sign Up")),
            successRegisterModalText = $(byText("Congratulations! You've registered!")),
            signInLink = $("a[href*='redirect']"),
            differentPasswordsErrorText = $("span.form__error");

    @Step("Заполнить поле username значением {username}")
    public RegistrationPage setUsername(String username) {
        usernameField.setValue(username);
        return this;
    }

    @Step("Заполнить поле password значением {password}")
    public RegistrationPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Заполнить поле submitPassword значением {password}")
    public RegistrationPage setSubmitPassword(String password) {
        submitPasswordField.setValue(password);
        return this;
    }

    @Step("Кликнуть на кнопку Sign Up")
    public RegistrationPage clickOnSignUpButton() {
        signUpButton.click();
        return this;
    }

    @Step("Проверить видимость сообщения об успешной регистрации")
    public RegistrationPage checkVisibilityOfSuccessRegisterMessage() {
        successRegisterModalText.shouldBe(visible);
        return this;
    }

    @Step("Кликнуть на ссылку Sign In")
    public LoginPage clickOnSignInLink() {
        signInLink.click();
        return new LoginPage();
    }

    @Step("Проверить наличия аттрибута required у полей")
    public void checkRequiredAttribute() {
        assertAll(
                () -> assertTrue(usernameField.has(attribute("required")), "У поля username отсутствует аттрибут required"),
                () -> assertTrue(passwordField.has(attribute("required")), "У поля password отсутствует аттрибут required"),
                () -> assertTrue(submitPasswordField.has(attribute("required")), "У поля submitPassword отсутствует аттрибут required")
        );
    }

    @Step("Проверить отображения ошибки с текстом {error}")
    public void checkDifferentPasswordsErrorText(String error) {
        differentPasswordsErrorText.shouldHave(text(error));
    }
}
