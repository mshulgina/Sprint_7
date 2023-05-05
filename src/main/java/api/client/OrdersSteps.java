package api.client;

import api.model.Order;
import api.util.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersSteps extends Constants {
    @Step("Отправка запроса на список заказов /api/v1/orders")
    public ValidatableResponse getOrdersResponse(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(API_ORDER)
                .then();
    }
}