package tests;

import io.restassured.RestAssured;
import model.lombok.RegisterBody;
import model.lombok.RegisterErrorResponseModel;
import model.lombok.RegisterResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInRegister {

    /*
        1. make POST- request to https://reqres.in/api/register
            with body { "email": "eve.holt@reqres.in", "password": "pistol" }

        2. get response { "id": 4, "token": "QpwL5tke4Pnpja7X4" }

        3. check id & token is "id": 4, "QpwL5tke4Pnpja7X4"

     */

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }


    @Test
    @DisplayName("Проверка, что регистрация успешна и приходит код 200")
    void registerTest() {
        RegisterBody data = new RegisterBody();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

//        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        RegisterResponse registerResponse = given()
                .log().uri()
                .when()
                .contentType(JSON)
                .body(data)
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponse.class);

        assertEquals(4, registerResponse.getId());
        assertEquals("QpwL5tke4Pnpja7X4", registerResponse.getToken());

//        assertThat(registerResponse.getId()).isEqualTo(4);
//        assertThat(registerResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

//                .body("id", is(4), "token", is("QpwL5tke4Pnpja7X4")); \

    }

    @Test
    @DisplayName("Проверка, что регистрация не прошла и приходит код 400")
    void redisterTestUnsuccessfull() {
        RegisterBody data = new RegisterBody();
        data.setEmail("sydney@fife");


//        String data = "{ \"email\": \"sydney@fife\" }";

        RegisterErrorResponseModel registerResponse = given()
                .log().uri()
                .when()
                .contentType(JSON)
                .body(data)
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(RegisterErrorResponseModel.class);

        assertEquals("Missing password", registerResponse.getError());
    }
}
