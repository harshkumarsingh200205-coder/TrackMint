# TrackMint

TrackMint is a Java-based expense tracking system that helps users manage daily expenses, set monthly budgets, and analyze spending patterns through a structured and layered backend design.

---

## Project Overview

TrackMint is a console-based application built using Java and SQLite. It is designed to solve a practical problem: helping users track where their money goes, plan monthly budgets, and understand their spending behavior through analytics.

The application supports multiple users, persistent data storage, budget handling, and spending analysis.

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
- User-specific expense handling

### Budget Management

- Set monthly budget
- Update budget for an existing month
- View monthly budget

### Analytics

- Monthly total spending
- Category-wise spending summary
- Remaining budget
- Top spending category

### Dashboard

- Summary shown after login
- Current month total spent
- Remaining budget
- Top category

### Improvements

- Input validation
- Safer update and delete operations
- Clean menu navigation
- Improved console formatting

---

## Tech Stack

- Java
- JDBC
- SQLite
- Git & GitHub

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

## How to Run

### Compile

```powershell
javac -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

or
run .\run.ps1
```
