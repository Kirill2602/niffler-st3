package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.annotations.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.jupiter.annotations.User.UserType.INVITATION_RECEIVED;

public class InvitationReceivedWebTest extends BaseWebTest {
    @BeforeEach
    public void doLogin(@User(userType = INVITATION_RECEIVED) UserJson userForTest) {
        open("http://127.0.0.1:3000/");
        mainPage
                .checkMainHeaderText("Welcome to magic journey with Niffler. The coin keeper")
                .clickOnLoginButton()
                .signIn(userForTest);
    }

    @Test
    public void invitationReceivedShouldBeDisplayedInTableFriends() {
        navigationPage
                .clickOnFriendsLink()
                .checkFriendsList()
                .checkInvitationReceived();
    }

    @Test
    public void invitationReceivedShouldBeDisplayedInTableSubmitInvited() {
        navigationPage
                .clickOnAllPeopleLink()
                .checkSubmitInvitation();
    }

    @Test
    public void invitationReceivedShouldBeDisplayedInTableSubmitDeclined() {
        navigationPage
                .clickOnAllPeopleLink()
                .checkDeclineInvitation();
    }
}
