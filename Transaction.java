import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String accountId;
    private String type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime dateTime;

    public Transaction(String accountId, String type, double amount, double balanceAfter) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.dateTime = LocalDateTime.now();
    }

    public String toCSV() {
        return accountId + "," + type + "," + amount + "," + balanceAfter + "," + dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("[%s] %s: %.2f | Balance: %.2f",
                dateTime.format(fmt), type, amount, balanceAfter);
    }
}
