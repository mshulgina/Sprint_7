import api.client.OrdersSteps;
import api.model.Order;
import api.util.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Тесты создания заказа")
public class CreateOrderTest extends Constants {
    private final String firstNameValue;
    private final String lastNameValue;
    private final String addressValue;
    private final int metroStationValue;
    private final String phoneValue;
    private final int rentTimeValue;
    private final String deliveryDateValue;
    private final String commentValue;
    private final List<String> colorValue;
    public int track;

    public CreateOrderTest(String firstNameValue, String lastNameValue, String addressValue, int metroStationValue, String phoneValue, int rentTimeValue, String deliveryDateValue, String commentValue, List<String> colorValue) {
        this.firstNameValue = firstNameValue;
        this.lastNameValue = lastNameValue;
        this.addressValue = addressValue;
        this.metroStationValue = metroStationValue;
        this.phoneValue = phoneValue;
        this.rentTimeValue = rentTimeValue;
        this.deliveryDateValue = deliveryDateValue;
        this.commentValue = commentValue;
        this.colorValue = colorValue;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDataCreateOrder() {
        return new Object[][]{
                {"Семен", "Иванов", "Комсомольская 31", 10, "89965472341", 2, "2001-01-07", "1-й подъезд", null},
                {"Андрей", "Сергеев", "Ленина 21", 10, "89944554376", 2, "2002-02-16", "2-й подъезд", List.of("BLACK")},
                {"Сергей", "Андреев", "Пролетарская 41", 10, "87583474376", 2, "2003-03-21", "3-й подъезд", List.of("GREY")},
                {"Иван", "Семенов", "Победы 51", 10, "89962107376", 2, "2004-04-10", "4-й подъезд", List.of("BLACK", "GREY")},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void testCodeAndBody(){
        OrdersSteps ordersSteps = new OrdersSteps();
        ValidatableResponse emptyPasswordField  = ordersSteps.getOrdersResponse(
                new Order(firstNameValue, lastNameValue, addressValue,
                        metroStationValue, phoneValue, rentTimeValue, deliveryDateValue, commentValue, colorValue));
        emptyPasswordField
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        String json = "{\"track\": " + track + "}";
        Response responseCancel =
                given().header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .put("/api/v1/orders/cancel");
        int statusCancel = responseCancel.statusCode();
        if (statusCancel != 200) {
            System.out.println("Не удалось отменить заказ");
        }
    }
}