package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement
            mainHeader = $(".main__header"),
            loginButton = $("a[href*='redirect']"),
            registerButton = $("a[href*='register']");

    @Step("Проверить, что заголовок на главной странице содержит текст {value}")
    public MainPage checkMainHeaderText(String value) {
        mainHeader.shouldHave(text(value));
        return this;
    }

    @Step("Кликнуть на кнопку 'Login'")
    public LoginPage clickOnLoginButton() {
        loginButton.shouldBe(visible).click();
        return new LoginPage();
    }

    @Step("Кликнуть на кнопку 'Register'")
    public RegistrationPage clickOnRegisterButton() {
        registerButton.click();
        return new RegistrationPage();
    }
}
