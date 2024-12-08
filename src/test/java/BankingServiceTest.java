import com.example.bankingsystem.service.BankingService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BankingServiceTest {

    @Test
    public void testDepositMoney() {
        BankingService service = new BankingService();
        assertDoesNotThrow(() -> service.depositMoney(1, 500));
    }

    @Test
    public void testWithdrawMoney() {
        BankingService service = new BankingService();
        assertDoesNotThrow(() -> service.withdrawMoney(1, 100));
    }

    @Test
    public void testTransferMoney() {
        BankingService service = new BankingService();
        assertDoesNotThrow(() -> service.transferMoney(1, 2, 200));
    }

    @Test
    public void testPayUtilityBill() {
        BankingService service = new BankingService();
        assertDoesNotThrow(() -> service.payUtilityBill(1, 150));
    }
}
