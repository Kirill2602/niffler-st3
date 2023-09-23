package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class NavigationPage {
    private final SelenideElement
            friendsLink = $("li[data-tooltip-id='friends']"),
            allPeopleLink = $("li[data-tooltip-id='people']"),
            profileLink = $("li[data-tooltip-id='profile']");

    @Step("Перейти на страницу 'Friends'")
    public FriendsPage clickOnFriendsLink() {
        friendsLink.click();
        return new FriendsPage();
    }

    @Step("Перейти на страницу 'All People'")
    public AllPeoplePage clickOnAllPeopleLink() {
        allPeopleLink.click();
        return new AllPeoplePage();
    }

    @Step("Перейти на страницу профиля")
    public ProfilePage clickOnProfileLink() {
        profileLink.click();
        return new ProfilePage();
    }
}
