import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ATM {

    // Validate PIN (must be exactly 4 digits)
    private static boolean isValidPin(String pin) {
        return pin.matches("\\d{4}");
    }

    // Validate DOB (DD-MM-YYYY, not future, must be real date)
    private static LocalDate validateDOB(String dob) {
        DateTimeFormatter indianFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate date = LocalDate.parse(dob, indianFmt);
            if (date.isAfter(LocalDate.now())) {
                System.out.println("❌ DOB cannot be a future date!");
                return null;
            }
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid DOB format! Use DD-MM-YYYY.");
            return null;
        }
    }

    // Generate Account ID using: 2 chars of name + MMDD from DOB + serial
    private static String generateAccountId(String name, LocalDate date) throws IOException {
        String prefix = name.substring(0, 2).toUpperCase();
        String datePart = String.format("%02d%02d", date.getMonthValue(), date.getDayOfMonth());
        int serial = FileRepository.getNextSerial();
        String serialStr = String.format("%04d", serial);
        return prefix + datePart + serialStr;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();

        System.out.println("=== Welcome to ATM ===");
        System.out.println("1. Login");
        System.out.println("2. Create New Account");
        System.out.print("Choose option: ");
        int option = sc.nextInt();
        sc.nextLine();

        String accId = null;
        if (option == 1) {
            System.out.print("Enter Account ID: ");
            accId = sc.nextLine();

            System.out.print("Enter PIN (4 digits): ");
            String pin = sc.nextLine();
            if (!isValidPin(pin)) {
                System.out.println("❌ Invalid PIN! Must be 4 digits.");
                return;
            }

            if (!service.authenticate(accId, pin)) {
                System.out.println("❌ Invalid credentials!");
                return;
            }
            System.out.println("✅ Login successful! Welcome " + accId);

        } else if (option == 2) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            LocalDate dobDate = null;
            while (dobDate == null) {
                System.out.print("Enter DOB (DD-MM-YYYY): ");
                String dob = sc.nextLine();
                dobDate = validateDOB(dob);
            }

            accId = generateAccountId(name, dobDate);
            System.out.println("Generated Account ID: " + accId);

            System.out.print("Set PIN (4 digits): ");
            String pin = sc.nextLine();
            if (!isValidPin(pin)) {
                System.out.println("❌ PIN must be exactly 4 digits!");
                return;
            }

            System.out.print("Initial Deposit: ");
            double deposit = sc.nextDouble();

            service.createAccount(accId, name, pin, deposit);
            System.out.println("✅ Account created successfully! Your Account ID is: " + accId);
            return;
        } else {
            System.out.println("❌ Invalid option!");
            return;
        }

        // === ATM Menu Loop ===
        while (true) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: " + service.getBalance(accId));
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double dep = sc.nextDouble();
                    service.deposit(accId, dep);
                    System.out.println("Deposited successfully.");
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double wit = sc.nextDouble();
                    if (service.withdraw(accId, wit)) {
                        System.out.println("Withdrawn successfully.");
                    } else {
                        System.out.println("Insufficient funds!");
                    }
                    break;
                case 4:
                    System.out.println("Mini Statement:");
                    for (Transaction tx : service.getMiniStatement(accId)) {
                        System.out.println(tx);
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using ATM!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
