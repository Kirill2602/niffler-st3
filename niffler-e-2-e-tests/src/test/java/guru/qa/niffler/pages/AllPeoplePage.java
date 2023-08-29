package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {
    SelenideElement
            submitInvitation = $("div[data-tooltip-id='submit-invitation']"),
            declineInvitation = $("div[data-tooltip-id='decline-invitation']"),
            pendingInvitation = $(byText("Pending invitation"));

    @Step("Проверка полученного приглашения в друзья")
    public AllPeoplePage checkSubmitInvitation() {
        submitInvitation.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения кнопки 'отклонить-приглашение'")
    public AllPeoplePage checkDeclineInvitation() {
        declineInvitation.shouldBe(visible);
        return this;
    }

    @Step("Проверка ожидания подтверждения приглашения")
    public AllPeoplePage checkPendingInvitation() {
        pendingInvitation.shouldBe(visible);
        return this;
    }
}
