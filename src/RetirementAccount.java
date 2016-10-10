/**
 * Created by bearden-tellez on 10/8/16.
 */
public class RetirementAccount extends BankAccount implements Runnable {

    long sleepTimeout = 2000;

    public RetirementAccount(String name, double amount) {
        super(name, amount);
    }

    @Override
    public double startAcrruingInterest() {
        accrueInterest();
        new Thread(this).start();
        return getBalance();
    }

    public void accrueInterest() {
        System.out.println("******");
        double balBefore = getBalance();
        setBalance(getBalance() + (getBalance() * 0.1));
        System.out.println(getName() + " increased balance from " + balBefore + " to " + getBalance());
    }

    public void run() {
        System.out.println("Running the background thread ... ");
        try {
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
