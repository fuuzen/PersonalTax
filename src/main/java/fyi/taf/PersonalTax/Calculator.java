package fyi.taf.PersonalTax;

import java.util.ArrayList;

/**
 * 个人所得税计算器
 * 5 个 threshold (起征点) 对应 5 个 level，每个 level 对应一个 rate (税率)
 * @author fuuzen
 * @version 1.1.0
 * @since 2025-03-02
 */
public class Calculator {

    /**
     * 所有的税级
     */
    private ArrayList<TaxLevel> taxLevels;

    /**
     * Calculator constructor
     * 初始化 5 个 tax level
     */
    public Calculator() {
        taxLevels = new ArrayList<TaxLevel>() {{
            add(new TaxLevel(0, 0.05));
            add(new TaxLevel(500, 0.1));
            add(new TaxLevel(2000, 0.15));
            add(new TaxLevel(5000, 0.2));
            add(new TaxLevel(20000, 0.25));
        }};
    }

    /**
     * 添加新的税级
     * @param threshold 新的起征点
     * @param rate 新的税率
     * @throws IllegalArgumentException 如果 threshold 已存在则抛出异常
     */
    public void addTaxLevel(double threshold, double rate) throws IllegalArgumentException {
        taxLevels.forEach(item -> {
            if (item.getThreshold() == threshold) {
                throw new IllegalArgumentException("Tax level already exists!");
            }
        });
        taxLevels.add(new TaxLevel(threshold, rate));
    }

    /**
     * 删除一个指定税级, 规定至少要有一个税级, 不允许删除最后一个税级
     * @param level 要删除的税级
     * @throws IllegalArgumentException 如果 level 超出范围或者当前仅剩一个税级抛出异常
     */
    public void removeTaxLevel(int level) throws IllegalArgumentException {
        if (level < 0 || level >= taxLevels.size()) {
            throw new IllegalArgumentException("Tax level does not exist!");
        }
        if (taxLevels.size() == 1) {
            throw new IllegalArgumentException("Cannot remove the last tax level!");
        }
        taxLevels.remove(level);
    }

    /**
     * 计算个人所得税
     * @param income 个人收入
     * @return 个人所得税的各级征税及最终结果
     */
    public ArrayList<Double> calculate(double income) {
        int l = taxLevels.size();
        ArrayList<Double> taxes = new ArrayList<Double>() {{
            double taxTotal = 0, tax = 0;
            for (int i = 0; i < l - 1; ++i) {
                tax = 0;
                if (income > taxLevels.get(i).getThreshold()) {
                    tax = (Math.min(income, taxLevels.get(i + 1).getThreshold()) - taxLevels.get(i).getThreshold()) * taxLevels.get(i).getRate();
                    taxTotal += tax;
                }
                add(tax);
            }
            tax = 0;
            if (income > taxLevels.get(l - 1).getThreshold()) {
                tax = (income - taxLevels.get(l - 1).getThreshold()) * taxLevels.get(l - 1).getRate();
                taxTotal += tax;
            }
            add(tax);
            add(taxTotal);
        }};
        return taxes;
    }

    /**
     * 将给定 level 的起征点调整为给定的 threshold
     * @param level 要调整的 level, 范围是 0 &lt;= level &lt; 税级数量
     * @param threshold 新的 threshold
     */
    public void adjustTaxThreshold(int level, double threshold) {
        taxLevels.get(level).setThreshold(threshold);
    }

    /**
     * 将给定 level 的税率调整为给定的 rate
     * @param level 要调整的 level
     * @param rate 新的 rate
     */
    public void adjustTaxRate(int level, double rate) {
        taxLevels.get(level).setRate(rate);
    }

    /**
     * 获取税级数量
     * @return TaxLevel 数量
     */
    public int getTaxLevelCount() {
        return taxLevels.size();
    }

    /**
     * 获取指定 level 的税级 TaxLevel
     * @param level 要获取的 level
     * @return 指定 level 的 TaxLevel
     */
    public TaxLevel getTaxLevel(int level) {
        return taxLevels.get(level);
    }

    /**
     * 重载 toString 方法
     * @return 所有税级的起征点和税率
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i = 0; i < taxLevels.size(); ++i) {
            sb.append("\t").append(taxLevels.get(i).toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
