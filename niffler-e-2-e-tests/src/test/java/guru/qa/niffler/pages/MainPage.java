package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    SelenideElement
            mainHeader = $(".main__header"),
            loginButton = $("a[href*='redirect']"),
            registerButton = $("a[href*='href=http://127.0.0.1:9000/register']");

    @Step("Проверить, что заголовок на главной странице содержит текст {value}")
    public MainPage checkMainHeaderText(String value) {
        mainHeader.shouldHave(text(value));
        return this;
    }

    @Step("Кликнуть на кнопку 'Login'")
    public LoginPage clickOnLoginButton() {
        loginButton.click();
        return new LoginPage();
    }
}
