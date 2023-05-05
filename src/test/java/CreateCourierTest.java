import api.client.CourierSteps;
import api.model.Courier;
import api.util.Methods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты создания курьера")
public class CreateCourierTest extends Methods {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        createCourier();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка кода статуса и значение поля для /api/v1/courier(201)")
    public void courierCreatedTest() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse newLogin  = courierSteps.getCourierResponse(
                Courier.getRandomCourier());
        newLogin
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка кода статуса и наличие сообщения при создании двух одинаковых курьеров(409)")
    public void creatingTwoIdenticalCouriersTest() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse duplicateLogin  = courierSteps.getCourierResponse(
                new Courier(existingLogin, existingPassword, existingFirstName));
        duplicateLogin
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка кода статуса и наличие сообщения при создании курьера без поля login(400)")
    public void courierCreateWithoutLoginTest() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse emptyLoginField  = courierSteps.getCourierResponse(
                new Courier(null, existingPassword, existingFirstName));
        emptyLoginField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка кода статуса и наличие сообщения при создании курьера без поля password(400)")
    public void courierCreateWithoutPasswordTest() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse emptyPasswordField  = courierSteps.getCourierResponse(
                new Courier(existingLogin, null, existingFirstName));
        emptyPasswordField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без поля без firstName")
    @Description("Проверка кода статуса при успешном создании курьера без без поля firstName(201)")
    public void courierCreateWithoutFirstNameTest() {
        CourierSteps courierSteps = new CourierSteps();
        ValidatableResponse newLogin  = courierSteps.getCourierResponse(
                Courier.getRandomCourierWithoutFirstName());
        newLogin
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown(){
        deleteCourier();
    }
}