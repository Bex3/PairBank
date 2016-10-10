import java.util.HashMap;

/**
 * Created by bearden-tellez on 10/8/16.
 */
public class Bank {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += "Bank name = " + getName();
        for (Customer customer : getCustomers().values()) {
            returnString += "\n" + "Customer = " + customer.getName();
        }

        return returnString;
    }

    public void setName(String name) {
        this.name = name;
    }

    private HashMap<String, Customer> customers = new HashMap<String, Customer>();

    public HashMap<String, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(HashMap<String, Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getName(), customer);
    }

    public Customer getCustomer(String name) {
        return customers.get(name);
    }

}
