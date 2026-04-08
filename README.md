# Lead Manager QA Automation Framework

Automation framework for the Lead Manager SaaS application covering UI (Selenium) and API (RestAssured) testing with TestNG.

## Tools & Frameworks

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 11 | Language |
| Maven | 3.6+ | Build & dependency management |
| TestNG | 7.8.0 | Test runner, parallel execution, suite management |
| Selenium | 4.25.0 | UI browser automation |
| RestAssured | 5.3.2 | API testing |
| JavaFaker | 1.0.2 | Dynamic test data generation |
| Gson | 2.10.1 | JSON serialization |

## Setup

### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser (for UI tests)

### Installation
```bash
git clone https://github.com/automationchef007/osmos-qa-lead.git
cd osmos-qa-lead
mvn clean install -DskipTests
```

### Configuration
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

## Running Tests

| Command | Description |
|---------|-------------|
| `mvn clean test` | Run all tests (UI + API) |
| `mvn clean test -Papi` | Run only API tests |
| `mvn clean test -Pui` | Run only UI tests |
| `mvn clean test -Dbrowser=firefox` | Run with Firefox browser |
| `mvn clean test -Papi -Dapi.base.url=https://staging.example.com/api` | Run API tests against a different environment |


## Project Structure

```
src/
├── main/java/com/osmos/qa/
│   ├── applicationpages/   # Page Object Model (LoginPage, DashboardPage, CreateLeadPage)
│   ├── base/               # BasePage — shared UI utilities
│   ├── config/             # ConfigManager — environment configuration
│   ├── driverManager/      # DriverFactory (thread-safe), Chrome/Firefox managers
│   ├── models/             # Lead POJO with Builder pattern
│   └── utils/              # TestDataProvider — Faker-based test data
└── test/
    ├── java/com/osmos/qa/
    │   ├── base/
    │   │   ├── BaseTest.java       # UI test base (driver lifecycle)
    │   │   └── BaseApiTest.java    # API test base (auth, request/response specs)
    │   └── tests/
    │       ├── ui/                 # UI test classes
    │       └── api/                # API test classes
    └── resources/
        ├── config.properties       # Environment config
        ├── testng.xml              # Full suite — all tests (default)
        ├── testng-api.xml          # API-only suite
        └── testng-ui.xml           # UI-only suite
```

## Test Coverage

### UI Tests — CreateLeadE2ETest
| Test | Flow |
|------|------|
| `testLoginCreateLeadAndVerifyInList` | Login → Create Lead → Verify popup closes → Verify lead in dashboard list |

### API Tests — LoginApiTest
| Test | Scenario |
|------|----------|
| `testLoginSuccess` | Valid credentials return token matching `Bearer_[A-Z0-9_]+` pattern |
| `testLoginInvalidCredentials` | Wrong credentials return 401 |
| `testLoginEmptyEmail` | Empty email returns 400 validation error |
| `testLoginEmptyPassword` | Empty password returns validation error |

### API Tests — LeadsApiTest
| Test | Scenario |
|------|----------|
| `testGetLeadsSuccess` | Authenticated GET returns paginated leads list with schema validation |
| `testGetLeadsPaginationPage1` | Page 1 returns max pageSize (10) leads |
| `testGetLeadsPaginationPage2` | Page 2 returns next set with correct pagination metadata |
| `testGetLeadsWithoutAuth` | Missing auth header returns 401 |
| `testGetLeadsInvalidToken` | Invalid token returns 401 |
| `testCreateLeadSuccess` | Valid lead creation returns 201 with lead data |
| `testCreateLeadMissingName` | Missing name returns 400 with validation error message |
| `testCreateLeadWithoutAuth` | Unauthenticated create returns 401 |
| `testCreateHighPriorityLead` | High priority qualified lead created successfully |

## Design Patterns
- **Page Object Model** — UI interactions encapsulated per page
- **Factory Pattern** — `DriverFactory` for browser creation, `TestDataProvider` for test data
- **Builder Pattern** — `Lead` model for flexible object construction
- **Strategy Pattern** — `BrowserDriver` interface with Chrome/Firefox implementations
- **Thread-safe parallel execution** — `ThreadLocal<WebDriver>` in DriverFactory
