package guru.qa.niffler.test;

import guru.qa.niffler.db.jupiter.annotations.DBUser;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotations.WebTest;
import guru.qa.niffler.pages.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@WebTest
public class CategoryTest {
    private final MainPage mainPage = new MainPage();
    private final String
            defaultPassword = "12345",
            category = "New Category";

    @Test
    @DBUser(password = defaultPassword)
    @DisplayName("Добавление новой категории в профиле")
    public void addNewCategory(AuthUserEntity user) {
        open("http://127.0.0.1:3000/main");
        mainPage
                .clickOnLoginButton()
                .signIn(user.getUsername(), defaultPassword)
                .clickOnProfileLink()
                .setNewCategory(category)
                .clickOnCreateButton()
                .checkSuccessAddedNewCategory(category)
                .checkCategoryList(category);
    }
}
