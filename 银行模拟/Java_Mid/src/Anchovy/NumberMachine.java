package Anchovy;

/**
 * @author Anchovy
 * @date 2020/11/18 18:49
 * 为生成的用户排号
 */
public class NumberMachine {
    // 私有的构造函数，保证该函数只能在这个类中调用。这个类之外的任何类都不能调用它，因为是私有的、不可见。
    // 这意味着任何类都不能创建该类的对象。只有这个类自己才能创建
    private NumberMachine() {
    }
    // 创建该类自己的1个对象。这个对象就是这个类的单个实例，简称单例
    private static NumberMachine instance = new NumberMachine();

    // 对外提供一个public的静态方法，以供其它类来获得这个单例对象。就是说，任何其它类想要得到该类的对象，只能通过这个方法得到
    public static NumberMachine getInstance() {
        return instance;
    }

    // 创建3个号码生成器对象，分别为普通客户、快速客户和VIP客户单独排号
    private NumberGenerator commnGenerator = new NumberGenerator();
    private NumberGenerator quickGenerator = new NumberGenerator();
    private NumberGenerator vipGenerator = new NumberGenerator();

    public static void setInstance(NumberMachine instance) {
        NumberMachine.instance = instance;
    }

    public NumberGenerator getCommnGenerator() {
        return commnGenerator;
    }

    public void setCommnGenerator(NumberGenerator commnGenerator) {
        this.commnGenerator = commnGenerator;
    }

    public NumberGenerator getQuickGenerator() {
        return quickGenerator;
    }

    public void setExpressGenerator(NumberGenerator quickGenerator) {
        this.quickGenerator = quickGenerator;
    }

    public NumberGenerator getVipGenerator() {
        return vipGenerator;
    }

    public void setVipGenerator(NumberGenerator vipGenerator) {
        this.vipGenerator = vipGenerator;
    }
}
