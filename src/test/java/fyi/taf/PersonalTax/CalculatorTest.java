package fyi.taf.PersonalTax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Calculator
 */
public class CalculatorTest {

    /**
     * 比较两个 ArrayList<Double> 在 0.01 误差范围内是否相等
     * @param a 实际结果
     * @param b 期望结果
     */
    public void cmpArrListDouble(
        ArrayList<Double> a,
        ArrayList<Double> b
    ) {
        assertTrue(
            a.size() == b.size(),
            "Expected value: " + b.size() + ", but was: " + a.size()
        );
        for (int i = 0; i < a.size(); i++) {
            assertTrue(
                Math.abs(a.get(i) - b.get(i)) < 0.01,
                String.format(
                    "Expected value: %.2f ~ %.2f, but was: %.2f",
                    b.get(i) - 0.01,
                    b.get(i) + 0.01,
                    a.get(i)
                )
            );
        }
    }

    @Test
    public void shouldCalculateCorrectly() {
        Calculator calculator = new Calculator();
        cmpArrListDouble(
            calculator.calculate(0),
            new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
        );
        cmpArrListDouble(
            calculator.calculate(500),
            new ArrayList<Double>(Arrays.asList(25.0, 0.0, 0.0, 0.0, 0.0, 25.0))
        );
        cmpArrListDouble(
            calculator.calculate(2000),
            new ArrayList<Double>(Arrays.asList(25.0, 150.0, 0.0, 0.0, 0.0, 175.0))
        );
        cmpArrListDouble(
            calculator.calculate(5000),
            new ArrayList<Double>(Arrays.asList(25.0, 150.0, 450.0, 0.0, 0.0, 625.0))
        );
        cmpArrListDouble(
            calculator.calculate(20000),
            new ArrayList<Double>(Arrays.asList(25.0, 150.0, 450.0, 3000.0, 0.0, 3625.0))
        );
        cmpArrListDouble(
            calculator.calculate(114514),
            new ArrayList<Double>(Arrays.asList(25.0, 150.0, 450.0, 3000.0, 23628.5, 27253.5))
        );
    }

    @Test
    public void shouldCalculateCorrectlyAfterAdjust() {
        Calculator calculator = new Calculator();
        calculator.adjustTaxThreshold(0, 1);
        calculator.adjustTaxThreshold(1, 11);
        calculator.adjustTaxThreshold(2, 114);
        calculator.adjustTaxThreshold(3, 1145);
        calculator.adjustTaxThreshold(4, 11451);
        cmpArrListDouble(
            calculator.calculate(114514),
            new ArrayList<Double>(Arrays.asList(0.5, 10.3, 154.65, 2061.2, 25765.75, 27992.4))
        );
        calculator.adjustTaxRate(0, 0.01);
        calculator.adjustTaxRate(1, 0.11);
        calculator.adjustTaxRate(2, 0.14);
        calculator.adjustTaxRate(3, 0.15);
        calculator.adjustTaxRate(4, 0.45);
        cmpArrListDouble(
            calculator.calculate(114514),
            new ArrayList<Double>(Arrays.asList(0.10, 11.33, 144.34, 1545.90, 46378.35, 48080.02))
        );
    }
}
