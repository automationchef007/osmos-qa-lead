# Lead Manager QA Automation Framework

## Overview
Automation framework for the Lead Manager SaaS application covering UI (Selenium) and API (RestAssured) testing with TestNG.

## Architecture

```
src/
├── main/java/com/osmos/qa/
│   ├── api/              # API client layer (RestAssured)
│   ├── config/           # Environment configuration manager
│   ├── core/             # BasePage with shared UI utilities
│   ├── factory/          # DriverFactory (thread-safe) + LeadFactory (test data)
│   ├── models/           # POJOs (Lead)
│   └── pages/            # Page Object Model classes
└── test/
    ├── java/com/osmos/qa/tests/
    │   ├── BaseTest.java       # UI test base (driver lifecycle)
    │   ├── BaseApiTest.java    # API test base (auth setup)
    │   ├── ui/                 # UI test classes
    │   └── api/                # API test classes
    └── resources/
        ├── config.properties   # Environment config
        └── testng.xml          # Suite definition
```

## Design Patterns
- **Page Object Model** — UI interactions encapsulated per page
- **Factory Pattern** — `DriverFactory` for browser creation, `LeadFactory` for test data
- **Thread-safe parallel execution** — `ThreadLocal<WebDriver>` in DriverFactory
- **Layered architecture** — Separation of test, page, API, and config layers

## Tools & Frameworks
| Tool | Purpose |
|------|---------|
| Java 11 | Language |
| Maven | Build & dependency management |
| TestNG | Test runner, parallel execution, suite management |
| Selenium 4 | UI automation |
| WebDriverManager | Automatic browser driver management |
| RestAssured | API testing |

## Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser (for UI tests)

## Setup
```bash
git clone <repo-url>
cd osmos-qa-lead
mvn clean install -DskipTests
```

## Running Tests

### Run all tests (UI + API)
```bash
mvn clean test
```

### Run only API tests
```bash
mvn clean test -Dtest="com.osmos.qa.tests.api.*"
```

### Run only UI tests
```bash
mvn clean test -Dtest="com.osmos.qa.tests.ui.*"
```

### Run with a different browser
```bash
mvn clean test -Dbrowser=firefox
```

### Run with a different environment
```bash
mvn clean test -Dbase.url=https://staging.example.com -Dapi.base.url=https://staging.example.com/api
```

## Test Coverage

### UI Tests
| Test | Flow |
|------|------|
| `CreateLeadE2ETest` | Login → Create Lead → Verify lead in dashboard list |

### API Tests — Login
| Test | Scenario |
|------|----------|
| `testLoginSuccess` | Valid credentials return token |
| `testLoginInvalidCredentials` | Wrong credentials return 401 |
| `testLoginEmptyEmail` | Empty email rejected |
| `testLoginEmptyPassword` | Empty password rejected |

### API Tests — Leads
| Test | Scenario |
|------|----------|
| `testGetLeadsSuccess` | Authenticated GET returns paginated leads |
| `testGetLeadsWithoutAuth` | Missing auth header returns 401 |
| `testGetLeadsInvalidToken` | Invalid token returns 401 |
| `testCreateLeadSuccess` | Valid lead creation returns 201 with lead data |
| `testCreateLeadMissingName` | Missing name returns 400 validation error |
| `testCreateLeadWithoutAuth` | Unauthenticated create returns 401 |
| `testCreateHighPriorityLead` | High priority lead created successfully |

## Configuration
Edit `src/test/resources/config.properties`:
```properties
base.url=https://v0-lead-manager-app.vercel.app
api.base.url=https://v0-lead-manager-app.vercel.app/api
browser=chrome
default.email=admin@company.com
default.password=Admin@123
implicit.wait=10
explicit.wait=15
```

All properties can be overridden via `-D` system properties at runtime.
