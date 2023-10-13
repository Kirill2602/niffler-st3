package guru.qa.niffler.test.grpc;

import guru.qa.grpc.niffler.grpc.CalculateRequest;
import guru.qa.grpc.niffler.grpc.CalculateResponse;
import guru.qa.grpc.niffler.grpc.CurrencyResponse;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static guru.qa.grpc.niffler.grpc.CurrencyValues.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NifflerCurrencyGrpcTests extends BaseGrpcTest {
    @ParameterizedTest(name = "Запрос всех валют возвращает валюту {0}")
    @EnumSource(value = CurrencyValues.class, names = {"RUB", "USD", "KZT", "EUR"})
    void getAllCurrenciesTest(CurrencyValues currency) {
        CurrencyResponse allCurrencies = currencyStub.getAllCurrencies(EMPTY);
        assertEquals(4, allCurrencies.getAllCurrenciesList().size());
        assertTrue(allCurrencies.getAllCurrenciesList().stream().anyMatch(c -> c.getCurrency().equals(currency)));
    }

    @ParameterizedTest(name = "Перевод валюты {0} в валюту {0} возвращает ту же сумму, что и отправляет")
    @EnumSource(value = CurrencyValues.class, names = {"RUB", "USD", "KZT", "EUR"})
    void calculateRateTest(CurrencyValues currency) {
        final double amount = 100.0;
        final CalculateRequest cr = CalculateRequest.newBuilder()
                .setAmount(amount)
                .setSpendCurrency(currency)
                .setDesiredCurrency(currency)
                .build();
        CalculateResponse response = currencyStub.calculateRate(cr);
        assertEquals(amount, response.getCalculatedAmount());
    }

    static Stream<Arguments> calculateRateFromDefaultCurrency() {
        return Stream.of(
                Arguments.of(200, RUB, USD, 3),
                Arguments.of(250, RUB, EUR, 3.47),
                Arguments.of(300, RUB, KZT, 2142.86),
                Arguments.of(-200, RUB, USD, -3),
                Arguments.of(-250, RUB, EUR, -3.47),
                Arguments.of(-300, RUB, KZT, -2142.86),
                Arguments.of(0, RUB, USD, 0),
                Arguments.of(0, RUB, EUR, 0),
                Arguments.of(0, RUB, KZT, 0)
        );
    }

    @ParameterizedTest(name = "Конвертация {0} {1} в {2} возвращает {3} {2}")
    @MethodSource
    void calculateRateFromDefaultCurrency(double amount, CurrencyValues spendCurrency, CurrencyValues desiredCurrency, double expectedResult) {
        final CalculateRequest cr = CalculateRequest.newBuilder()
                .setAmount(amount)
                .setSpendCurrency(spendCurrency)
                .setDesiredCurrency(desiredCurrency)
                .build();
        CalculateResponse response = currencyStub.calculateRate(cr);
        assertEquals(expectedResult, response.getCalculatedAmount());
    }

    static Stream<Arguments> calculateRateBetweenOtherCurrencies() {
        return Stream.of(
                Arguments.of(200, USD, KZT, 95238.1),
                Arguments.of(250, USD, EUR, 231.48),
                Arguments.of(300, EUR, KZT, 154285.71),
                Arguments.of(-200, EUR, USD, -216.0),
                Arguments.of(-250, KZT, EUR, -0.49),
                Arguments.of(-300, KZT, USD, -0.63),
                Arguments.of(0, USD, EUR, 0),
                Arguments.of(0, USD, KZT, 0),
                Arguments.of(0, USD, KZT, 0),
                Arguments.of(0, USD, EUR, 0),
                Arguments.of(0, EUR, KZT, 0),
                Arguments.of(0, EUR, USD, 0),
                Arguments.of(0, KZT, EUR, 0),
                Arguments.of(0, KZT, USD, 0)
                );
    }

    @ParameterizedTest(name = "Конвертация трат {0} {1} в {2} возвращает {3} {2}")
    @MethodSource
    void calculateRateBetweenOtherCurrencies(double amount, CurrencyValues spendCurrency, CurrencyValues desiredCurrency, double expectedResult) {
        final CalculateRequest cr = CalculateRequest.newBuilder()
                .setAmount(amount)
                .setSpendCurrency(spendCurrency)
                .setDesiredCurrency(desiredCurrency)
                .build();
        CalculateResponse response = currencyStub.calculateRate(cr);
        assertEquals(expectedResult, response.getCalculatedAmount());
    }
}
