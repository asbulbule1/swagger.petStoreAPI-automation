<<<<<<< HEAD
# swagger.petStoreAPI-automation
=======
# 🧪 PetStore API Automation Framework

### Overview
This project is a **REST API automation framework** built using **Java, RestAssured, and TestNG**.  
It automates testing of the [Swagger PetStore API](https://petstore.swagger.io/) — covering **CRUD operations**, **data-driven testing**, **schema validation**, and **end-to-end lifecycle validation** of pet entities.

---

## 🚀 Features
- ✅ Modular **framework structure** with reusable components  
- ✅ **Data-driven tests** using Excel (Apache POI)  
- ✅ **End-to-end Pet lifecycle** automation  
- ✅ **Dynamic data management** (create → get → update → delete)  
- ✅ **JSON Schema Validation**  
- ✅ **Custom Logging & Reporting**
  - Extent Reports
  - Allure Reports
- ✅ Configurable via `config.properties`
- ✅ Executable via **TestNG XML suite**

---

## 🧩 Framework Architecture
```
PetStoreAPIAutomation
│
├── pom.xml                    # Maven dependencies
├── testng.xml                 # TestNG suite configuration
├── TestData/
│   └── PetDataAndTestCases.xls            # Input data for data-driven tests
│
├── src/test/java/
│   ├── endpoints/
│   │   └── PetEndpoints.java
│   │
│   ├── pojo/
│   │   └── Pet.java
│   │
│   ├── tests/
│   │   ├── CreatePetTest.java
│   │   ├── GetPetTest.java
│   │   ├── UpdatePetTest.java
│   │   ├── DeletePetTest.java
│   │   ├── FindByStatusTest.java
│   │   └── SchemaValidations.java
│   │
│   ├── dataDrivenTests/
│   │   ├── CreatePetDataDrivenTest.java
│   │   ├── GetPetDataDrivenTest.java
│   │   ├── UpdatePetDataDrivenTest.java
│   │   ├── DeletePetDataDrivenTest.java
│   │   └── FindByStatusDataDrivenTest.java
│   │
│   ├── petLifeCycle/
│   │   └── End2EndLifeCycleTest.java
│   │
│   └── utils/
│       ├── BaseTest.java
│       ├── ConfigReader.java
│       ├── ExcelUtils.java
│       ├── TestDataReader.java
│       └── ExtentReporter.java
│
└── allure-results/
```

---

## ⚙️ Tools and Technologies
| Component | Technology Used |
|------------|-----------------|
| Language | Java |
| Framework | TestNG |
| API Testing | RestAssured |
| Build Tool | Maven |
| Reports | Allure, Extent Reports |
| Data Source | Excel (Apache POI) |
| Logging | Custom Log + Extent Log |
| JSON Schema Validation | org.everit.json.schema |

---

## 🧠 Test Coverage
| Test Category | Description |
|----------------|-------------|
| **CRUD Tests** | Validations for Create, Get, Update, Delete pet APIs |
| **Data-Driven Tests** | Fetch test data from Excel for parameterized execution |
| **Schema Validation** | Ensures API response adheres to expected JSON schema |
| **End-to-End Flow** | Validates pet lifecycle from creation to deletion |

---

## 🧰 Setup and Execution
### Prerequisites
- Java 11+
- Maven 3+
- TestNG plugin (optional if running from CLI)


### Install Dependencies
```bash
mvn clean install
```

### Execute Test Suite
Run all tests defined in **`testng.xml`**
```bash
mvn test
```

### Generate Reports
#### 🧾 Extent Report
```
/reports/ExtentReport.html
```

#### 📊 Allure Report
```bash
allure serve allure-results
```

---

## 🧪 Example Test Flow
**Create Pet → Get Pet → Update Pet → Delete Pet**
1. Create a pet using `/pet` POST API  
2. Fetch pet details using `/pet/{id}` GET API  
3. Update pet details (e.g., status, name)  
4. Delete pet by ID  
5. Validate each step via assertions and schema validations  

---

## 📁 Configuration
All environment-level properties are configurable via:
```
src/test/resources/config.properties
```

Example:
```properties
base.url=https://petstore.swagger.io/v2
```

---

## 📚 Test Data
Located at:
```
TestData/PetDataAndTestCases.xls
```

Each row corresponds to specific test data for CRUD and data-driven tests.

---

## 🧑‍💻 Author
**Abhishek Bulbule**  
QA Automation Engineer | API & UI Automation | Java | Selenium | Playwright | RestAssured

---

## 📈 Future Enhancements
- Integrate **CI/CD pipeline** (GitHub Actions / Jenkins)
- Add **parallel execution support**
- Expand for other modules
>>>>>>> b9b5c18 (first)
