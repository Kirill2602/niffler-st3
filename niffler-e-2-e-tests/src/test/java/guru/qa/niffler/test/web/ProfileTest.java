package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.annotations.ApiLogin;
import guru.qa.niffler.jupiter.annotations.DBUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class ProfileTest extends BaseWebTest {
    private final String category = "New Category";

    @Test
    @ApiLogin
    @DBUser
    @DisplayName("Редактирование данных пользователя")
    void updateUserProfile() {
        open(CFG.baseUrl() + "/profile");
        profilePage
                .setName("Test")
                .setSurname("Testov")
                .setCurrency("USD")
                .clickOnSubmitButton()
                .checkVisibilityOfSuccessProfileUpdateMessage("Profile updated!");
    }

    @Test
    @ApiLogin
    @DBUser
    @DisplayName("Добавление новой категории трат в профиле")
    public void addNewCategory() {
        open(CFG.baseUrl() + "/profile");
        profilePage
                .setNewCategory(category)
                .clickOnCreateButton()
                .checkSuccessAddedNewCategory(category)
                .checkCategoryList(category);
    }
}
