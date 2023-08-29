package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {
    ElementsCollection
            friendList = $$(".abstract-table tbody tr");
    SelenideElement
            invitationReceived = $("div[data-tooltip-id='submit-invitation']");

    @Step("Проверить, что список друзей/запросов в друзья не пустой")
    public FriendsPage checkFriendsList() {
        friendList.shouldBe(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверить, что есть запрос в друзья")
    public FriendsPage checkInvitationReceived() {
        invitationReceived.shouldBe(visible);
        return this;
    }

    @Step("Проверить, что вы дружите с {username}")
    public FriendsPage checkYourFriend(String username) {
        $(byText(username)).shouldBe(visible);
        return this;
    }
}
