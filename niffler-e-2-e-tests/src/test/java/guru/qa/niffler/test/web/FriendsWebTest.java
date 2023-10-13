package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.annotations.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.jupiter.annotations.User.UserType.WITH_FRIENDS;

public class FriendsWebTest extends BaseWebTest {
    @BeforeEach
    public void doLogin(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        open("http://127.0.0.1:3000/");
        mainPage
                .checkMainHeaderText("Welcome to magic journey with Niffler. The coin keeper")
                .clickOnLoginButton()
                .signIn(userForTest);
    }

    @Test
    @AllureId("100")
    public void friendShouldBeDisplayedInTable(@User(userType = WITH_FRIENDS) UserJson anotherUser) {
        navigationPage
                .clickOnFriendsLink()
                .checkFriendsList()
                .checkYourFriend(anotherUser.getUsername());
    }
}
