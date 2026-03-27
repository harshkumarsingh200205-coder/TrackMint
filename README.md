# TrackMint

TrackMint is a Java-based expense tracker that helps users record daily spending, store data persistently, and analyze expenses.

## Features

- User registration and login
- Add, edit, delete expenses
- Category-wise tracking
- Budget setting
- Expense analytics
- Report export

## Tech Stack

- Java
- JDBC
- SQLite

## Project Status

### Day 1 – Project Setup

- Created project structure
- Implemented core model classes (User, Expense, Budget)
- Set up SQLite database connection using JDBC
- Initialized database with required tables
- Configured GitHub repository and initial commits

---

### Day 2 – Expense Management Module

#### Features Implemented

**1. Expense CRUD Operations**

- Add new expense
- View all expenses
- Update existing expense
- Delete expense

**2. Database Integration (SQLite)**

- Connected Java application to SQLite using JDBC
- Implemented database operations using PreparedStatement
- Ensured safe and efficient data handling

**3. Layered Architecture**

- Model Layer → Expense, Category, PaymentMode
- Repository Layer → ExpenseRepository (database operations)
- Service Layer → ExpenseService (business logic)
- UI Layer → ExpenseMenu (user interaction)

**4. Input Handling**

- Created InputUtil for:
  - integer input validation
  - double input validation
  - string input handling
- Prevents runtime crashes due to invalid input

**5. Menu-driven Console Interface**

- Interactive CLI for user operations
- Continuous loop for user actions
- Clean structured output

**6. Default User Setup**

- Added default user for testing (user_id = 1)
- Ensures foreign key constraint satisfaction

**7. External Dependencies**

- Integrated SQLite JDBC driver
- Added SLF4J dependencies for logging support

### Day 3 – Authentication, Budget, and Analytics

#### 1. User Authentication

- Implemented user registration
- Implemented login system using email and password
- Connected login flow to application entry point

#### 2. User-Specific Expense Handling

- Linked each expense to a specific user (user_id)
- Modified system to show only logged-in user’s expenses
- Removed dependency on hardcoded user IDs

#### 3. Budget Management

- Added monthly budget system
- Implemented:
  - Set budget for a month (YYYY-MM)
  - Update existing budget
  - Retrieve budget by user and month
- Ensured one budget per user per month

#### 4. Analytics Module

- Implemented spending insights:
  - Monthly total spending
  - Category-wise spending summary
  - Remaining budget calculation
  - Top spending category
- Used Java streams and data aggregation logic
