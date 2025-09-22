# Product Management API

A Spring Boot REST API for managing products with a consistent response wrapper, validation, DTO-based input models, and centralized error handling. The API exposes CRUD + PATCH endpoints under `/api/products` and persists data with Spring Data JPA (PostgreSQL driver included). OpenAPI/Swagger UI is enabled.

## Tech stack
- Spring Boot (Web, Validation, Data JPA)
- PostgreSQL driver
- Springdoc OpenAPI UI
- Spring Boot Actuator

## Project structure
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
> **Note**: The `shared` package contains three files: a generic response wrapper, the global exception handler, and a custom exception type used for domain errors.

## API endpoints (base path: `/api/products`)

| Method | Endpoint               | Description               | Request body           | Response body             |
|-------:|------------------------|---------------------------|------------------------|---------------------------|
| GET    | `/api/products`        | List all products         | –                      | `GlobalResponse<List<Product>>` |
| GET    | `/api/products/{id}`   | Get product by id         | –                      | `GlobalResponse<Product>` |
| POST   | `/api/products`        | Create a product          | `ProductCreate`        | `GlobalResponse<Product>` |
| PUT    | `/api/products/{id}`   | Replace a product         | `ProductUpdate`        | `GlobalResponse<Product>` |
| PATCH  | `/api/products/{id}`   | Partially update product  | `ProductPatch`         | `GlobalResponse<Product>` |
| DELETE | `/api/products/{id}`   | Delete a product          | –                      | `GlobalResponse<{message}>` |

### Request models (DTOs)

- **`ProductCreate`**
  - `name` — required; not blank; max 100 chars
  - `price` — required; decimal ≥ 0; up to 10 integer digits and 2 fraction digits
  - `quantity` — required; integer ≥ 0 and ≤ 1,000,000
  - Method: `toEntity()` → `Product`

- **`ProductUpdate`**
  - Same constraints as `ProductCreate`
  - Method: `applyTo(Product target)`

- **`ProductPatch`** (all fields optional; only apply non-null)
  - `name` — if present, must not be blank
  - `price` — if present, decimal ≥ 0
  - `quantity` — if present, integer ≥ 0
  - Method: `applyPartially(Product target)`

### Entity
- **`Product`**
  - Fields: `id`, `name`, `price`, `quantity`

## Response format

All endpoints return a `GlobalResponse<T>`:

```json
// success
{
  "status": "SUCCESS",
  "data": { /* T */ }
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

Utility factory methods:
- `GlobalResponse.success(data)`
- `GlobalResponse.successMessage("...")`

## Validation & error handling

Centralized handler (`GlobalExceptionResponse`) maps common problems to structured error responses:
- `CustomResponseException` → HTTP status from the exception (e.g., not found for missing resources)
- `MethodArgumentNotValidException` (body validation errors) → `400 Bad Request`
- `ConstraintViolationException` (path/query validations) → `400 Bad Request`
- `MethodArgumentTypeMismatchException` (wrong param types) → `400 Bad Request`
- `DataIntegrityViolationException` (DB conflicts) → `409 Conflict`
- Generic `Exception` → `500 Internal Server Error`

All error responses use `GlobalResponse` with an `errors` array of `{ message }` items.

## OpenAPI / Swagger

Springdoc is included. Once the app is running, the documentation UI is available at:

- `http://localhost:8080/swagger-ui.html` (redirects to `/swagger-ui/index.html`)
- Raw spec: `http://localhost:8080/v3/api-docs`

## Running locally

Using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

> The project includes the PostgreSQL JDBC driver; configure your datasource in `application.properties` as appropriate for your environment.
