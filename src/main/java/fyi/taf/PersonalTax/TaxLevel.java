package fyi.taf.PersonalTax;

/**
 * 税级
 */
public class TaxLevel {

    /**
     * 起征点
     */
    private double threshold;

    /**
     * 税率
     */
    private double rate;

    /**
     * TaxLevel constructor
     * @param threshold 起征点
     * @param rate 税率
     */
    public TaxLevel(double threshold, double rate) {
        this.threshold = threshold;
        this.rate = rate;
    }

    /**
     * 获取起征点
     * @return 起征点
     */
    public double getThreshold() { return threshold; }

    /**
     * 获取税率
     * @return 税率
     */
    public double getRate() { return rate; }

    /**
     * 设置起征点
     * @param threshold
     */
    public void setThreshold(double threshold) {
      this.threshold = threshold;
    }

    /**
     * 设置税率
     * @param rate
     */
    public void setRate(double rate) {
      this.rate = rate;
    }

    /**
     * 重载 toString 方法
     * @return 税级的起征点和税率
     */
    @Override
    public String toString() {
        return "{ threshold = " + threshold + ", rate = " + rate + " }";
    }
}
