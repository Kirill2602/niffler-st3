package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotations.WebTest;
import guru.qa.niffler.pages.*;

@WebTest
public abstract class BaseWebTest {
    protected static final Config CFG = Config.getInstance();
    protected final MainPage mainPage = new MainPage();
    protected final AllPeoplePage allPeoplePage = new AllPeoplePage();
    protected final FriendsPage friendsPage = new FriendsPage();
    protected final LoginPage loginPage = new LoginPage();
    protected final NavigationPage navigationPage = new NavigationPage();
    protected final ProfileMainPage profileMainPage = new ProfileMainPage();
    protected final ProfilePage profilePage = new ProfilePage();
    protected final RegistrationPage registrationPage = new RegistrationPage();
}
