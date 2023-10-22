package guru.qa.niffler.api.category;

import guru.qa.niffler.api.RestService;
import guru.qa.niffler.model.CategoryJson;
import io.qameta.allure.Step;

import java.io.IOException;

public class CategoryServiceClient extends RestService {
    private final CategoryService categoryServiceClient = retrofit.create(CategoryService.class);

    public CategoryServiceClient() {
        super(CFG.nifflerSpendUrl());
    }

    @Step("Create Spend")
    public CategoryJson addCategory(CategoryJson category) throws IOException {
        return categoryServiceClient.addCategory(category)
                .execute()
                .body();
    }
}
