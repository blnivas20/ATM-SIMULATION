import java.math.BigDecimal;

public class Account {
    private String accountId;
    private String name;
    private String pin;
    private String dob;
    private BigDecimal balance;

    public Account(String accountId, String name, String pin, String dob, BigDecimal balance) {
        this.accountId = accountId;
        this.name = name;
        this.pin = pin;
        this.dob = dob;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountId + "," + name + "," + pin + "," + dob + "," + balance;
    }
}
