# User Management System (CRUD Only)

## Overview

This is a Java desktop application that allows you to manage user information using basic **CRUD** operations. The system connects to a **MySQL database** via **JDBC** and uses **XAMPP** for the database server.

> 🚫 No login or authentication – this is a basic management system.

---

## Features

- Add new users
- View user records
- Update user details
- Delete users

---

## Technologies Used

- Java (JDK 8 or higher)
- MySQL (XAMPP)
- JDBC (MySQL Connector/J)
- Console-based or Swing GUI (depends on your implementation)

---

## Setup Instructions

### 1. Requirements

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [XAMPP](https://www.apachefriends.org/index.html) with MySQL running
- Java IDE (e.g., Eclipse, IntelliJ IDEA, NetBeans)
- MySQL Connector/J (JDBC driver)

### 2. Import the Database

1. Start **XAMPP** and run **MySQL**.
2. Open **phpMyAdmin** (`http://localhost/phpmyadmin`).
3. Click **Import** and upload the provided `users.sql` file.
4. This will create the `javacrud` database and `users` table.

---

## How to Run the System

### 1. Setup JDBC Driver

- Download the **MySQL Connector/J** from the official site:  
  https://dev.mysql.com/downloads/connector/j/

- Extract the `.zip` or `.tar.gz` file.

- Add the `mysql-connector-java-<version>.jar` to your project’s classpath:

  - **In Eclipse**:  
    Right-click your project → *Build Path* → *Configure Build Path* → *Add External JARs*.

  - **In IntelliJ**:  
    File → *Project Structure* → *Modules* → *Dependencies* → *Add JARs or directories*.

---

### 2. Configure Database Connection

In your Java code (e.g., `DBConnection.java`), ensure the database credentials match your XAMPP setup:

```java
String url = "jdbc:mysql://localhost:3306/javacrud";
String username = "root";
String password = ""; // Default is empty for XAMPP
```

---

### 3. Compile and Run

#### ✅ Using an IDE:

- Open your project in your Java IDE (Eclipse, IntelliJ, etc.).
- Run the main class `CRUDAppUI.java`.

#### ✅ Using Command Line:

Make sure the `.java` files and the MySQL Connector JAR file are in the same directory, or update the path accordingly.

**On Windows:**

```bash
javac -cp .;mysql-connector-java-<version>.jar *.java
java -cp .;mysql-connector-java-<version>.jar Main
```

**On macOS/Linux:**

```bash
javac -cp .:mysql-connector-java-<version>.jar *.java
java -cp .:mysql-connector-java-<version>.jar Main
```

> 🔁 Replace `<version>` with the actual version number of your MySQL Connector JAR (e.g., `8.0.33`).
