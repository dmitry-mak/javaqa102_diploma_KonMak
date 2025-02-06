package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SavingAccountTest {

    /**
     * Под каждым параметризованным тестом оставил тест-Template, чтобы можно было прогнать проверку по конкретным параметрам,
     * а не весь тест целиком. Перед финальной проверкой убедиться, что набор данных в этих тестах не вызовет ошибку,
     * или просто закомментировать.
     */
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
    //-------------------------------------------------------------------------------------------\\

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

    @Test
    public void createSavingAccountTestTemplate() {

        initialBalance = 0;
        minBalance = 0;
        maxBalance = 0;
        rate = 0;

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        Assertions.assertEquals(initialBalance, account.getBalance());
        Assertions.assertEquals(minBalance, account.getMinBalance());
        Assertions.assertEquals(maxBalance, account.getMaxBalance());
        Assertions.assertEquals(rate, account.getRate());
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
    @CsvSource(value = {
            "-1,false,2000",
            "0,false,2000",
            "1,true,1999",
            "999,true,1001",
            "1000,true,1000",
            "1001,false,2000"
    })
    public void paymentTest(int amount, boolean expectedResult, int expectedBalance) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean actualResult = account.pay(amount);
        Assertions.assertEquals(expectedResult, actualResult);

        int actualBalance = account.getBalance();
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void paymentTestTemplate() {

        int amount = 1;
        boolean expectedResult = true;
        int expectedBalance = 1999;

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean actualResult = account.pay(amount);
        Assertions.assertEquals(expectedResult, actualResult);

        int actualBalance = account.getBalance();
        Assertions.assertEquals(expectedBalance, actualBalance);
    }
    //----------------------------------------------------------------------------------------------------\\

    @ParameterizedTest
    @CsvSource(value = {
            "-1,false,2000",
            "0,false,2000",
            "1,true,2001",
            "7999,true,9999",
            "8000,true,10000",
            "8001,false,2000"
    })
    public void addTest(int amount, boolean expectedResult, int expectedBalance) {

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean actualResult = account.add(amount);
        Assertions.assertEquals(expectedResult, actualResult);

        int actualBalance = account.getBalance();
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void addTestTemplate() {

        int amount = 1;
        boolean expectedResult = true;
        int expectedBalance = 2001;

        SavingAccount account = new SavingAccount(initialBalance, minBalance, maxBalance, rate);

        boolean actualResult = account.add(amount);
        Assertions.assertEquals(expectedResult, actualResult);

        int actualBalance = account.getBalance();
        Assertions.assertEquals(expectedBalance, actualBalance);
    }
    //---------------------------------------------------------------------------------------------\\

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