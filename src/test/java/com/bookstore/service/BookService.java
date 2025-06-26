//package com.bookstore.service;
//
//import com.bookstore.payloads.BookPayload;
//import com.bookstore.config.ConfigManager;
//import com.bookstore.utils.AuthUtil;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//
//import static io.restassured.RestAssured.given;
//
//public class BookService {
//
//    private static final String BASE_URL = ConfigManager.getBaseUrl();
//    private static final String TOKEN = AuthUtil.getBearerToken();
//
//    public static Response createBook(BookPayload book) {
//        return given()
//                .baseUri(BASE_URL)
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + TOKEN)
//                .body(book)
//                .when()
//                .post("/books/")
//                .then()
//                .log().all()
//                .extract().response();
//    }
//
//    public static Response getBookById(int bookId) {
//        return given()
//                .baseUri(BASE_URL)
//                .header("Authorization", "Bearer " + TOKEN)
//                .when()
//                .get("/books/" + bookId)
//                .then()
//                .log().all()
//                .extract().response();
//    }
//
//    public static Response updateBook(int bookId, BookPayload updatedBook) {
//        return given()
//                .baseUri(BASE_URL)
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + TOKEN)
//                .body(updatedBook)
//                .when()
//                .put("/books/" + bookId)
//                .then()
//                .log().all()
//                .extract().response();
//    }
//
//    public static Response deleteBook(int bookId) {
//        return given()
//                .baseUri(BASE_URL)
//                .header("Authorization", "Bearer " + TOKEN)
//                .when()
//                .delete("/books/" + bookId)
//                .then()
//                .log().all()
//                .extract().response();
//    }
//    
//    public static Response getAllBooks() {
//        return given()
//                .baseUri(BASE_URL)
//                .header("Authorization", "Bearer " + TOKEN)
//                .when()
//                .get("/books/")
//                .then()
//                .log().all()
//                .extract().response();
//    }
//
//}



package com.bookstore.service;

import com.bookstore.payloads.BookPayload;
import com.bookstore.config.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookService {

    private static final String BASE_URL = ConfigManager.getBaseUrl();
    public static Response createBook(BookPayload book, String token) {
       // System.out.println(book != null ? book.toString() : "Book payload is null");

        RequestSpecification request = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);

        if (token != null && !token.isEmpty()) {
            request.header("Authorization", token);
        }

        if (book != null) {
            request.body(book);
        }

        return request
                .log().all()
                .when()
                .post("/books/")
                .then()
                .log().all()
                .extract().response();
    }
    
    public static Response getBookById(int bookId, String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", token) // ✅ fix
                .when()
                .get("/books/" + bookId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateBook(int bookId, BookPayload updatedBook, String token) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(updatedBook)
                .when()
                .put("/books/" + bookId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteBook(int bookId, String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .when()
                .delete("/books/" + bookId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getAllBooks(String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .when()
                .get("/books/")
                .then()
                .log().all()
                .extract().response();
    }
}







