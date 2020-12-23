package Anchovy;

/**
 * @author Anchovy
 * @date 2020/11/18 16:22
 * 操作过的用户信息记录，服务于生成记录表单
 */
public class CustomerRecord {
    //顾客名字
    private String customerName;
    //顾客到达时间
    private String arrivingTime;
    //顾客类型
    private String businessType;
    //用的时间
    private float timeUsing;
    //总共时间（从到达到办理业务完毕所用时间）
    private float totalTime;


    //getter and setter
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(String arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public float getTimeUsing() {
        return timeUsing;
    }

    public void setTimeUsing(float timeUsing) {
        this.timeUsing = timeUsing;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }
}
