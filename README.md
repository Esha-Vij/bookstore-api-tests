# bookstore-api-tests
# 📘 Bookstore API Automation Framework

## 📝 Overview

This project is a REST API automation framework built to test the core functionalities of a FastAPI-based Bookstore application. It uses **Java**, **RestAssured**, **TestNG**, and **Maven**, and is designed for scalability, maintainability, and CI/CD readiness.

---

## 📚 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Tools & Technologies](#tools--technologies)
- [Setup & Configuration](#setup--configuration)
- [How to Run Tests](#how-to-run-tests)
- [Authentication Strategy](#authentication-strategy)
- [Future Scope](#future-scope)
- [License](#license)

---

## ✅ Features

- Full CRUD API test coverage (Create, Read, Update, Delete)
- Request chaining: signup → login → create → get → update → delete
- Positive and negative test case coverage
- Token-based authentication (auto-handled)
- Environment-based configuration with `config.properties`
- TestNG for test execution
- Maven for dependency management and test lifecycle
- Ready for GitHub Actions CI/CD (coming next)

---

## 🧾 Project Structure

bookstore-api-tests/
├── src/
│ ├── main/
│ │ └── java/com/bookstore/
│ │ ├── config/ # ConfigManager for base URL
│ │ └── utils/ # AuthUtil for signup, login, and token
│ └── test/
│ └── java/com/bookstore/api/
│ └── tests/ # TestNG test cases (Book API)
├── config.properties # Env configuration
├── testng.xml # TestNG suite configuration
├── pom.xml # Maven config
├── .gitignore # Files to ignore in Git
├── README.md # Project documentation


---

## 🧰 Tools & Technologies

| Tool         | Description                          |
|--------------|--------------------------------------|
| Java         | Programming language                 |
| RestAssured  | API testing library                  |
| TestNG       | Test framework                       |
| Maven        | Build & dependency management        |
| GitHub       | Version control and collaboration    |
| GitHub Actions | (Coming Soon) CI/CD automation     |

---

## ⚙️ Setup & Configuration

### 1. Prerequisites

- Java 11+
- Maven installed and added to `PATH`
- IDE (Eclipse, IntelliJ, VS Code)

### 2. Clone the Repository
git clone git@github.com:Esha-Vij/bookstore-api-tests.git
cd bookstore-api-tests
▶️ How to Run Tests
Using Maven
mvn test
Using TestNG Suite
mvn test -DsuiteXmlFile=testng.xml
Using IDE
Right-click the test class and run as TestNG Test

🔐 Authentication Strategy
Sign-up & Login are handled automatically in AuthUtil.java

Unique id, email, and password are generated per test run

Bearer Token is extracted and used for all authorized endpoints

No manual token handling is required

