package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SavingAccountTest {

    int minBalance = 1_000;                     //
    int initialBalance = minBalance + 1_000;    // изменяя эти поля можно нарушить актуальность
    int maxBalance = minBalance + 9_000;        //          параметризованных тестов
    int rate = 5;                               //

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
            "1,2,3,0"
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
    @ValueSource(ints = {-1, 0, 1, 999, 1000, 1001})
    public void paymentTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult;
        int expectedBalance;

        if ((initialBalance - amount) < minBalance || amount <= 0) {

            expectedResult = false;
            expectedBalance = initialBalance;
        } else {
            expectedResult = true;
            expectedBalance = initialBalance - amount;
        }
        boolean actualResult = account.pay(amount);
        int actualBalance = account.getBalance();

        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 7999, 8000, 8001})
    public void addTest(int amount) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean expectedResult;
        int expectedBalance;

        if ((initialBalance + amount) > maxBalance || amount <= 0) {

            expectedResult = false;
            expectedBalance = initialBalance;
        } else {
            expectedResult = true;
            expectedBalance = initialBalance + amount;
        }
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

        int expectedChange = initialBalance * rate / 100;
        int actualChange = account.yearChange();

        Assertions.assertEquals(expectedChange, actualChange);
    }
}