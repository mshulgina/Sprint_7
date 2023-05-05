package api.client;

import api.model.Authorization;
import api.model.Courier;
import api.util.Constants;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierSteps extends Constants {

    @Step("Отправка запроса на /api/v1/courier")
    public ValidatableResponse getCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(API_COURIER)
                .then();
    }

    @Step("Отправка запроса на /api/v1/courier/login")
    public ValidatableResponse getLoginResponse(Authorization authorization) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .and()
                .body(authorization)
                .when()
                .post(API_LOGIN)
                .then();
    }
}