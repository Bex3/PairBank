import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by bearden-tellez on 10/8/16.
 */
public class BankRunner {
    Scanner inputScanner = new Scanner(System.in);

    public static boolean closeBank = false;

    public static void main(String[] args) throws Exception {
        System.out.println("Running bank");

        BankRunner runner = new BankRunner();

//        runner.testSave();
//        runner.testRead();

        runner.displayMainMenu();

        closeBank = true;
    }

    public void saveBank(Bank bank) {
        try {
            File bankFile = new File("bank.txt");
            FileWriter bankWriter = new FileWriter(bankFile);
            int customerIndex = 0;
            for (Customer customer : bank.getCustomers().values()) {
                if (customerIndex != 0) {
                    bankWriter.write(",");
                }
                bankWriter.write(customer.getName());

                saveAccounts(customer);

                customerIndex++;
            }
            bankWriter.close();
        } catch (Exception exception) {
            System.out.println("Exception while writing to file ...");
        }
    }

    public void saveAccounts(Customer customer) {
        try {
            File customerFile = new File(customer.getName() + ".txt");
            FileWriter customerWriter = new FileWriter(customerFile);
            for (BankAccount account : customer.getCustomerAccounts().values()) {
                customerWriter.write("account.name=" + account.getName() + "\n");
                customerWriter.write("account.initBalance=" + account.getInitialBalance() + "\n");
                customerWriter.write("account.currentBalance=" + account.getBalance() + "\n");
                customerWriter.write("account.accountType=" + account.getClass().getName() + "\n");
            }
            customerWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Bank readBank() {
        try {
            File bankFile = new File("bank.txt");
            Scanner fileScanner = new Scanner(bankFile);
            String bankName = null;
            Bank retrievedBank = new Bank();
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                String[] customerNames = currentLine.split(",");
                for (String customerName : customerNames) {
                    Customer customer = new Customer();
                    customer.setName(customerName);
                    readCustomerAccounts(customer);
                    retrievedBank.addCustomer(customer);
                }
                return retrievedBank;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void readCustomerAccounts(Customer customer) {
        try {
            File customerFile = new File(customer.getName() + ".txt");
            Scanner fileScanner = new Scanner(customerFile);
            while (fileScanner.hasNext()) {
                String nameLine = fileScanner.nextLine();
                String initBalanceLine = fileScanner.nextLine();
                String currBalanceLine = fileScanner.nextLine();
                String accountTypeLine = fileScanner.nextLine();
                String accountType = accountTypeLine.split("=")[1];

                BankAccount bankAccount = null;
                if (accountType.equalsIgnoreCase("BankAccount")) {
                    bankAccount = new BankAccount(nameLine.split("=")[1],
                            Double.valueOf(initBalanceLine.split("=")[1]));
                } else if (accountType.equalsIgnoreCase("SavingsAccount")) {
                    bankAccount = new SavingsAccount(nameLine.split("=")[1],
                            Double.valueOf(initBalanceLine.split("=")[1]));
                } else if (accountType.equalsIgnoreCase("RetirementAccount")) {
                    bankAccount = new RetirementAccount(nameLine.split("=")[1],
                            Double.valueOf(initBalanceLine.split("=")[1]));
                }

                bankAccount.setBalance(Double.valueOf(currBalanceLine.split("=")[1]));
                bankAccount.startAcrruingInterest();
                customer.addBankAccount(bankAccount);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void displayMainMenu() throws Exception {
        Bank myBank = readBank();
        if (myBank == null) {
            myBank = new Bank();
        }

        myBank.setName("Pride Bank");

        System.out.println(myBank);

//        Customer hardCustomer = new Customer();
//        hardCustomer.setName("Hardcoded");
//        myBank.addCustomer(hardCustomer);

        System.out.println("Welcome to Pride Bank - what's your name?");
        String customerName = inputScanner.nextLine();

        System.out.println("Welcome, " + customerName);

        Customer customer = myBank.getCustomer(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setName(customerName);
        }

//        getAccountInfoFromUser(customer);
//        myBank.addCustomer(customer);

        while (true) {
            System.out.println("Would you like to create an account? (y/n)");
            System.out.println("Type exit if you would like to exit the program");
            String createAccountResponse = inputScanner.nextLine();
            if (createAccountResponse.equalsIgnoreCase("exit")){
                break;
            }
            if (createAccountResponse.equalsIgnoreCase("y")) {
                getAccountInfoFromUser(customer);
            } else {
                displayAccountMenu(customer);
            }
        }

        saveBank(myBank);
    }

    public void displayAccountMenu(Customer customer) {
        while (true) {
            System.out.println("Choose an account for your transactions (enter the account name)");
            for (BankAccount currentAccount : customer.getCustomerAccounts().values()) {
                System.out.println(currentAccount.getName());
            }
            System.out.println("(Type exit to return to the previous menu)");
            String accountChoice = inputScanner.nextLine();
            if (accountChoice.equalsIgnoreCase("exit")) {
                break;
            }
            BankAccount chosenAccount = customer.getBankAccount(accountChoice);
            System.out.println(chosenAccount);

            displayTransactionMenu(chosenAccount, customer);
        }
    }

    public void displayTransactionMenu(BankAccount chosenAccount, Customer customer) {
        while (true) {
            System.out.println("What transaction would you like to perform on your " + chosenAccount.getName() + " account?");
            System.out.println("0. Done with transactions");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            int txChoice = Integer.valueOf(inputScanner.nextLine());
            if (txChoice == 0) {
                System.out.println("exiting this menu ...");
                break;
            } else if (txChoice == 1) {
                System.out.println("How much would you like to deposit?");
                double amountToDeposit = Double.valueOf(inputScanner.nextLine());
                chosenAccount.deposit(amountToDeposit);
            } else if (txChoice == 2) {
                System.out.println("How much would you like to withdraw?");
                double amountToWithdraw = Double.valueOf(inputScanner.nextLine());
                chosenAccount.withdraw(amountToWithdraw);
            } else if (txChoice == 3) {
                System.out.println("Chose a target account for your transfer (enter account name)");
                for (BankAccount currentAccount : customer.getCustomerAccounts().values()) {
                    System.out.println(currentAccount.getName());
                }
                String targetAccountChoice = inputScanner.nextLine();
                BankAccount targetAccount = customer.getBankAccount(targetAccountChoice);
                System.out.println("How much would you like to transfer?");
                double amountToTransfer = Double.valueOf(inputScanner.nextLine());
                chosenAccount.transfer(targetAccount, amountToTransfer);
            }
            System.out.println("Your new account balance is " + chosenAccount.getBalance());
        }
    }

    public void getAccountInfoFromUser(Customer customer) {
        System.out.println("Please enter an account name");
        String accountName = inputScanner.nextLine();
        System.out.println("What type of account is this?");
        System.out.println("1. Checking Account");
        System.out.println("2. Savings Account");
        System.out.println("3. Retirement Account");
        int accountTypeChoice = Integer.valueOf(inputScanner.nextLine());
        BankAccount customerAccount = null;
        if (accountTypeChoice == 1) {
            customerAccount = new BankAccount(accountName, 0.00);
        } else if (accountTypeChoice == 2) {
            customerAccount = new SavingsAccount(accountName, 10.00);
        } else if (accountTypeChoice == 3) {
            customerAccount = new RetirementAccount(accountName, 10.00);
        }
        customerAccount.startAcrruingInterest();
        customer.addBankAccount(customerAccount);
    }
}
