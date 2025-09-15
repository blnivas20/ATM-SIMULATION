import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FileRepository {
    private static final String ACCOUNT_FILE = "data/accounts.txt";
    private static final String TRANSACTION_FILE = "data/transactions.txt";
    private static final String SERIAL_FILE = "data/serial.txt";

    // Load accounts from file
    public static Map<String, Account> loadAccounts() throws IOException {
        Map<String, Account> accounts = new HashMap<>();
        File file = new File(ACCOUNT_FILE);
        if (!file.exists()) return accounts;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Account acc = new Account(parts[0], parts[1], parts[2], parts[3], new BigDecimal(parts[4]));
                    accounts.put(acc.getAccountId(), acc);
                }
            }
        }
        return accounts;
    }

    // Save all accounts back to file
    public static void saveAccounts(Map<String, Account> accounts) throws IOException {
        File file = new File(ACCOUNT_FILE);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Account acc : accounts.values()) {
                bw.write(acc.toString());
                bw.newLine();
            }
        }
    }

    // Save transaction to file
    public static void addTransaction(Transaction tx) throws IOException {
        File file = new File(TRANSACTION_FILE);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(tx.toCSV());
            bw.newLine();
        }
    }

    // Load all transactions of an account
    public static List<Transaction> loadTransactions(String accId) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) return transactions;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(accId)) {
                    transactions.add(new Transaction(
                            parts[0], parts[1],
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                }
            }
        }
        return transactions;
    }

    // Get next serial for account ID generation
    public static int getNextSerial() throws IOException {
        File file = new File(SERIAL_FILE);
        file.getParentFile().mkdirs();
        int serial = 0;

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                if (line != null) {
                    serial = Integer.parseInt(line.trim());
                }
            }
        }

        serial++; // increment serial

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.valueOf(serial));
        }

        return serial;
    }
}
