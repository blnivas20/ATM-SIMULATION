import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AccountService {
    private Map<String, Account> accounts;

    public AccountService() throws IOException {
        this.accounts = FileRepository.loadAccounts();
    }

    public boolean authenticate(String accId, String pin) {
        Account acc = accounts.get(accId);
        return acc != null && acc.getPin().equals(pin);
    }

    public void createAccount(String accId, String name, String pin, double deposit) throws IOException {
        Account acc = new Account(accId, name, pin, "", BigDecimal.valueOf(deposit));
        accounts.put(accId, acc);
        FileRepository.saveAccounts(accounts);
        FileRepository.addTransaction(new Transaction(accId, "OPEN", deposit, deposit));
    }

    public double getBalance(String accId) {
        return accounts.get(accId).getBalance().doubleValue();
    }

    public void deposit(String accId, double amount) throws IOException {
        Account acc = accounts.get(accId);
        BigDecimal newBalance = acc.getBalance().add(BigDecimal.valueOf(amount));
        acc.setBalance(newBalance);
        FileRepository.saveAccounts(accounts);
        FileRepository.addTransaction(new Transaction(accId, "DEPOSIT", amount, newBalance.doubleValue()));
    }

    public boolean withdraw(String accId, double amount) throws IOException {
        Account acc = accounts.get(accId);
        if (acc.getBalance().doubleValue() >= amount) {
            BigDecimal newBalance = acc.getBalance().subtract(BigDecimal.valueOf(amount));
            acc.setBalance(newBalance);
            FileRepository.saveAccounts(accounts);
            FileRepository.addTransaction(new Transaction(accId, "WITHDRAW", amount, newBalance.doubleValue()));
            return true;
        }
        return false;
    }

    public List<Transaction> getMiniStatement(String accId) throws IOException {
        List<Transaction> transactions = FileRepository.loadTransactions(accId);
        int size = transactions.size();
        return transactions.subList(Math.max(0, size - 5), size);
    }
}
