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





# 🥨 Bikaner Sweets - Frontend

A modern, responsive Single Page Application (SPA) for an e-commerce sweet shop. This interface allows users to browse traditional Indian sweets, manage a shopping cart, and simulate secure payments, while providing a comprehensive dashboard for Administrators to manage inventory.

> **Note:** This frontend interacts with a custom-built **Spring Boot Backend** running on Port 8080.

## 🤖 AI Usage Disclosure

**Development Methodology:**
* **Frontend (100% AI Generated):** The entire frontend codebase—including the React architecture, CSS styling, component logic, and Context API integration—was generated by Artificial Intelligence. The goal was to rapidly prototype a professional UI to demonstrate the capabilities of the backend.
* **Backend:** The Spring Boot backend, including the database architecture, security (JWT), API endpoints, and business logic, was designed and coded manually with AI suggestions.

## 🚀 Key Features

### 👤 User Features
* **Hero Landing Page:** Professional entrance with trust badges and brand storytelling.
* **Smart Dashboard:**
    * **Trending Now:** Automatically highlights top-selling items based on sales data.
    * **Search:** Real-time filtering by sweet name.
    * **Stock Indicators:** Visual badges for "In Stock" vs "Sold Out".
* **Shopping Cart:**
    * Global state management using **Context API**.
    * Persistent cart (saved in LocalStorage).
    * Removal and summary calculation.
* **Simulated Checkout:** A realistic "Fake Payment" modal with spinning loaders and validation.

### 🛡️ Admin Features
* **Role-Based Access:** Admin-only buttons are hidden from normal users.
* **Inventory Management:**
    * **Add Item:** Upload names, prices, categories, and images.
    * **Edit Item:** Update prices or fix descriptions instantly.
    * **Delete Item:** Remove products with confirmation.
    * **User Management:** Promote standard users to Admins.

## 🛠️ Tech Stack

| Category | Technology |
| :--- | :--- |
| **Framework** | React 18 (Vite Build Tool) |
| **Language** | JavaScript (ES6+) |
| **Styling** | Bootstrap 5 + Custom "SaaS-Style" CSS |
| **State Management** | React Context API |
| **Routing** | React Router DOM v6 |
| **HTTP Client** | Axios (with Interceptors for JWT) |
| **Notifications** | React Toastify |

## ⚙️ Installation & Setup

### Prerequisites
* Node.js (v18 or higher)
* The **Bikaner Sweets Backend** must be running on `http://localhost:8080`.

### Steps
1.  **Navigate to the folder:**
    ```bash
    cd frontend
    ```

2.  **Install Dependencies:**
    ```bash
    npm install
    ```

3.  **Run the Development Server:**
    ```bash
    npm run dev
    ```

4.  **Access the App:**
    Open your browser to `http://localhost:5173` (or the port shown in your terminal).

## 📂 Project Structure

```text
src/
├── components/          # Reusable UI parts
│   ├── Navbar.jsx       # Responsive navigation with Cart badge
│   ├── Footer.jsx       # Static footer with links
│   └── PaymentModal.jsx # Fake payment simulation
├── context/
│   └── CartContext.jsx  # Global Cart State Logic
├── pages/
│   ├── HomePage.jsx     # Landing Page (Hero Section)
│   ├── DashboardPage.jsx# Main Shop (Grid, Search, Trending)
│   ├── CartPage.jsx     # Checkout Screen
│   ├── LoginPage.jsx    # Auth Forms
│   └── AddSweetPage.jsx # Admin Forms
├── services/
│   ├── api.js           # Axios instance with Token Interceptor
│   └── sweetService.js  # API calls to Backend
└── index.css            # Custom professional styling
```

## Application Flow
Visitor: Lands on Home Page -> Browses Shop -> Logs in/Registers.

Customer: Adds items to Cart -> Reviews Cart -> clicks "Proceed to Pay" -> Enters fake details -> Order confirmed.

Admin: Logs in -> Sees Edit/Delete buttons on cards -> Can add new inventory via "+ Add Item".

