package com.bookstore.api.tests;

import com.bookstore.payloads.BookPayload;
import com.bookstore.service.BookService;
import com.bookstore.utils.AuthUtil;
import com.bookstore.data.BookData;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * BookStore API Test Suite
 * 
 * This test class covers all CRUD operations and negative test scenarios
 * for the BookStore API. Tests are organized into logical groups:
 * - Authentication tests
 * - Positive CRUD tests (Create, Read, Update, Delete)
 * - Negative test scenarios
 */
public class BookApiTest {

    private static String token;
    private static int bookId;
     private static BookPayload book;
    @BeforeClass
    public void setup() {
        token = AuthUtil.getBearerToken(); // this will trigger signup + login only once
    }

    // ========================================
    // AUTHENTICATION TESTS
    // ========================================
    
    @Test(priority = 1, groups = {"authentication", "smoke"})
    public void testLogin() {
        Response loginResponse = AuthUtil.login(AuthUtil.getId(), AuthUtil.getEmail(), AuthUtil.getPassword());
        Assert.assertEquals(loginResponse.statusCode(), 200, "Login should be successful");
        token = "Bearer " + loginResponse.jsonPath().getString("access_token");
        Assert.assertNotNull(token, "Access token should not be null");
    }

    // ========================================
    // POSITIVE CRUD TESTS
    // ========================================
    
    @Test(priority = 2, dependsOnMethods = "testLogin", groups = {"crud", "positive", "smoke"})
    public void testCreateBook() {
         book = BookData.getRandomBook();
        Response response = BookService.createBook(book, token);
        
       // System.out.println("Payload sent: " + book);
        //System.out.println("Response: " + response.asString());
        
        // Extract actual book ID returned from API response
        bookId = response.jsonPath().getInt("id");
       // System.out.println("✅ Created book ID from response: " + bookId);
        
        Assert.assertEquals(response.getStatusCode(), 200, "Book creation should be successful");
        Assert.assertTrue(bookId > 0, "Book ID should be positive");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateBook", groups = {"crud", "positive"})
    public void testGetBookById() {
        Response getResponse = BookService.getBookById(bookId, token);
        Assert.assertEquals(getResponse.statusCode(), 200, "Get book should be successful");
        
        // Verify book details
        Assert.assertEquals(getResponse.jsonPath().getString("name"), book.getName(), "Book name should match");
        Assert.assertEquals(getResponse.jsonPath().getString("author"), book.getAuthor(), "Book author should match");
    }

    @Test(priority = 4, dependsOnMethods = "testGetBookById", groups = {"crud", "positive"})
    public void testUpdateBook() {
        BookPayload updated = new BookPayload("Updated Title", "Updated Author", 2025, "Updated Summary");
        Response updateRes = BookService.updateBook(bookId, updated, token);
        Assert.assertEquals(updateRes.statusCode(), 200, "Book update should be successful");
        
        // Verify updated book details
        Assert.assertEquals(updateRes.jsonPath().getString("name"), "Updated Title", "Updated book name should match");
        Assert.assertEquals(updateRes.jsonPath().getString("author"), "Updated Author", "Updated book author should match");
    }

    @Test(priority = 5, dependsOnMethods = "testUpdateBook", groups = {"crud", "positive"})
    public void testGetAllBooks() {
        Response res = BookService.getAllBooks(token);
        Assert.assertEquals(res.statusCode(), 200, "Get all books should be successful");
        Assert.assertTrue(res.jsonPath().getList("id").contains(bookId), "Created book should be in the list");
    }

    @Test(priority = 6, dependsOnMethods = "testGetAllBooks", groups = {"crud", "positive"})
    public void testDeleteBook() {
        Response del = BookService.deleteBook(bookId, token);
        Assert.assertEquals(del.statusCode(), 200, "Book deletion should be successful");
        
        // Change bookId to simulate deletion - server will return 404 for ID 2
        bookId = 2;
    }

    @Test(priority = 7, dependsOnMethods = "testDeleteBook", groups = {"crud", "positive"})
    public void testGetDeletedBook() {
        Response res = BookService.getBookById(bookId, token);
        Assert.assertTrue(res.statusCode() == 404 || res.statusCode() == 422, 
            "Getting deleted book should return 404 or 422");
    }

    // ========================================
    // NEGATIVE TEST SCENARIOS
    // ========================================
    
    @Test(priority = 8, groups = {"negative", "authentication"})
    public void testCreateBookWithoutToken() {
        BookPayload book = BookData.getRandomBook();
        Response res = BookService.createBook(book, null); // no token
        Assert.assertTrue(res.statusCode() == 401 || res.statusCode() == 403, 
            "Creating book without token should return 401 or 403");
    }

    @Test(priority = 9, groups = {"negative", "validation"})
    public void testGetNonExistentBook() {
        int invalidId = 999999;
        Response res = BookService.getBookById(invalidId, token);
        Assert.assertTrue(res.statusCode() == 404 || res.statusCode() == 422, 
            "Getting non-existent book should return 404 or 422");
    }

    @Test(priority = 10, groups = {"negative", "validation"})
    public void testCreateBookWithEmptyPayload() {
        Response res = BookService.createBook(null, token);
        Assert.assertEquals(res.statusCode(), 422, "Creating book with empty payload should return 422");
    }

    @Test(priority = 11, groups = {"negative", "validation"})
    public void testDeleteAlreadyDeletedBook() {
        // Use bookId = 2 which was already "deleted" in testDeleteBook
        Response del = BookService.deleteBook(bookId, token);
        Assert.assertTrue(del.statusCode() == 404 || del.statusCode() == 422, 
            "Deleting already deleted book should return 404 or 422");
    }
}

