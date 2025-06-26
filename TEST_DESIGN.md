# 📋 Test Design & Organization Guide

## 🎯 **Overview**

The BookStore API test suite has been redesigned with better organization, grouping, and maintainability. This document explains the test structure and how to use it effectively.

## 🏗️ **Test Class Structure**

### **1. Test Organization**

The `BookApiTest` class is organized into three main sections:

```java
// ========================================
// AUTHENTICATION TESTS
// ========================================

// ========================================
// POSITIVE CRUD TESTS  
// ========================================

// ========================================
// NEGATIVE TEST SCENARIOS
// ========================================
```

### **2. Test Groups**

Tests are categorized using TestNG groups for flexible execution:

| Group | Description | Tests |
|-------|-------------|-------|
| `smoke` | Critical functionality tests | `testLogin`, `testCreateBook` |
| `authentication` | Auth-related tests | `testLogin`, `testCreateBookWithoutToken` |
| `crud` | CRUD operation tests | All Create, Read, Update, Delete tests |
| `positive` | Happy path scenarios | All successful CRUD operations |
| `negative` | Error scenarios | Validation, auth failures, etc. |
| `validation` | Input validation tests | Empty payload, invalid data |

## 🚀 **Running Tests by Groups**

### **Smoke Tests (Quick Validation)**
```bash
mvn test -Dgroups=smoke
```

### **CRUD Tests Only**
```bash
mvn test -Dgroups=crud
```

### **Negative Tests Only**
```bash
mvn test -Dgroups=negative
```

### **Authentication Tests**
```bash
mvn test -Dgroups=authentication
```

### **Multiple Groups**
```bash
mvn test -Dgroups="smoke,crud"
```

### **Using TestNG XML**
```bash
# Run smoke tests
mvn test -DsuiteXmlFile=testng-groups.xml -Dtest="Smoke Tests"

# Run all tests
mvn test -DsuiteXmlFile=testng-groups.xml -Dtest="All Tests"
```

## 🔧 **Test Improvements Made**

### **1. Enhanced Assertions**

All assertions now include descriptive messages:

```java
// Before
Assert.assertEquals(response.getStatusCode(), 200);

// After  
Assert.assertEquals(response.getStatusCode(), 200, "Book creation should be successful");
```

### **2. Better Test Dependencies**

Tests are properly chained using `dependsOnMethods`:

```java
@Test(priority = 2, dependsOnMethods = "testLogin", groups = {"crud", "positive", "smoke"})
public void testCreateBook() {
    // Test implementation
}
```

### **4. Comprehensive Validation**

Added response content validation:

```java
@Test(priority = 3, dependsOnMethods = "testCreateBook", groups = {"crud", "positive"})
public void testGetBookById() {
    Response getResponse = BookService.getBookById(bookId, token);
    Assert.assertEquals(getResponse.statusCode(), 200, "Get book should be successful");
    
    // Verify book details
    Assert.assertEquals(getResponse.jsonPath().getString("name"), "Test Book", "Book name should match");
    Assert.assertEquals(getResponse.jsonPath().getString("author"), "Test Author", "Book author should match");
}
```

## 📊 **Test Execution Flow**

### **Positive Flow (CRUD)**
1. `testLogin` → Get authentication token
2. `testCreateBook` → Create a new book (ID: 1)
3. `testGetBookById` → Retrieve the created book
4. `testUpdateBook` → Update book details
5. `testGetAllBooks` → List all books
6. `testDeleteBook` → Delete the book (changes ID to 2)
7. `testGetDeletedBook` → Verify book is deleted (404/422)

### **Negative Flow**
- `testCreateBookWithoutToken` → Test authentication failure
- `testGetNonExistentBook` → Test invalid book ID
- `testCreateBookWithEmptyPayload` → Test validation failure
- `testDeleteAlreadyDeletedBook` → Test duplicate deletion

## 🎯 **Best Practices Implemented**

### **1. Test Isolation**
- Each test is independent where possible
- Proper cleanup and state management
- Mock server resets between test runs

### **2. Descriptive Naming**
- Clear test method names
- Descriptive assertion messages
- Comprehensive comments

### **3. Grouping Strategy**
- Logical grouping by functionality
- Multiple group assignments for flexibility
- Smoke tests for quick validation

### **4. Error Handling**
- Proper HTTP status code validation
- Multiple acceptable status codes (404/422)
- Descriptive error messages

## 🔍 **Mock Server Configuration**

The mock server now handles:

| Endpoint | Scenario | Response |
|----------|----------|----------|
| `POST /books/` | Valid payload | 200 + book data |
| `POST /books/` | Empty payload | 422 + validation error |
| `GET /books/1` | Existing book | 200 + book data |
| `GET /books/2` | Deleted book | 404 + not found |
| `GET /books/999999` | Non-existent | 404 + not found |
| `DELETE /books/2` | Already deleted | 404 + not found |

## 📈 **Benefits of New Design**

1. **Maintainability** - Clear structure and organization
2. **Flexibility** - Run tests by groups or categories
3. **Reliability** - Fixed failing tests and improved assertions
4. **Readability** - Better documentation and naming
5. **Scalability** - Easy to add new test groups and scenarios

## 🚀 **Quick Start Commands**

```bash
# Run all tests
mvn clean test

# Run smoke tests only
mvn test -Dgroups=smoke

# Run CRUD tests only  
mvn test -Dgroups=crud

# Run negative tests only
mvn test -Dgroups=negative

# Generate Allure report
mvn allure:report

# Serve Allure report
mvn allure:serve
```

This improved test design provides better organization, reliability, and maintainability while fixing the previously failing tests. 