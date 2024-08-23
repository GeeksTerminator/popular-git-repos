# Popular Git Repos

<hr />

## About

This is a Spring Boot application that integrates with the GitHub Search API to search for Git repositories. The
application fetches repository data, scores them based on popularity metrics (stars, forks, Recency of updates etc.),
and returns them via
a search endpoint. It is designed to provide an easy way to find and rank repositories based on user-defined filters.

## Tech Stack

| Category       | Technology                                  |
|----------------|---------------------------------------------|
| **Languages**  | Java 21                                     |
| **Build Tool** | Maven                                       |
| **Framework**  | Spring Framework, Spring Boot               |
| **Utilities**  | Lombok, MapStruct                           |
| **Testing**    | JUnit 5, Mockito, AssertJ, WireMock, Jacoco |

## Requirements

- **Java 21**: Make sure you have Java 21 installed.
- **Maven**: Maven is required to build and run the application.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/GeeksTerminator/popular-git-repos.git
   cd popular-git-repos
   ```

2. Build the application:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start and run on the default port `8080`.

## API Documentation

The API documentation for this Spring Boot application is available through Swagger. You can access it at the following
URL after starting the application:
[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

Swagger provides a user-friendly interface to explore the available API endpoints and test them directly within the
browser.

## Test Coverage

The project uses Jacoco to generate test coverage reports. After running the tests, the coverage report can be found in
the following location:
`target/site/jacoco/index.html`

Open the `index.html` file in a browser to view the detailed test coverage report, which provides insights into how much
of the code is covered by tests.

## Notes

- **WireMock Server**: During tests, a WireMock server will be running on port `8080` to simulate the GitHub API. Ensure
  that no other services are running on port `8080` while executing the tests with `mvn test`, as it may cause
  conflicts.
