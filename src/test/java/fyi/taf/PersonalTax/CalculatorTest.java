package fyi.taf.PersonalTax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Calculator
 */
public class CalculatorTest {

    public void myAssertTrue(
        double[] a,
        double taxRank1,
        double taxRank2,
        double taxRank3,
        double taxRank4,
        double taxRank5,
        double taxTotal
    ) {
        assertTrue(Math.abs(a[0] - taxRank1) < 0.01);
        assertTrue(Math.abs(a[1] - taxRank2) < 0.01);
        assertTrue(Math.abs(a[2] - taxRank3) < 0.01);
        assertTrue(Math.abs(a[3] - taxRank4) < 0.01);
        assertTrue(Math.abs(a[4] - taxRank5) < 0.01);
        assertTrue(Math.abs(a[5] - taxTotal) < 0.01);
    }

    @Test
    public void shouldCalculateCorrectly() {
        Calculator calculator = new Calculator();
        myAssertTrue(calculator.calculate(0), 0, 0, 0, 0, 0, 0);
        myAssertTrue(calculator.calculate(500), 25, 0, 0, 0, 0, 25);
        myAssertTrue(calculator.calculate(2000), 25, 150, 0, 0, 0, 175);
        myAssertTrue(calculator.calculate(5000), 25, 150, 450, 0, 0, 625);
        myAssertTrue(calculator.calculate(20000), 25, 150, 450, 3000, 0, 3625);
        myAssertTrue(calculator.calculate(114514), 25, 150, 450, 3000, 27378.5, 31003.5);
        calculator.adjustTaxThreshold(0, 1);
        calculator.adjustTaxThreshold(1, 11);
        calculator.adjustTaxThreshold(2, 114);
        calculator.adjustTaxThreshold(3, 1145);
        calculator.adjustTaxThreshold(4, 11451);
        myAssertTrue(calculator.calculate(114514), 0.5, 10.3, 154.65, 2061.2, 28342.25, 30568.9);
        calculator.adjustTaxRate(0, 0.01);
        calculator.adjustTaxRate(1, 0.11);
        calculator.adjustTaxRate(2, 0.14);
        calculator.adjustTaxRate(3, 0.15);
        calculator.adjustTaxRate(4, 0.45);
        myAssertTrue(calculator.calculate(114514), 0.10, 11.33, 144.34, 1545.90, 51016.05, 52717.72);
    }
}
