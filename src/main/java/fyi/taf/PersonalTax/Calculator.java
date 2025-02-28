package fyi.taf.PersonalTax;

/**
 * 个人所得税计算器
 * 5 个 threshold (起征点) 对应 5 个 level，每个 level 对应一个 rate (税率)
 * @author fuuzen
 * @version 1.0.0
 * @since 2025-2-28
 */
public class Calculator {

    /**
     * Calculator constructor
     */
    public Calculator() {};

    /**
     * threshold (起征点)
     * 默认第一个税级起征点为 0
     */
    double[] taxThresholds = {0, 500, 2000, 5000, 20000};

    /**
     * rate (税率)
     */
    double[] taxRates = {0.05, 0.1, 0.15, 0.2, 0.25};

    /**
     * 计算个人所得税
     * @param income 个人收入
     * @return 个人所得税的各级征税及最终结果
     */
    public double[] calculate(double income) {
        double[] tax = new double[6];
        tax[5] = 0;
        for (int i = 0; i < 4; ++i){
            if (income > taxThresholds[i]) {
                tax[i] = (Math.min(income, taxThresholds[i + 1]) - taxThresholds[i]) * taxRates[i];
                tax[5] += tax[i];
            }
        }
        if (income > taxThresholds[4]) {
            tax[4] = (income - taxThresholds[3]) * taxRates[4];
            tax[5] += tax[4];
        }
        return tax;
    }

    /**
     * 将给定 level 的起征点调整为给定的 threshold
     * @param level 要调整的 level, 范围是 0 &lt;= level &lt; 5
     * @param threshold 新的 threshold
     */
    public void adjustTaxThreshold(int level, double threshold) {
        taxThresholds[level] = threshold;
    }

    /**
     * 将给定 level 的税率调整为给定的 rate
     * @param level 要调整的 level
     * @param rate 新的 rate
     */
    public void adjustTaxRate(int level, double rate) {
        taxRates[level] = rate;
    }
}
