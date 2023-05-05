package api.util;

import api.model.Courier;
import api.model.Authorization;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Methods extends Constants {

    public void deleteCourier() {
        Authorization authorization = new Authorization(existingLogin, existingPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authorization)
                        .when()
                        .post(API_LOGIN);
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete(API_LOGIN + id);
    }

    public void createCourier() {
        Courier successfulCourier = new Courier(existingLogin, existingPassword, existingFirstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(successfulCourier)
                .when()
                .post(API_COURIER);
    }
}