# Mocking API Backend

A self-hosted Spring Boot application designed to help frontend developers and QA engineers simulate backend APIs. This project allows you to quickly create, manage, and route mock REST endpoints, making it easier to test applications without relying on a real backend. 

## Features

* **Collection Management:** Group your mock APIs logically into collections (folders) for better organization.
* **Dynamic API Mocking:** Create custom endpoints by defining the HTTP method, exact path, status code, and JSON response body.
* **Chaos Engineering Engine:** Test how your frontend handles bad network conditions. You can configure endpoints to have artificial latency (e.g., a 2000ms delay) or a specific error rate (e.g., a 50% chance to return a 500 error), easily add new Chaos by extending `ChaosStrategy`.
* **Search and Filter:** Quickly find specific endpoints across your workspace using dynamic JPQL filtering (by keyword, HTTP method, or collection ID).
* **Multi-tenant Security:** Secured with JWT. Each user has an isolated workspace and can only manage and route to their own mock APIs.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:
* Java 17 or higher
* Maven 3.6+
* A relational database (MySQL) configured in your `application.properties`

## How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/mituc24/mocking-api-backend.git](https://github.com/mituc24/mocking-api-backend.git)
   cd mocking-api-backend

2. **Build the project:**
    ```bash
    mvn clean install

3. **Run the application:**
    ```bash
    mvn spring-boot:run

The server will start on http://localhost:8080

## How to use the Mocking Routing
Once you have created an account, a collection, and a mock configuration via the admin APIs, you can trigger your mocked responses using the public routing filter.

Simply prefix your configured path with `/mock/{your_username}`

For example, if user `mituc24` creates a `GET` mock for `/api/products`, the frontend can call it at:
`GET http://localhost:8080/mock/mituc24/api/products`   
