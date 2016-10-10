import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bearden-tellez on 10/8/16.
 */
public class Customer {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private HashMap<String, BankAccount> customerAccounts = new HashMap<String, BankAccount>();

    public BankAccount getBankAccount(String name) {
        return customerAccounts.get(name);
    }

    public void addBankAccount(BankAccount bankAccount) {
        customerAccounts.put(bankAccount.getName(), bankAccount);
    }

    public HashMap<String, BankAccount> getCustomerAccounts() {
        return customerAccounts;
    }

    public void setCustomerAccounts(HashMap<String, BankAccount> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }
}
