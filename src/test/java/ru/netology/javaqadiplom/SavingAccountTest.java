package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        account.add(3_000);

        Assertions.assertEquals(2_000 + 3_000, account.getBalance());
    }


    @ParameterizedTest
    @CsvSource(value = {
            "0,0,0,0",
            "1,1,1,1",
            "2,1,3,0"
    })
    public void createSavingAccountTest(int initialBalance, int minBalance, int maxBalance, int rate) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        Assertions.assertEquals(initialBalance, account.getBalance());
        Assertions.assertEquals(rate, account.getRate());
        Assertions.assertEquals(minBalance, account.getMinBalance());
        Assertions.assertEquals(maxBalance, account.getMaxBalance());
    }

    //-----ожидаемое сообщение в следующих тестах можно подкорректировать, главное- чтобы улавливался смысл исключения-----------\\
    @Test
    public void createSavingAccountWithRateException() {

        SavingAccount account = null;
        rate = -1;

        try {
            account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        } catch (IllegalArgumentException e) {

            String expectedMessage = "Накопительная ставка не может быть отрицательной, а у вас: " + rate;
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
        Assertions.assertEquals(null, account); // если дошло до этого ассерта, значит проверяемое условие не реализовано
    }

    @Test
    public void createSavingAccountWithMinBalanceException() {

        SavingAccount account = null;
        minBalance = -1;

        try {
            account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        } catch (IllegalArgumentException e) {

            String expectedMessage = "Минимальный баланс не может быть отрицательным, а у вас: " + minBalance;
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
        Assertions.assertEquals(null, account); // если дошло до этого ассерта, значит проверяемое условие не реализовано
    }

    @Test
    public void createSavingAccountWithMaxBalanceException() {

        SavingAccount account = null;
        maxBalance = minBalance - 1;

        try {
            account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        } catch (IllegalArgumentException e) {

            String expectedMessage = "Максимальный баланс не может быть меньше минимального, а у вас: " + maxBalance;
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
        Assertions.assertEquals(null, account); // если дошло до этого ассерта, значит проверяемое условие не реализовано
    }

    @Test
    public void createSavingAccountWithInitialBalanceLessThanMinBalanceException() {

        SavingAccount account = null;
        initialBalance = minBalance - 1;

        try {
            account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        } catch (IllegalArgumentException e) {

            String expectedMessage = "Начальный баланс не может быть меньше минимального, а у вас: " + initialBalance;
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
        Assertions.assertEquals(null, account); // если дошло до этого ассерта, значит проверяемое условие не реализовано
    }

    @Test
    public void createSavingAccountWithInitialBalanceMoreThanMaxBalanceException() {

        SavingAccount account = null;
        initialBalance = maxBalance + 1;

        try {
            account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);
        } catch (IllegalArgumentException e) {

            String expectedMessage = "Начальный баланс не может быть больше максимального, а у вас: " + initialBalance;
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
        Assertions.assertEquals(null, account); // если дошло до этого ассерта, значит проверяемое условие не реализовано
    }
    //----------------------------------------------------------------------------------------------------------------------------\\

    @ParameterizedTest
    @ValueSource(ints = {1, 999, 1000})
    public void paymentSuccessTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult = true;
        int expectedBalance = initialBalance - amount;

        boolean actualResult = account.pay(amount);
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1001})
    public void paymentFailTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult = false;
        int expectedBalance = initialBalance;

        boolean actualResult = account.pay(amount);
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7999, 8000})
    public void addSuccessTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult = true;
        int expectedBalance = initialBalance + amount;

        boolean actualResult = account.add(amount);
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 8001})
    public void addFailTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult = false;
        int expectedBalance = initialBalance;

        boolean actualResult = account.add(amount);
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }


    @Test
    public void yearChange() {

        initialBalance = 2643;
        rate = 15;

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        int expectedChange = (int) ((double) initialBalance / 100 * rate);
        int actualChange = account.yearChange();

        Assertions.assertEquals(expectedChange, actualChange);
    }
}