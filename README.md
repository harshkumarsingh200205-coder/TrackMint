# TrackMint

TrackMint is a Java-based expense tracking system that helps users manage expenses, set monthly budgets, and analyze spending behavior through a structured backend system.

---

## Overview

TrackMint is a console-based application built using Java and SQLite. It follows a layered architecture and supports multiple users with persistent data storage and analytics.

---

## Tech Stack

- Java (Core Java, OOP)
- JDBC
- SQLite
- Git & GitHub

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
- User-specific data handling

### Budget Management

- Set monthly budget
- Update existing budget
- View budget for a specific month

### Analytics

- Monthly total spending
- Category-wise spending
- Remaining budget
- Top spending category

### Dashboard

- Monthly summary on login
- Quick insights:
  - total spent
  - remaining budget
  - top category

---

## Architecture

TrackMint follows a layered structure:

- **UI Layer**
  - Handles menus and user interaction

- **Service Layer**
  - Contains business logic

- **Repository Layer**
  - Handles database operations

- **Database Layer**
  - SQLite database

---

## Project Progress

### Day 1 – Setup

- Project structure created
- Model classes implemented
- Database connection established
- Tables created

---

### Day 2 – Expense Module

- Implemented CRUD operations
- Integrated SQLite using JDBC
- Created menu-driven interface

---

### Day 3 – Core Features

- Added user authentication (register/login)
- Linked expenses to users
- Implemented budget system
- Built analytics module:
  - monthly totals
  - category summaries
  - remaining budget
  - top category

---

### Day 4 – Final Improvements

- Added dashboard after login
- Introduced main menu navigation
- Implemented input validation
- Improved console output formatting
- Ensured user-specific update/delete operations
- Cleaned and refactored code structure

---

## How to Run

### Compile

```bash
javac -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

or run .\run.ps1
```
