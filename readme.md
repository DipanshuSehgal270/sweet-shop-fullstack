# 🍬 Sweet Shop Management System (Backend)

A robust, enterprise-grade RESTful API designed to manage inventory, sales, and authentication for a modern Sweet Shop. Built using **Spring Boot 3.4** with a strict **Test-Driven Development (TDD)** methodology.

## 🚀 Key Features

* **Secure Authentication:** Stateless **JWT (JSON Web Token)** authentication with Role-Based Access Control (RBAC).
* **Inventory Management:** Real-time stock updates (Purchase/Restock logic) with concurrency checks.
* **Advanced Search:** Dynamic filtering of sweets by Name, Category, and Price range.
* **Image Handling:** Integrated BLOB storage for product images directly in the database using Multipart requests.
* **Documentation:** Automated API documentation via **Swagger/OpenAPI**.
* **Reliability:** 90%+ Test Coverage using JUnit 5 and Mockito.

## 🛠️ Tech Stack

| Component | Technology |
| :--- | :--- |
| **Framework** | Java 17, Spring Boot 3.4 |
| **Database** | PostgreSQL (Production), H2 (Testing) |
| **Security** | Spring Security 6, JJWT 0.12, BCrypt |
| **Testing** | JUnit 5, Mockito, MockMvc (WebMvcTest) |
| **Docs** | OpenAPI (Swagger UI) |
| **Build Tool** | Maven |

## 📐 Architecture & Design Decisions

### 1. Test-Driven Development (TDD)
This project strictly followed the **Red-Green-Refactor** cycle. Every feature (Controller, Service, Security) started with a failing test case before a single line of implementation logic was written.
* **Unit Tests:** Isolated testing of Service layer business logic.
* **Integration Tests:** `MockMvc` testing of Controller endpoints (handling JSON & Multipart data).

### 2. Security Architecture
* **Stateless:** No server-side sessions.
* **Filter Chain:** Custom `JwtAuthenticationFilter` intercepts requests to validate tokens via `Authorization: Bearer <token>`.
* **RBAC:** Method-level security (`@PreAuthorize`) ensures only `ADMIN` roles can perform destructive actions (Delete/Restock).

### 3. Data Handling
* **Images:** Stored as `byte[]` (BLOB) in PostgreSQL to ensure data integrity and portability without relying on external file systems (S3/Cloudinary).
* **DTO Pattern:** Data Transfer Objects are used to decouple the internal Entity model from the external API layer.

---

## ⚙️ Setup & Installation

### Prerequisites
* Java 17 or higher
* Maven 3.6+
* PostgreSQL

### API Documentation
Once the server is running, access the interactive API documentation (Swagger UI):

👉 http://localhost:8080/swagger-ui/index.html

You can test endpoints directly from the browser using the "Authorize" button to input your JWT token.

### 🧪 Running Tests
To verify the TDD integrity and run the full test suite:

```Bash
./mvnw test
Test Coverage:

AuthControllerTest: Registration & Login flows.

SweetControllerTest: CRUD, Image Uploads, Search, Inventory.

SweetServiceTest: Business logic and validations.
```

### 1. Clone the Repository
```bash
git clone [https://github.com/yourusername/sweet-shop-backend.git](https://github.com/yourusername/sweet-shop-backend.git)

cd sweet-shop-backend

2. Database ConfigurationUpdate src/main/resources/application.properties with your PostgreSQL credentials:Propertiesspring.datasource.url=jdbc:postgresql://localhost:5432/sweet_shop

spring.datasource.username=your_postgres_username

spring.datasource.password=your_postgres_password

spring.jpa.hibernate.ddl-auto=update

3. JWT ConfigurationSet a secure key (min 32 chars) in application.properties:Propertiesspring.application.security.jwt.secretKey=YourSuperSecretKeyMustBe32CharsLong1234
spring.application.security.jwt.EXPIRATION_TIME=86400000

4. Build and RunBash./mvnw clean install

./mvnw spring-boot:run

The server will start on http://localhost:8080.📖 API DocumentationOnce the server is running, access the interactive API documentation (Swagger UI):👉 http://localhost:8080/swagger-ui/index.htmlYou can test endpoints directly from the browser using the "Authorize" button to input your JWT token.🧪 Running TestsTo verify the TDD integrity and run the full test suite:Bash./mvnw test

Test Coverage:AuthControllerTest: Registration & Login flows.SweetControllerTest: CRUD, Image Uploads, Search, Inventory.SweetServiceTest: Business logic and validations.🔒 Security EndpointsMethodEndpointDescriptionAccessPOST/api/auth/registerRegister a new user (Default Role: USER)PublicPOST/api/auth/loginLogin to receive JWT TokenPublicGET/api/sweetsView all sweetsPublicPOST/api/sweetsAdd new sweet (Multipart)Auth RequiredPUT/api/sweets/{id}Update details/imageAuth RequiredPOST/api/sweets/{id}/purchaseBuy a sweet (Decrements stock)Auth RequiredPOST/api/sweets/{id}/restockRestock inventoryADMIN OnlyDELETE/api/sweets/{id}Delete a sweetADMIN Only
```

