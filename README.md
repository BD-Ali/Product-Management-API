# Product Management API

A simple **Spring Boot** REST API for managing products with standardized responses and validation.  
The API provides endpoints to create, read, update and delete products stored in a **PostgreSQL** database.

---

## ✨ Features

- CRUD endpoints for products under `/api/products`
- Persistence with Spring Data JPA (PostgreSQL)
- Service layer and repository pattern
- Validation on fields (name not blank, price ≥ 0, quantity within range)
- Consistent response wrapper `GlobalResponse<T>` with `status`, `data`, `errors`
- Global exception handling (404 for missing product, 400 for invalid input, 500 fallback)
- MIT License

---

## 🧱 Tech Stack

- Java 17+, Spring Boot, Spring Web, Spring Data JPA
- PostgreSQL
- Maven

---

## 📦 Data Model (Product)

```java
class Product {
  Long id;
  String name;      
  BigDecimal price; 
  Integer quantity; 
}
```

Validation errors return HTTP 400 with messages in the `errors` array.

---

## 🔁 Standard Response Format

```json
// Success
{
  "status": "success",
  "data": { /* payload */ }
}

// Error
{
  "status": "error",
  "errors": [ { "message": "Readable error message" } ]
}
```

---

## 🚦 Endpoints

| Method | Endpoint                | Description                |
|-------:|-------------------------|----------------------------|
| GET    | `/api/products`         | Get all products           |
| GET    | `/api/products/{id}`    | Get a product by ID        |
| POST   | `/api/products`         | Create a new product       |
| PUT    | `/api/products/{id}`    | Update an existing product |
| DELETE | `/api/products/{id}`    | Delete a product           |

**Sample request body:**
```json
{
  "name": "Laptop",
  "price": 699.99,
  "quantity": 10
}
```

---

## 🏁 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL

### Configure Database

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/storedb
spring.datasource.username=product_user
spring.datasource.password=product_pass
spring.jpa.hibernate.ddl-auto=update
```

### Run the App

```bash
./mvnw spring-boot:run
# App runs at http://localhost:8080
```

---

## 🧰 Project Structure

```
src/main/java/com/api/productmanagementapi
├─ ProductManagementApiApplication.java
├─ controller/
│  └─ ProductController.java
├─ entity/
│  └─ Product.java
├─ repository/
│  └─ ProductRepository.java
└─ service/
   ├─ ProductService.java
   └─ ProductServiceImpl.java
```

---

## 📄 License

MIT
