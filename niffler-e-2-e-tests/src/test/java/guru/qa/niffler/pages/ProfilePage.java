package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {
    private final SelenideElement
            nameField = $("input[name='firstname']"),
            surnameField = $("input[name='surname']"),
            currencyField = $x("//div[@class=' css-b62m3t-container']"),
            submitButton = $(byText("Submit")),
            successProfileUpdateMessage = $(byText("Profile updated!")),
            categoryField = $("input[name='category']"),
            createButton = $(byText("Create")),
            successAddedNewCategory = $(byText("New category added!"));
    private final ElementsCollection
            categoryList = $$("li.categories__item");

    @Step("Вставить имя {name}")
    public ProfilePage setName(String name) {
        nameField.setValue(name);
        return this;
    }

    @Step("Вставить фамилию {surname}")
    public ProfilePage setSurname(String surname) {
        surnameField.setValue(surname);
        return this;
    }

    @Step("Вставить валюту {currency}")
    public ProfilePage setCurrency(String currency) {
        currencyField.scrollTo().click();
        $(byText(currency)).click();
        return this;
    }

    @Step("Кликнуть на кнопку Submit")
    public ProfilePage clickOnSubmitButton() {
        submitButton.click();
        return this;
    }

    @Step("Проверить, что появилось сообщение с текстом {messageText}")
    public void checkVisibilityOfSuccessProfileUpdateMessage(String messageText) {
        successProfileUpdateMessage.shouldHave(text(messageText));
    }

    @Step("Вставить значение {category} в поле 'Category name'")
    public ProfilePage setNewCategory(String category) {
        categoryField.setValue(category);
        return this;
    }

    @Step("Нажать на кнопку 'Create'")
    public ProfilePage clickOnCreateButton() {
        createButton.click();
        return this;
    }

    @Step("Проверить, что появилось сообщение с текстом {messageText}")
    public ProfilePage checkSuccessAddedNewCategory(String messageText) {
        successAddedNewCategory.shouldHave(text(messageText));
        return this;
    }

    @Step("Проверить, что в списке категорий появилась новая категория {category}")
    public ProfilePage checkCategoryList(String category) {
        categoryList.filterBy(text(category)).shouldHave(sizeGreaterThan(0));
        return this;
    }
}
