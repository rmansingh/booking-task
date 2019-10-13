package com.fromatob.uiTest;

import com.base.BaseTest;
import com.google.common.collect.ImmutableMap;
import com.pages.*;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Owner("Rupak Mansingh")
@Feature("Search and book the ICE train ticket")
@Severity(SeverityLevel.CRITICAL)
public class BookTrainTicketTest extends BaseTest {

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"Berlin", "Nuremberg", "Mr", "firstname", "lastname", "test@gmail.com"}
        };
    }

    @Test(dataProvider = "data")
    @Description("Search and book the ICE train ticket")
    public void bookTrainTicket(String departureCity, String arrivalCity, String salutation, String firstName, String lastName, String email) {

        SearchPage searchPage = searchForTrain(departureCity, arrivalCity);

        assertThat("It didn't land in search page", searchPage.isTransportTypeVisible(), is(true));
        assertThat("FromAtoB page title didn't display", getDriver().getTitle(), is("fromAtoB"));
        assertThat("Search page url didn't display", getDriver().getCurrentUrl(), is(containsStringIgnoringCase("search")));

        OverViewPage overViewPage = searchPage
                .closeEnvironmentalPopUp()
                .selectTrain()
                .selectFirstOption()
                .clickChooseFare();
        assertThat("FromAtoB page title didn't display", getDriver().getTitle(), is("fromAtoB"));
        assertThat("Overview page url didn't display", getDriver().getCurrentUrl(), is(containsStringIgnoringCase("overview")));

        Float beforeUpSellPrice = new OverViewPage(getDriver())
                .getTotalPrice();
        float afterUpSellPrice = overViewPage.selectFlexibilityAndComfortClassOption()
                .getTotalPrice();
        assertThat("Price didn't change after up-sell selection", afterUpSellPrice, greaterThan(beforeUpSellPrice));

        TravellerInfoPage travellerInfoPage = overViewPage.clickContinueToTravellerInfo();
        assertThat("FromAtoB page title didn't display", getDriver().getTitle(), is("fromAtoB"));
        assertThat("Info page url didn't display", getDriver().getCurrentUrl(), is(containsStringIgnoringCase("info")));

        Float priceOnTravellerInfoPage = travellerInfoPage.getTotalPrice();

        travellerInfoPage.enterCustomerInfo(salutation, firstName, lastName, email)
                .clickContinueToPayment();
        boolean isTermsAndConditionsSelected = travellerInfoPage.isErrorInTermsAndConditions();

        assertThat("Terms and conditions error is not displayed", isTermsAndConditionsSelected, is(true));

        PaymentPage paymentPage = travellerInfoPage.clickTermsAndConditions()
                .clickContinueToPayment();
        paymentPage.waitUntilForPaymentPage();
        Float priceOnPaymentPage = travellerInfoPage.getTotalPrice();
        assertThat("Price didn't match on payment page", priceOnPaymentPage, is(priceOnTravellerInfoPage));
        assertThat("FromAtoB page title didn't display", getDriver().getTitle(), is("fromAtoB"));
        assertThat("Payment page url didn't display", getDriver().getCurrentUrl(), is(containsStringIgnoringCase("payment")));

        int paymentList = paymentPage.getPaymentList();
        for (int i = 0; i < paymentList; i++) {
            String actualPaymentOptions = paymentPage.getPaymentOptions(i);
            assertThat("Payment options didn't display", actualPaymentOptions, is(getPaymentOptionsValue(i)));
        }
    }

    private SearchPage searchForTrain(String departureCity, String arrivalCity) {
        return new HomePage(getDriver())
                .openHomePage()
                .uncheckCompareWithDeutscheBahn()
                .enterDepartureCity(departureCity)
                .enterArrivalCity(arrivalCity)
                .dismissCookieMessage()
                .enterDepartureDate()
                .search();
    }

    private static final ImmutableMap<Integer, String> PAYMENT_OPTIONS = new ImmutableMap.Builder<Integer, String>()
            .put(0, "Pay with credit card")
            .put(1, "Pay with")
            .put(2, "Pay with")
            .build();

    static String getPaymentOptionsValue(int index) {
        return PAYMENT_OPTIONS.get(index);
    }
}
