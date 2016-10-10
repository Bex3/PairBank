/**
 * Created by bearden-tellez on 10/8/16.
 */
public class BankAccount {
    private String name;
    private double balance = 0;
    private double initialBalance = 0;

    public BankAccount() {
    }

    @Override
    public String toString() {
        return "Account name = " + name + "\nAccount balance = " + balance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BankAccount (String name, double balance){
        this.name= name;
        this.balance = balance;
        this.initialBalance = balance;
    }
    public double deposit(double amountToDeposit){
        balance += amountToDeposit;
        return balance;
    }

    public double withdraw(double amountToWithdraw){
        balance -= amountToWithdraw;
        return balance;
    }

    public double transfer(BankAccount targetAccount, double amountToTransfer) {
        withdraw(amountToTransfer);
        targetAccount.deposit(amountToTransfer);
        return balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double startAcrruingInterest() {
        return balance;
    }
}
