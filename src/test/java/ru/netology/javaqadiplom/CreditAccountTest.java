package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    //    должен увеличивать баланс на указанную сумму
    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(3_000);

        Assertions.assertEquals(3_000, account.getBalance());
    }

    //    должен выкидывать IllegalArgumentException при создании объекта с отрицатальным rate
    @Test
    public void shouldThrowIllegalArgumentExceptionForNegativeRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(
                    5000,
                    1000,
                    -15
            );
        });
    }

    // Должен создавать новый объект класса с валидными данными
    @Test
    public void shouldCreateAccountWithValidData() {
        CreditAccount creditAccount = new CreditAccount(
                5000,
                1000,
                10
        );
        Assertions.assertEquals(5000, creditAccount.getBalance());
        Assertions.assertEquals(1000, creditAccount.getCreditLimit());
        Assertions.assertEquals(10, creditAccount.getRate());
    }

       //    должен выкидывать IllegalArgumentException при создании объекта с отрицатальным creditLimit
    @Test
    public void shouldThrowIllegalArgumentExceptionForNegativeLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(
                    5000,
                    -1000,
                    10
            );
        });
    }

    //    должен выкидывать IllegalArgumentException при создании объекта с отрицательным начальным балансом (initialBalance)
    @Test
    public void shouldThrowIllegalArgumentExceptionForNegativeInitialBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(
                    -5000,
                    1000,
                    10
            );
        });
    }

    //    должен успешно производить оплату. Баланс должен уменьшаться на сумму amount, а метод возвращать true
    @Test
    void shouldPayWithValidData() {

        CreditAccount creditAccount = new CreditAccount(3000, 1000, 10);
        creditAccount.pay(500);

        int expectedBalance = 2500;
        int actualBalance = creditAccount.getBalance();

        Assertions.assertTrue(creditAccount.pay(500));
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    // метод должен возвращать false, если в результате операции баланс становится меньше лимита.
    // Баланс не должен изменяться
    @Test
    void shouldNotPayExceedingCreditLimit() {
        CreditAccount creditAccount = new CreditAccount(1000, 1000, 10);

        boolean result = creditAccount.pay(2001);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, creditAccount.getBalance());
    }

    // метод должен возвращать true, если в результате операции баланс становится
    // отрицательным, но не превышает лимита
    @Test
    public void shouldPayIfNegativeBalanceNotExceedCreditLimit() {
        CreditAccount creditAccount = new CreditAccount(500, 1000, 10);

        boolean result = creditAccount.pay(1000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-500, creditAccount.getBalance());
    }

    //    Не должен изменять баланс, при отрицательной сумме платежа. Возвращает false
    @Test
    public void shouldNotPayWithNegativeAmount() {
        CreditAccount creditAccount = new CreditAccount(3000, 1000, 10);

        boolean result = creditAccount.pay(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(3000, creditAccount.getBalance());
    }

//    должен осуществлять платеж, в результате которого баланс становится равным лимиту
//    Метод должен вернуть true и изменить значение баланса на новое
    @Test
    public void shouldPayWhenBalanceEqualsCreditLimit() {
        CreditAccount creditAccount = new CreditAccount(1000, 1000, 10);

        boolean result = creditAccount.pay(2000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-1000, creditAccount.getBalance());
    }

    //    При добавлении суммы больше 0, должен увеличивать баланс на сумму amount и возвращать true
    @Test
    void shouldAddAmountToBalance() {
        CreditAccount creditAccount = new CreditAccount(100, 1000, 10);

        boolean result = creditAccount.add(100);
        Assertions.assertTrue(result);
        Assertions.assertEquals(200, creditAccount.getBalance());
    }

    //    При добавлении отрицательной суммы, возвращает false, баланс не должен меняться
    @Test
    void shouldNotAddNegativeAmountToBalance() {
        CreditAccount creditAccount = new CreditAccount(100, 1000, 10);
        boolean result = creditAccount.add(-1);
        Assertions.assertFalse(result);
        Assertions.assertEquals(100, creditAccount.getBalance());
    }


    //    должен рассчитывать годовой платеж за отрицательную сумму на балансе.
    @Test
    void yearChangeWithNegativeBalance() {
        CreditAccount creditAccount = new CreditAccount(0, 1000, 15);

        creditAccount.pay(100);
        int expected = -15;
        Assertions.assertEquals(expected, creditAccount.yearChange());
    }

    //    При положительном балансе, всегда должен возвращать 0
    @Test
    void yearChangeWithPositiveBalance() {
        CreditAccount creditAccount = new CreditAccount(100, 1000, 15);

        int expected = 0;
        Assertions.assertEquals(expected, creditAccount.yearChange());
    }
}
