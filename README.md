💳 ATM Simulation System

A console-based ATM simulator built in Java that demonstrates Object-Oriented Programming (OOP) principles, File I/O for data persistence, and core Java concepts.

The system mimics real-world ATM operations such as PIN-based login, balance inquiry, deposits, withdrawals, and generating mini statements.

🚀 Features

🔑 Secure Authentication – Login using Account ID and 4-digit PIN.

🏦 Account Creation – Users provide only Name, DOB (DD-MM-YYYY), and PIN.

Account ID is auto-generated using:

First 2 characters of Name

Date and Month from DOB

Unique 4-digit serial number

Example: Name = Nivas, DOB = 09-09-2002 → NI09090001

💰 Balance Inquiry – Check account balance anytime.

💵 Deposit Money – Add funds to your account.

💸 Withdraw Money – Withdraw funds with balance validation.

📄 Mini Statement – View the last 5 transactions with date & time.

💾 Persistent Storage –

Accounts stored in accounts.txt

Transactions stored in transactions.txt

🏗️ Project Structure
ATMSystem/
│
├── ATM.java              # Main driver class (menus, program flow)
├── Account.java          # Account model (fields: id, name, DOB, pin, balance)
├── Transaction.java      # Transaction model (deposit, withdraw, timestamped)
├── AccountService.java   # Business logic (deposit, withdraw, mini statement)
├── FileRepository.java   # Handles file I/O (accounts.txt & transactions.txt)
│
├── data/
│   ├── accounts.txt      # Stores account details
│   └── transactions.txt  # Stores transaction history
│
└── README.md             # Documentation

⚙️ How It Works

Account Creation

User enters Name, DOB, and PIN.

System generates unique Account ID automatically.

Initial deposit is required.

Account details saved in accounts.txt.

Login

User enters Account ID + PIN.

Credentials validated against accounts.txt.

ATM Menu Options

Balance Inquiry

Deposit

Withdraw

Mini Statement (last 5 transactions)

Exit

Transactions

Every deposit/withdrawal is recorded in transactions.txt with:

accountId, type, amount, balanceAfter, timestamp

🧑‍💻 OOP Concepts Demonstrated

Encapsulation → Account fields like PIN and balance are private, accessed only via methods.

Abstraction → User interacts only with high-level ATM options; internal file logic is hidden.

Polymorphism → toString() is overridden in Transaction for formatted statements.

Inheritance (extendable) → System can easily support SavingsAccount or CurrentAccount subclasses.

📂 Core Java Concepts Used

File I/O – BufferedReader, BufferedWriter, FileReader, FileWriter for persistent storage.

Collections – List<Transaction> to store and fetch transaction history.

Exception Handling – Graceful handling of invalid inputs and file errors.

String Manipulation – Used for generating Account IDs.

Java Date & Time API – LocalDate for DOB, LocalDateTime for transaction timestamps.

Static & Final – File paths defined as constants.

▶️ How to Run

Clone or download this repository.

Navigate to the project folder:

cd ATMSystem


Compile the Java files:

javac *.java


Run the program:

java ATM

📝 Example Usage
=== Welcome to ATM ===
1. Login
2. Create New Account
Choose option: 2
Enter Name: Nivas
Enter DOB (DD-MM-YYYY): 09-09-2002
Enter 4-digit PIN: 1984
Enter Initial Deposit: 5000
Account created! Your Account ID is: NI09090001

🔮 Future Enhancements

Add different account types (Savings, Current).

Add daily withdrawal limits.

Implement password encryption for PIN storage.

Build a GUI version using JavaFX or Swing.
