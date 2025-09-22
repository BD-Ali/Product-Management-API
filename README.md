<!-- Project badges -->
<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-REST%20API-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/JPA-PostgreSQL-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/License-MIT-black" alt="MIT">
  <img src="https://img.shields.io/badge/Docs-OpenAPI%2FSwagger-85EA2D?logo=swagger&logoColor=white" alt="Swagger">
</p>

<h1 align="center">🛍️ Product Management API</h1>
<p align="center">
  A clean Spring Boot REST API with DTOs, validation, a standardized response wrapper, and centralized error handling.
</p>

---

## 🔎 Overview

- CRUD + **PATCH** endpoints under <code>/api/products</code>  
- **DTOs** for input/output (entity not exposed): <code>ProductCreate</code>, <code>ProductUpdate</code>, <code>ProductPatch</code>  
- **GlobalResponse&lt;T&gt;** wrapper for consistent <em>success / error</em> shapes  
- **Validation** on inputs and **@ControllerAdvice** for errors  
- **Spring Data JPA** + PostgreSQL driver  
- **OpenAPI/Swagger UI** enabled

---

## 📑 Table of Contents

- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [API Summary](#-api-summary)
- [Request Models (DTOs)](#-request-models-dtos)
- [Response Shape](#-response-shape)
- [Validation & Error Handling](#-validation--error-handling)
- [Run Locally](#-run-locally)
- [OpenAPI / Swagger](#-openapi--swagger)
- [License](#-license)

---

## 🧱 Tech Stack

- **Spring Boot** (Web, Validation, Data JPA)  
- **PostgreSQL** JDBC driver  
- **springdoc-openapi** (Swagger UI)  
- **Maven**

---

## 🗂️ Project Structure

```
src/main/java/com/api/productmanagementapi
├── ProductManagementApiApplication.java
├── controller/
│   └── ProductController.java
├── dtos/
│   ├── ProductCreate.java
│   ├── ProductUpdate.java
│   └── ProductPatch.java
├── entity/
│   └── Product.java
├── repository/
│   └── ProductRepository.java
├── service/
│   ├── ProductService.java
│   └── ProductServiceImpl.java
└── shared/
    ├── CustomResponseException.java
    ├── GlobalExceptionResponse.java
    └── GlobalResponse.java
```

> The <code>shared</code> package includes a generic response wrapper, a global exception handler, and a custom exception type.

---

## 🚦 API Summary

> Base path: <code>/api/products</code>

| Method | Endpoint                | Purpose                    |
|------: |-------------------------|----------------------------|
| GET    | `/api/products`         | List all products          |
| GET    | `/api/products/{id}`    | Get one product by id      |
| POST   | `/api/products`         | Create a product           |
| PUT    | `/api/products/{id}`    | Replace a product          |
| PATCH  | `/api/products/{id}`    | Partially update a product |
| DELETE | `/api/products/{id}`    | Delete a product           |

<details>
<summary><strong>Sample create (POST)</strong></summary>

```http
POST /api/products
Content-Type: application/json

{
  "name": "Laptop",
  "price": 699.99,
  "quantity": 10
}
```
</details>

<details>
<summary><strong>Sample success response</strong></summary>

```json
{
  "status": "SUCCESS",
  "data": {
    "id": 1,
    "name": "Laptop",
    "price": 699.99,
    "quantity": 10
  }
}
```
</details>

---

## 📨 Request Models (DTOs)

```java
// ProductCreate.java
class ProductCreate {
  String name;       // @NotBlank @Size(max = 100)
  BigDecimal price;  // @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2)
  Integer quantity;  // @NotNull @Min(0) @Max(1_000_000)
}
```

```java
// ProductUpdate.java (replace semantics, same constraints as create)
class ProductUpdate {
  String name;
  BigDecimal price;
  Integer quantity;
}
```

```java
// ProductPatch.java (partial update; only non-null fields applied)
class ProductPatch {
  String name;       // if present, must not be blank
  BigDecimal price;  // if present, >= 0
  Integer quantity;  // if present, >= 0
}
```

---

## 📦 Response Shape

All endpoints return **GlobalResponse&lt;T&gt;**:

```json
// success
{
  "status": "SUCCESS",
  "data": { /* payload */ }
}
```
```json
// error
{
  "status": "ERROR",
  "errors": [
    { "message": "Explanation of what went wrong" }
  ]
}
```

---

## 🧯 Validation & Error Handling

Centralized handler **GlobalExceptionResponse** maps common problems to structured errors:

- `CustomResponseException` → HTTP status from the exception (e.g. 404 for missing id)  
- `MethodArgumentNotValidException` / `ConstraintViolationException` → **400 Bad Request** (validation details)  
- `MethodArgumentTypeMismatchException` → **400 Bad Request** (wrong param types)  
- `DataIntegrityViolationException` → **409 Conflict** (DB constraint)  
- Generic `Exception` → **500 Internal Server Error**  

All error payloads use `GlobalResponse` with an `errors` array of `{ "message": "..." }` items.

---

## ▶️ Run Locally

1) Configure datasource in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/storedb
spring.datasource.username=product_user
spring.datasource.password=product_pass
spring.jpa.hibernate.ddl-auto=update
```

2) Start the app:
```bash
./mvnw spring-boot:run
# http://localhost:8080
```

---

## 📘 OpenAPI / Swagger

Once running:

- Swagger UI → `http://localhost:8080/swagger-ui.html`  
- OpenAPI JSON → `http://localhost:8080/v3/api-docs`

---

## 🪪 License

This project is licensed under the **MIT License**.
