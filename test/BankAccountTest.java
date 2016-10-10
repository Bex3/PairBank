import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bearden-tellez on 10/8/16.
 */
public class BankAccountTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void deposit() throws Exception {
        BankAccount myBankAccount = new BankAccount("testAccount", 10.00 );
        double newBal = myBankAccount.deposit(12.00);
        assertEquals(22.00, myBankAccount.getBalance(), 0);
        assertEquals(22.00, newBal, 0);

    }

    @Test
    public void withdraw() throws Exception {
        BankAccount myBankAccount = new BankAccount("testAccount", 10.00 );
        double newBal = myBankAccount.withdraw(2.00);
        assertEquals(8.00, myBankAccount.getBalance(), 0);
        assertEquals(8.00, newBal, 0);

    }

    @Test
    public void transfer() throws Exception {
        BankAccount sourceBankAccount = new BankAccount("testAccount", 10.00 );
        BankAccount targetAccount = new BankAccount("tAccount", 24.00);

        double newBal = sourceBankAccount.transfer(targetAccount, 5.00);
        assertEquals(5.00, sourceBankAccount.getBalance(), 0);
        assertEquals(5.00, newBal, 0);
        assertEquals(29.00, targetAccount.getBalance(), 0);

    }

    @Test
    public void testNoAccruing() throws Exception {
        BankAccount baseBankAccount = new BankAccount("testAccount", 10.00);
        double newBal = baseBankAccount.startAcrruingInterest();
        assertEquals(10.00, newBal, 0);
    }

    @Test
    public void testAccruingSavings() throws Exception {
        BankAccount savingsBankAccount = new SavingsAccount("testAccount", 100.00);
        double newBal = savingsBankAccount.startAcrruingInterest();
        assertEquals(105.00, newBal, 0);
    }

    @Test
    public void testAccruingRetirement() throws Exception {
        BankAccount retBankAccount = new RetirementAccount("testAccount", 100.00);
        double newBal = retBankAccount.startAcrruingInterest();
        assertEquals(110.00, newBal, 0);
    }

}