# bookstore-api-tests
# 📘 Bookstore API Automation Framework


## 📝 Overview


This project is a REST API automation framework built to test the core functionalities of a FastAPI-based Bookstore application. It uses **Java**, **RestAssured**, **TestNG**, and **Maven**, and is designed for scalability, maintainability, and CI/CD readiness.


---


## 📚 Table of Contents


- [Features](#-features)
- [Project Structure](#project-structure)
- [Tools & Technologies](#tools--technologies)
- [Setup & Configuration](#setup--configuration)
- [How to Run Tests](#how-to-run-tests)
- [Authentication Strategy](#authentication-strategy)
- [Future Scope](#future-scope)
- [License](#-license)


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
- **FastAPI Server** - Real server implementation for integration testing


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
├── server/ # FastAPI server implementation
│ ├── main.py # FastAPI application
│ ├── requirements.txt # Python dependencies
│ └── README.md # Server setup instructions
├── config.properties # Env configuration
├── testng.xml # TestNG suite configuration
├── pom.xml # Maven config
├── run-tests.bat # Windows test runner
├── run-tests.sh # Unix/Linux test runner
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
| FastAPI      | Python web framework (server)        |
| GitHub       | Version control and collaboration    |
| GitHub Actions | (Coming Soon) CI/CD automation     |


---


## ⚙️ Setup & Configuration


### 1. Prerequisites


- Java 11+
- Maven installed and added to `PATH`
- IDE (Eclipse, IntelliJ, VS Code)
- Python 3.7+ (optional, for FastAPI server)


### 2. Clone the Repository
```bash
git clone git@github.com:Esha-Vij/bookstore-api-tests.git
cd bookstore-api-tests
```


---


## ▶️ How to Run Tests


### Using FastAPI Server


1. **Start the FastAPI server on docker, with :**


2. **Run tests:**
### Using Maven Directly
```bash
mvn test
```


### Using TestNG Suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```


### Using IDE
Right-click the test class and run as TestNG Test


---


## 🔐 Authentication Strategy


- Sign-up & Login are handled automatically in `AuthUtil.java`
- Unique id, email, and password are generated per test run
- Bearer Token is extracted and used for all authorized endpoints
- No manual token handling is required


---


## 🚀 Quick Start


1. **Clone and setup:**
```bash
git clone <repository-url>
cd bookstore-api-tests
```


2. **Run tests:**
```bash
mvn clean test
```


3. **View results:**
- Check `test-output/` directory for TestNG reports
- Check `allure-results/` for Allure reports


---


## 🔧 Troubleshooting


### Connection Refused Error
If you see `java.net.ConnectException: Connection refused`, it means the tests are trying to connect to a server that isn't running.


**Solutions:**
1. Start the FastAPI server before running tests
2. Check if port 8000 is available and not blocked by firewall


### FastAPI Server Issues
- Check if port 8000 is available
- Docker is hosting FastAPI Server on 8000 port.


---


## 📈 Future Scope


- [ ] GitHub Actions CI/CD pipeline
- [ ] Docker containerization
- [ ] Database integration testing
- [ ] Performance testing with JMeter
- [ ] API documentation testing
- [ ] Mobile API testing support


---


## 📄 License


This project is licensed under the MIT License - see the LICENSE file for details.
