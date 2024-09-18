package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import model.lombok.LoginBody;
import model.lombok.LoginResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInLogin {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void loginWithCustomListenerModel() {
        LoginBody data = new LoginBody();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponse response = step("Get authorization data", () ->
                given()
                        .log().uri()
                        .log().headers()
                        .log().body()
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(data)
                        .when()
                        .post("/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponse.class));

        step("Verify authorization response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
//                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));

    }
}
/*
.log().uri()
                .when()
                .contentType(JSON)
                .body(data)

 */