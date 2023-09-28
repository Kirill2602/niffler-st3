package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.annotations.ApiLogin;
import guru.qa.niffler.jupiter.annotations.Category;
import guru.qa.niffler.jupiter.annotations.Spend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SpendingWebTest extends BaseWebTest {
    private final String
            USER_NAME = "Kirill",
            PASSWORD = "12345",
            DESCRIPTION = "Ужин в ресторане",
            CATEGORY = "Ресторан";
    private final double AMOUNT = 7000.00;


    static {
        Configuration.browserSize = "1980x1024";
    }

    @Category(
            username = USER_NAME,
            category = CATEGORY
    )
    @ApiLogin(username = USER_NAME, password = PASSWORD)
    @Spend(username = USER_NAME,
            description = DESCRIPTION,
            category = CATEGORY,
            amount = AMOUNT,
            currency = CurrencyValues.RUB)
    @Test
    void spendingShouldBeDeletedAfterDeleteAction(SpendJson createdSpend) {
        open(CFG.nifflerFrontUrl() + "/main");
        $(".spendings__content tbody")
                .$$("tr")
                .find(text(createdSpend.getDescription()))
                .$("td")
                .scrollTo()
                .click();

        $(byText("Delete selected")).click();

        $(".spendings__content tbody")
                .$$("tr")
                .shouldHave(size(0));
    }
}
