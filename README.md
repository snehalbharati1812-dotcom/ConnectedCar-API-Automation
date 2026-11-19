# ConnectedCar API Automation

Automated API testing framework built with **Java 21**, **REST Assured**, **TestNG**, **Jackson (POJO)**, **JSON Schema Validation**, and **GitHub Actions CI/CD** for validating connected-vehicle telemetry and post endpoints.

## Tech Stack & Tools

- **Language:** Java 21
- **API Testing:** REST Assured
- **Test Runner:** TestNG (`testng.xml` + Maven Surefire)
- **Serialization / Deserialization:** Jackson (POJOs)
- **Contract / Schema Validation:** JSON Schema Validator (`schemas/post-schema.json`)
- **Build Tool:** Apache Maven
- **CI/CD:** GitHub Actions (`.github/workflows/api-tests.yml`)
- **Version Control:** Git & GitHub

## Project Structure

```text
ConnectedCar-API-Automation/
├── .github/
│   └── workflows/
│       └── api-tests.yml          # GitHub Actions CI workflow (triggers on push, PR, manual)
├── src/
│   └── test/
│       ├── java/                  # REST Assured test classes & POJOs
│       └── resources/
│           └── schemas/
│               └── post-schema.json
├── pom.xml                        # Maven dependencies & Surefire configuration
├── testng.xml                     # TestNG test suite definition
└── README.md