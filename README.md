# Product Management API

A simple Spring Boot REST API for managing products with standardized responses and validation. The API provides endpoints to create, read, update and delete products stored in a PostgreSQL database.

## Features

- RESTful endpoints under `/api/products`
- CRUD operations on products: list, retrieve by id, create, update, delete
- Data persisted to PostgreSQL using Spring Data JPA
- Entity class `Product` with fields: id, name, price, quantity
- Service layer with business logic
- **Consistent response format** using a generic `GlobalResponse<T>` wrapper with `status`, `data` and `errors`
- **Validation rules** enforced on product fields to ensure data integrity (e.g., name must not be blank, price must be non‑negative, quantity within 0‑1,000,000)
- **Centralized exception handling** returning appropriate HTTP status codes and structured error messages

## Getting Started

### Prerequisites

- Java 17+ (adjust `maven.compiler.target` and `maven.compiler.source` in `pom.xml` if needed)
- Maven 3.8+
- A running PostgreSQL instance

### Database Setup

Create a PostgreSQL database and user matching the settings in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/storedb
spring.datasource.username=product_user
spring.datasource.password=product_pass
spring.jpa.hibernate.ddl-auto=update
```

Either update these properties to match your environment or create the `storedb` database and user:

```sql
CREATE DATABASE storedb;
CREATE USER product_user WITH PASSWORD 'product_pass';
GRANT ALL PRIVILEGES ON DATABASE storedb TO product_user;
```

### Running the Application

Clone the repository and build the project:

```bash
git clone https://github.com/BD-Ali/Product-Management-API.git
cd Product-Management-API
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080`.

### Using the API

Base path: `/api/products`

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update an existing product |
| DELETE | `/api/products/{id}` | Delete a product |

Sample JSON body for creating/updating a product:

```json
{
  "name": "Laptop",
  "price": 699.99,
  "quantity": 10
}
```

## Response Format

All endpoints return responses wrapped in a `GlobalResponse<T>` object. The wrapper has the following JSON structure:

- `status` (string): "SUCCESS" for successful requests or "ERROR" if an error occurred
- `data`: the response payload when `status` is "SUCCESS"
- `errors`: an array of error messages when `status` is "ERROR"

Examples:

**Success response** (creating a product):

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

**Error response** (requesting a non‑existent product):

```json
{
  "status": "ERROR",
  "errors": [
    "Product with id 999 not found"
  ]
}
```

## Validation

The `Product` entity uses Bean Validation annotations to enforce constraints:

- `name` must not be blank and may contain up to 100 characters
- `price` must be non‑null, zero or positive, and have at most 10 integer digits and 2 fractional digits
- `quantity` must be non‑null and fall between 0 and 1,000,000 (inclusive)

If a request violates these constraints, the API returns a `400 Bad Request` with details of the violations in the `errors` array. Example:

```json
{
  "status": "ERROR",
  "errors": [
    "name: must not be blank",
    "quantity: must be less than or equal to 1000000"
  ]
}
```

## Exception Handling

The API uses a global exception handler (`@ControllerAdvice`) to convert exceptions into meaningful error responses. Specific handlers exist for:

- **NoResourceFoundException** – returns a `404 Not Found` with a message when the requested product does not exist.
- **CustomResponseException** – returns a `400 Bad Request` for business‑rule violations.
- **HttpMessageNotReadableException** – returns a `400 Bad Request` for malformed JSON or invalid input types.
- All other unhandled exceptions – return a `500 Internal Server Error` with a generic error message.

All error responses conform to the `GlobalResponse` format described above.

## Project Structure

```
src/main/java/com/api/productmanagementapi
├── ProductManagementApiApplication.java  # Spring Boot entry point
├── controller/                          # REST controllers
│   └── ProductController.java
├── entity/                              # JPA entities
│   └── Product.java
├── repository/                          # Spring Data JPA repositories
│   └── ProductRepository.java
└── service/                             # Service interfaces and implementations
    ├── ProductService.java
    └── ProductServiceImpl.java
```

- `ProductController` exposes REST endpoints.
- `ProductService` defines business methods and `ProductServiceImpl` implements them.
- `ProductRepository` extends `JpaRepository` for CRUD operations.
- `Product` is the JPA entity.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.
