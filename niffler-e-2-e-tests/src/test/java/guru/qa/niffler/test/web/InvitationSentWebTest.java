package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.annotations.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class InvitationSentWebTest extends BaseWebTest {
    @BeforeEach
    public void doLogin(@User(userType = User.UserType.INVITATION_SENT) UserJson userForTest) {
        open("http://127.0.0.1:3000/main");
        mainPage
                .checkMainHeaderText("Welcome to magic journey with Niffler. The coin keeper")
                .clickOnLoginButton()
                .signIn(userForTest);
    }

    @Test
    public void invitationSentShouldBeDisplayedInTable() {
        navigationPage
                .clickOnAllPeopleLink()
                .checkPendingInvitation();
    }
}
