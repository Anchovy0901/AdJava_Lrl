package Anchovy;

/**
 * @author Anchovy
 * @date 2020/11/18 14:29
 * 服务窗口使用的时间
 */
public class ServiceTime {
    public static String OPEN_DOOR_TIME = "08:00:00"; //开门时间
    public static String CLOSE_DOOR_TIME = "17:00:00";//关门时间

    //个人存款
    public float deposit_max_time;
    public float deposit_min_time;
    //个人取款
    public float withdraw_max_time;
    public float withdraw_min_time;
    //缴纳罚款
    public float pay_panalty_max_time;
    public float pay_panalty_min_time;
    //开通网银
    public float open_onlinebank_max_time;
    public float open_onlinebank_min_time;
    //交水电费
    public float pay_utilities_max_time;
    public float pay_utilities_min_time;
    //购买基金
    public float buy_foud_max_time;
    public float buy_foud_min_time;
    //转账汇款
    public float transfer_max_time;
    public float transfer_min_time;
    //个贷还款
    public float loan_payment_max_time;
    public float loan_payment_min_time;

    //设定基准时间
    public ServiceTime(float base_time){
        System.out.println("设定各类业务的基准时间为： " + base_time);
        this.deposit_max_time = (float) (1.5 * base_time);
        this.deposit_min_time = (float) (0.5 * base_time);
        this.withdraw_max_time = (float) (1.5 * base_time);
        this.withdraw_min_time = (float) (0.5 * base_time);
        this.pay_panalty_max_time = (float) (2.0 * base_time);
        this.pay_panalty_min_time = (float) (1.2 * base_time);
        this.open_onlinebank_max_time = (float) (8.0 * base_time);
        this.open_onlinebank_min_time = (float) (5.0 * base_time);
        this.pay_utilities_max_time = (float) (2.0 * base_time);
        this.pay_utilities_min_time = (float) (1.5 * base_time);
        this.buy_foud_max_time = (float) (3.0 * base_time);
        this.buy_foud_min_time = (float) (2.0 * base_time);
        this.transfer_max_time = (float) (4.0 * base_time);
        this.transfer_min_time = (float) (3.0 * base_time);
        this.loan_payment_max_time = (float) (4.0 * base_time);
        this.loan_payment_min_time = (float) (2.0 * base_time);
    }

}
