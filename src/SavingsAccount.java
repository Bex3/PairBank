/**
 * Created by bearden-tellez on 10/8/16.
 */
public class SavingsAccount extends BankAccount implements Runnable {

    long sleepTimeout = 1000;

    public SavingsAccount(String name, double amount) {
        super(name, amount);
    }

    @Override
    public double startAcrruingInterest() {
        accrueInterest();
        new Thread(this).start();
        return getBalance();
    }

    public void accrueInterest() {
        double balBefore = getBalance();
        setBalance(getBalance() + (getBalance() * 0.05));
//        System.out.println(getName() + " increased balance from " + balBefore + " to " + getBalance());
    }

    public void run() {
//        System.out.println("Running the background thread ... ");
        try {1
            while (true && !BankRunner.closeBank) {
                Thread.sleep(sleepTimeout);
                accrueInterest();
            }
            System.out.println("Done accruing interest");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
