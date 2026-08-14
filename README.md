# Demo DAO JDBC Java ☕💾

A practical project developed in **Java** demonstrating the implementation of the **DAO (Data Access Object)** design pattern using **JDBC (Java Database Connectivity)** for data persistence in a relational database.

## 🎯 Project Objective

The main goal of this project is to isolate the data access logic from the application's business logic. This is achieved by encapsulating database operations, making maintenance easier and promoting clean, organized code according to Object-Oriented best practices.

## 🛠️ Technologies and Patterns

*   **Java:** Main programming language.
*   **JDBC (Java Database Connectivity):** Java API to connect and execute queries in the database.
*   **Relational Database:** (MySQL, PostgreSQL, etc.)
*   **DAO Pattern (Data Access Object):** To separate business rules from persistence rules.
*   **Factory Pattern:** To instantiate DAO implementations without exposing the concrete implementation (`DaoFactory`).

## 📂 Project Structure

The typical architecture of the project is organized as follows:

*   `model.entities`: Contains domain classes or entities (e.g., `Seller`, `Department`).
*   `model.dao`: Contains DAO interfaces and the instance factory (`DaoFactory`).
*   `model.dao.impl`: Contains concrete implementations of the interfaces using JDBC (e.g., `SellerDaoJDBC`).
*   `db`: Contains utility classes to manage database connection (`DB`) and custom exceptions (`DbException`, `DbIntegrityException`).
*   `application`: Contains the main class (`Program`) with the `main` method to test the operations.

## ⚙️ Features (CRUD)

All basic database operations have been implemented for the main entities:

*   **C**reate: Saves a new object to the database.
*   **R**ead: Fetches an object by ID or lists all records.
*   **U**pdate: Updates the information of an existing object.
*   **D**elete: Removes a record from the database using its ID.

## 🚀 How to Run

### Prerequisites
*   **Java Development Kit (JDK)** installed.
*   Relational database (e.g., MySQL) running locally or in the cloud.
*   JDBC Driver corresponding to the database added to the project's *Build Path* (or via Maven/Gradle in `pom.xml`).

### Step-by-Step

1. **Clone the repository:**
   ```bash
   git clone https://github.com/JotaV3/demo-dao-jdbc-java.git
   ```

2. **Database Configuration:**
   * Create the database and tables by running the SQL script (if there is a `script.sql` or similar file in the repository).
   * Open the properties file (usually `db.properties`) and insert your local database credentials:
     ```properties
     user=your_username
     password=your_password
     dburl=jdbc:mysql://localhost:3306/your_database_name
     useSSL=false
     ```

3. **Running the Application:**
   * Import the project into your favorite IDE (Eclipse, IntelliJ IDEA, VS Code).
   * Navigate to the `application` package and run the `Program.java` main class.
   * Check the console log demonstrating the CRUD tests.

## 👨‍💻 Author

Made by **João (JotaV3)**
*   GitHub: [JotaV3](https://github.com/JotaV3)

---
*Project developed to apply solid Software Engineering concepts and data persistence in Java.*
