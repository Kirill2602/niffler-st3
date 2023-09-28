package guru.qa.niffler.api;

public class SpendServiceClient extends RestService {
    public SpendServiceClient() {
        super(CFG.nifflerSpendUrl());
    }

    private final SpendService spendService = retrofit.create(SpendService.class);
}
