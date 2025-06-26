package com.bookstore.api.tests;

import com.bookstore.payloads.BookPayload;
import com.bookstore.service.BookService;
import com.bookstore.utils.AuthUtil;
import com.bookstore.data.BookData;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookApiTest {

    private static String token;
    private static int bookId;
    
    @BeforeClass
    public void setup() {
        // Ensure signup and login are done once before tests
        token = AuthUtil.getBearerToken(); // this will trigger signup + login only once

    }

    @Test(priority = 1)
    public void testLogin() {
    	Response loginResponse = AuthUtil.login(AuthUtil.getId(), AuthUtil.getEmail(),AuthUtil.getPassword());
        Assert.assertEquals(loginResponse.statusCode(), 200);
        token = "Bearer " + loginResponse.jsonPath().getString("access_token");
        Assert.assertNotNull(token);
    }

    @Test(priority = 2, dependsOnMethods = "testLogin")
    public void testCreateBook() {
        BookPayload book = BookData.getRandomBook();
        Response response = BookService.createBook(book, token);
        
        System.out.println("Payload sent: " + book);
        System.out.println("Response: " + response.asString());
        
     // ✅ Extract actual book ID returned from API response
        bookId = response.jsonPath().getInt("id");
        System.out.println("✅ Created book ID from response: " + bookId);  // <<== Add this line
        
        Assert.assertEquals(response.getStatusCode(), 200);
        bookId = response.jsonPath().getInt("id");
        Assert.assertTrue(bookId > 0);
    }

    @Test(priority = 3, dependsOnMethods = "testCreateBook")
    public void testGetBookById() {
        Response getResponse = BookService.getBookById(bookId, token);
        Assert.assertEquals(getResponse.statusCode(), 200);
    }

    @Test(priority = 4, dependsOnMethods = "testGetBookById")
    public void testUpdateBook() {
        BookPayload updated = new BookPayload("Updated Title", "Updated Author", 2025, "Updated Summary");
        Response updateRes = BookService.updateBook(bookId, updated, token);
        Assert.assertEquals(updateRes.statusCode(), 200);
    }

    @Test(priority = 5, dependsOnMethods = "testUpdateBook")
    public void testGetAllBooks() {
        Response res = BookService.getAllBooks(token);
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(res.jsonPath().getList("id").contains(bookId));
    }

    @Test(priority = 6, dependsOnMethods = "testGetAllBooks")
    public void testDeleteBook() {
        Response del = BookService.deleteBook(bookId, token);
        Assert.assertEquals(del.statusCode(), 200);
    }

    @Test(priority = 7, dependsOnMethods = "testDeleteBook")
    public void testGetDeletedBook() {
        Response res = BookService.getBookById(bookId, token);
        Assert.assertTrue(res.statusCode() == 404 || res.statusCode() == 422);
    }

    // NEGATIVE TESTS

    @Test(priority = 8)
    public void testCreateBookWithoutToken() {
        BookPayload book = BookData.getRandomBook();
        Response res = BookService.createBook(book, null); // no token
        Assert.assertTrue(res.statusCode() == 401 || res.statusCode() == 403);
    }

//    @Test(priority = 9)
//    public void testGetNonExistentBook() {
//        int invalidId = 999999;
//        Response res = BookService.getBookById(invalidId, token);
//        Assert.assertTrue(res.statusCode() == 404 || res.statusCode() == 422);
//    }
//
//    @Test(priority = 10)
//    public void testCreateBookWithEmptyPayload() {
//    	BookPayload emptyBook = new BookPayload();
//        Response res = BookService.createBook(emptyBook, token);
//        Assert.assertEquals(res.statusCode(), 422);
//    }
//    
//    @Test(priority = 11)
//    public void testDeleteAlreadyDeletedBook() {
//        Response del = BookService.deleteBook(bookId, token);
//        Assert.assertTrue(del.statusCode() == 404 || del.statusCode() == 422);
//    }
}

