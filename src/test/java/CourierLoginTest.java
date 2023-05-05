import api.client.CourierSteps;
import api.model.Authorization;
import api.util.Methods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты логина курьера")
public class CourierLoginTest extends Methods {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createCourier();
    }

    @Test
    @DisplayName("Курьер входит в систему")
    @Description("Проверка кода статуса, когда курьер входит в систему (200)")
    public void testCourierCanBeAuthorized() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse dataCourier = courierSteps.getLoginResponse(
                new Authorization(existingLogin, existingPassword));
        dataCourier
                .statusCode(200)
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Курьер входит в систему без логина")
    @Description("Проверка кода статуса, когда курьер входит в систему без логина (400)")
    public void testCourierCanBeAuthorizationWithoutLogin() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse dataCourierWithoutLogin = courierSteps.getLoginResponse(
                new Authorization(null, existingPassword));
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер входит в систему без пароля")
    @Description("Проверка кода статуса, когда курьер входит в систему без пароля (400)")
    public void testCourierCanBeAuthorizationWithoutPassword() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse dataCourierWithoutLogin = courierSteps.getLoginResponse(
                new Authorization(existingLogin, ""));
        dataCourierWithoutLogin
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер входит в систему с неправильным логином или паролем")
    @Description("Проверка кода статуса, когда курьер входит в систему с неправильным логином или паролем(404)")
    public void testCourierCanBeAuthorizationWithWrongPasswordOrLogin() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse nonExistentData = courierSteps.getLoginResponse(
                Authorization.getRandomLoginData());
        nonExistentData
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        deleteCourier();
    }
}
