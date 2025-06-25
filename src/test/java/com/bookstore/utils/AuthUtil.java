package com.bookstore.utils;

import com.bookstore.config.ConfigManager;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class AuthUtil {

    private static String email;
    private static String password;
    private static String token;
    private static int id;

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }
    public static int getId() {
        return id;
    }


    public static String getBearerToken() {
        if (token == null) {
            generateCredentialsAndLogin();
        }
        return token;
    }

    private static void generateCredentialsAndLogin() {
        id = generateRandomId();
        email = generateRandomEmail();
        password = generateRandomPassword();

        // Signup
        Response signUpResponse = given()
            .baseUri(ConfigManager.getBaseUrl())
            .contentType("application/json")
            .body(String.format("{\"id\": %d, \"email\": \"%s\", \"password\": \"%s\"}", id, email, password))
            .when()
            .post("/signup")
            .then().log().all()
            .extract().response();
        

        System.out.println("Signup response: " + signUpResponse.asString());
        System.out.println("Used email: " + email);
        System.out.println("Used password: " + password);
        System.out.println("Used ID: " + id);
        
        // ⏳ Wait briefly to ensure signup is fully processed by backend
        try {
            Thread.sleep(1000); // 500 milliseconds = 0.5 second
        } catch (InterruptedException e) {
            e.printStackTrace(); // optional: log error
        }

        // Login with same data
        Response loginResponse = login(id, email, password);
        System.out.println("Login response: " + loginResponse.asString());

        token = loginResponse.jsonPath().getString("access_token");
    }

    public static Response login(int id, String email, String password) {
        return given()
                .baseUri(ConfigManager.getBaseUrl())
                .contentType("application/json")
                .body(String.format("{\"id\": %d, \"email\":\"%s\",\"password\":\"%s\"}",id, email, password))
                .when()
                .post("/login")
                .then().log().all()
                .extract().response();
    }

    private static String generateRandomEmail() {
        return "user" + System.nanoTime() + "@test.com";
    }

    private static String generateRandomPassword() {
        return "Pass@" + new Random().nextInt(99999);
    }

    private static int generateRandomId() {
        return new Random().nextInt(100000); // avoid reusing ID
    }
}
