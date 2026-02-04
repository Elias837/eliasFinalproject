# Elias Final Project - UML Class Diagram

```mermaid
classDiagram
    %% Main Activities
    class MainActivity {
        -MaterialCardView btnAcc
        -MaterialCardView btnGoals
        -MaterialCardView btnAI
        -MaterialCardView btnStatistics
        -Button btnCalendar
        -Button btnCompass
        -Button btnToday
        -Button btnSettings
        -Spinner spnrIncome
        -FloatingActionButton fabAddIncome
        +onCreate(Bundle)
    }

    class AddGoal2Activity {
        -TextInputEditText goalNameEditText
        -TextInputEditText targetAmountEditText
        -TextInputEditText targetDateEditText
        -TextInputEditText notesEditText
        -MaterialButton saveButton
        -Calendar calendar
        +onCreate(Bundle)
        -initializeViews()
        -setupToolbar()
        -setupDatePicker()
        -setupSaveButton()
        -saveGoal()
        -showDatePicker()
        -updateDateInView()
    }

    class goalsAbudgeting {
        -FloatingActionButton addGoalFab
        +onCreate(Bundle)
    }

    class AIinsights {
        +onCreate(Bundle)
    }

    class statistics {
        +onCreate(Bundle)
    }

    class accountsAndPay {
        +onCreate(Bundle)
    }

    %% Authentication Activities
    class Login {
        +onCreate(Bundle)
    }

    class SignInActivity {
        +onCreate(Bundle)
    }

    class SignUpActivity {
        +onCreate(Bundle)
    }

    class SplashScreenActivity {
        +onCreate(Bundle)
    }

    %% Data Layer - Database
    class AppDataBaseE {
        -static String DATABASE_NAME
        -static AppDataBaseE instance
        +getInstance(Context) AppDataBaseE
        +myProfileDao() MyProfileQuery
    }

    class MyIncome {
        -long transactionId
        -double amount
        -String type
        -String shortTitle
        -String description
        -long date
        -String paymentMethod
        -String counterpart
        -boolean isCompleted
        -long categoryId
        -double remainingBalance
        -String location
        -boolean isRecurring
        -long userId
        +getters() and setters()
    }

    class MyProfile {
        %% Profile data fields
    }

    %% DAO Classes
    class MyIncomeQuery {
        %% Database operations for MyIncome
    }

    class MyProfileQuery {
        %% Database operations for MyProfile
    }

    %% Model Classes
    class BudgetItem {
        -String category
        -double currentAmount
        -double totalAmount
        -int progress
        +BudgetItem(String, double, double)
        +getters() and setters()
        +setCurrentAmount(double)
    }

    %% Adapter Classes
    class BudgetAdapter {
        %% RecyclerView adapter for budget items
    }

    class MyIncomeAdapter {
        %% RecyclerView adapter for income items
    }

    %% Relationships
    MainActivity --> accountsAndPay : navigates to
    MainActivity --> goalsAbudgeting : navigates to
    MainActivity --> AIinsights : navigates to
    MainActivity --> statistics : navigates to
    
    goalsAbudgeting --> AddGoal2Activity : navigates to
    
    AppDataBaseE --> MyIncome : contains
    AppDataBaseE --> MyProfile : contains
    AppDataBaseE --> MyIncomeQuery : provides
    AppDataBaseE --> MyProfileQuery : provides
    
    MyIncomeQuery --> MyIncome : operates on
    MyProfileQuery --> MyProfile : operates on
    
    BudgetAdapter --> BudgetItem : uses
    MyIncomeAdapter --> MyIncome : uses
    
    goalsAbudgeting --> BudgetAdapter : uses
    goalsAbudgeting --> BudgetItem : uses

    %% Inheritance
    MainActivity <|-- AppCompatActivity
    AddGoal2Activity <|-- AppCompatActivity
    goalsAbudgeting <|-- AppCompatActivity
    AIinsights <|-- AppCompatActivity
    statistics <|-- AppCompatActivity
    accountsAndPay <|-- AppCompatActivity
    Login <|-- AppCompatActivity
    SignInActivity <|-- AppCompatActivity
    SignUpActivity <|-- AppCompatActivity
    SplashScreenActivity <|-- AppCompatActivity
    
    AppDataBaseE <|-- RoomDatabase
    MyIncome <|-- Entity
    MyProfile <|-- Entity
```

## Architecture Overview

This Android application follows a **Model-View-Controller (MVC)** pattern with the following layers:

### **Presentation Layer (Activities)**
- **MainActivity**: Main dashboard with navigation to core features
- **AddGoal2Activity**: Goal creation form with date picker and validation
- **goalsAbudgeting**: Budget and goals management screen
- **AIinsights**: AI-powered financial insights (placeholder)
- **statistics**: Financial statistics and analytics (placeholder)
- **accountsAndPay**: Account management and payments (placeholder)
- **Authentication**: Login, Sign In, Sign Up, and Splash screens

### **Business Logic Layer**
- **BudgetItem**: Model for budget categories with progress tracking
- **BudgetAdapter**: RecyclerView adapter for displaying budget items
- **MyIncomeAdapter**: RecyclerView adapter for income/expense items

### **Data Layer**
- **AppDataBaseE**: Room database singleton
- **MyIncome**: Entity representing financial transactions
- **MyProfile**: Entity representing user profile data
- **MyIncomeQuery & MyProfileQuery**: DAO interfaces for database operations

### **Key Features Implemented**
1. **Navigation System**: Main dashboard with Material Design cards
2. **Goal Management**: Complete goal creation workflow with validation
3. **Database Architecture**: Room-based persistence layer
4. **Financial Tracking**: Comprehensive transaction model with Arabic documentation
5. **UI Components**: Material Design components throughout

### **Current Status**
- ✅ Core navigation structure
- ✅ Goal creation functionality
- ✅ Database schema and entities
- ✅ Basic UI layouts
- 🔄 AI insights, statistics, and accounts modules need implementation
- 🔄 Data binding and business logic integration needed
