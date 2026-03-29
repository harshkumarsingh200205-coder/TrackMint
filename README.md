# TrackMint

TrackMint is a Java-based expense tracking system that helps users manage daily expenses, set monthly budgets, and analyze spending patterns through a structured and layered backend design.

---

## Problem Statement

Managing daily expenses is often unstructured and inconsistent. Many users rely on memory or scattered tools, which leads to poor financial awareness and inefficient budgeting.

TrackMint solves this problem by providing a centralized system to record, manage, and analyze expenses.

---

## Why this Project Matters

This project addresses a real-world problem of financial tracking, especially for students and individuals managing limited budgets.

- Provides structured expense management
- Enables better financial decision-making
- Demonstrates real-world application of Java concepts
- Aligns with BYOP requirement of solving a practical problem

---

## Project Overview

TrackMint is a console-based application built using Java and SQLite. It allows users to:

- Track expenses
- Manage monthly budgets
- Analyze spending patterns

The application supports multiple users, persistent storage, and structured analytics.

---

## Features

### Expense Management

- Add expense
- View all expenses
- Update expense
- Delete expense

### User Management

- Register new user
- Login system
- User-specific expense tracking

### Budget Management

- Set monthly budget
- Update budget
- View budget

### Analytics

- Monthly total spending
- Category-wise spending summary
- Remaining budget
- Top spending category

### Dashboard

- Summary after login
- Monthly spending overview
- Remaining budget
- Top category

### Improvements

- Input validation
- Safe update/delete operations
- Clean menu navigation
- Improved console formatting

---

## Technologies Used

- Java (Core Java)
- JDBC
- SQLite
- Git & GitHub

---

## Java Concepts Applied

- Object-Oriented Programming (OOP)
- Encapsulation and modular design
- Package structuring (app, model, service, ui, db, util)
- Exception Handling
- Collections Framework
- JDBC (Database Connectivity)
- Control statements and loops

---

## Project Architecture

TrackMint follows a layered architecture:

### UI Layer

Handles menus and user interaction.

### Service Layer

Handles business logic such as authentication, budgeting, analytics, and expense operations.

### Repository Layer

Handles database communication using JDBC.

### Database Layer

Stores users, expenses, and budgets in SQLite.

---

## Project Structure

TrackMint/
│
├── src/
│ └── com/trackmint/
│ ├── app/ # Main entry point
│ ├── model/ # Data models
│ ├── service/ # Business logic
│ ├── ui/ # User interface
│ ├── db/ # Database connection
│ └── util/ # Utility classes
│
├── lib/ # JDBC driver
├── out/ # Compiled files
└── trackmint.db # Database file

---

---

## Setup Instructions

### Prerequisites

- Java JDK 8 or above
- SQLite JDBC driver in `lib/` folder

### Steps

1. Clone repository
   git clone <your-repo-link>
   cd TrackMint

2. Ensure JDBC driver is present:

lib/sqlite-jdbc-<version>.jar

3. Compile project:

javac -cp "lib/_" -d out (Get-ChildItem -Recurse -Filter _.java | ForEach-Object { $\_.FullName })

---

## How to Run

java -cp "out;lib/\*" com.trackmint.app.Main

For Linux/Mac:

java -cp "out:lib/\*" com.trackmint.app.Main

OR use:

.\run.ps1

---

## Sample Workflow

1. Launch application
2. Register or login
3. Add expenses
4. Set monthly budget
5. View dashboard
6. Analyze spending
7. Update or delete records

---

## Project Progress

### Day 1 – Setup

- Created project structure
- Added model classes
- Set up SQLite database connection
- Created database tables
- Connected GitHub repository

### Day 2 – Expense Module

- Implemented expense CRUD operations
- Added menu-driven expense handling
- Integrated SQLite with JDBC
- Added input utility for safe console input

### Day 3 – Authentication, Budget, and Analytics

- Added user registration and login
- Linked expenses to logged-in users
- Implemented monthly budget system
- Added analytics:
  - monthly total spending
  - category-wise totals
  - remaining budget
  - top category

### Day 4 – Final Improvements

- Added dashboard summary after login
- Refactored navigation using a main menu
- Added strong input validation
- Improved output formatting
- Improved project structure and safety of update/delete operations
- Cleaned repository and documentation

---

## Challenges Faced

- JDBC configuration and driver issues
- Classpath and compilation errors
- Designing scalable package structure
- Handling database initialization
- Ensuring proper separation of concerns

---

## Future Enhancements

- GUI version (JavaFX or Web App)
- Advanced analytics with graphs
- Budget alerts and notifications
- Export to CSV/Excel
- Multi-user authentication system
- Spring Boot backend migration

---

## Author

Harsh Kumar Singh  
B.Tech AI & ML

---
