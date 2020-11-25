package Anchovy;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Anchovy
 * @date 2020/11/19 21:45
 * 服务窗口类
 */
public class ServiceWindow {
    private CustomerType type;
    private int windowID;
    private String windowName;
    private ServiceTime serviceTime;
    private List<CustomerRecord> customerRecordList = new ArrayList<CustomerRecord>();
    private HashMap<String,Integer> businessNumberMap = new HashMap<String, Integer>();
    private ScheduledExecutorService callNumber;

    //getter and setter
    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public int getWindowID() {
        return windowID;
    }

    public void setWindowID(int windowID) {
        this.windowID = windowID;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public ServiceTime getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(ServiceTime serviceTime) {
        this.serviceTime = serviceTime;
    }

    public List<CustomerRecord> getCustomerRecordList() {
        return customerRecordList;
    }

    public void setCustomerRecordList(List<CustomerRecord> customerRecordList) {
        this.customerRecordList = customerRecordList;
    }

    public HashMap<String, Integer> getBusinessNumberMap() {
        return businessNumberMap;
    }

    public void setBusinessNumberMap(HashMap<String, Integer> businessNumberMap) {
        this.businessNumberMap = businessNumberMap;
    }

    public ScheduledExecutorService getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(ScheduledExecutorService callNumber) {
        this.callNumber = callNumber;
    }

    //叫号
    public void start(){
        //创建1个单独的线程
        callNumber = Executors.newScheduledThreadPool(1);
        //每3秒执行1次
        callNumber.scheduleAtFixedRate(new Runnable() {
            public void run() {
                switch (type) {// 为不同的类型客户分配不同的方法进行服务
                    case COMMON:
                        commonService();
                        break;
                    case QUICK:
                        quickService();
                        break;
                    case VIP:
                        vipService();
                        break;
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    //停止叫号
    public void stop(){
        try {
            //通知线程终止
            callNumber.shutdown();
            //等待1秒钟，等线程执行完。所有的任务都结束的时候，返回TRUE
            if(!callNumber.awaitTermination(1, TimeUnit.SECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                callNumber.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted" );
            callNumber.shutdownNow();
        }
        System.out.println(" 窗口停止任务！");
    }

    //普通用户服务窗口
    private void commonService(){
        String windowName = windowID + "号" + type + "窗口";
        this.windowName = windowName;
        //System.out.println(windowName + " 正在获取普通任务...");

        String custmorInfo = NumberMachine.getInstance().getCommnGenerator().getServiceNumber();
        if(custmorInfo!=null){
            String serviceNumber = custmorInfo.split(",")[0];
            String businessType = custmorInfo.split(",")[1];
            String customerName = custmorInfo.split(",")[2];
            String arrivingTime = custmorInfo.split(",")[3];
            System.out.println(windowName + "开始为C" + serviceNumber + "号普通客户服务");

            float randomTime = 0;
            float consumingTime = 0;
            if(businessType.equals(BusinessType.DEPOSIT.toString())){
                randomTime = serviceTime.deposit_max_time - serviceTime.deposit_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.deposit_min_time;
            }else if(businessType.equals(BusinessType.WITHDRAW.toString())){
                randomTime = serviceTime.withdraw_max_time - serviceTime.withdraw_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.withdraw_min_time;
            }else if(businessType.equals(BusinessType.PAY_PANALTY.toString())){
                randomTime = serviceTime.pay_panalty_max_time - serviceTime.pay_panalty_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1 ) + serviceTime.pay_panalty_min_time;
            }else if(businessType.equals(BusinessType.OPEN_ONLINEBANK.toString())){
                randomTime = serviceTime.open_onlinebank_max_time - serviceTime.open_onlinebank_min_time;
                consumingTime = new Random().nextInt((int) randomTime + 1) + serviceTime.open_onlinebank_min_time;
            }else if(businessType.equals(BusinessType.PAY_UTILITIES.toString())){
                randomTime = serviceTime.pay_utilities_max_time - serviceTime.pay_utilities_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.pay_utilities_min_time;
            }else if(businessType.equals(BusinessType.BUY_FOUD.toString())){
                randomTime = serviceTime.buy_foud_max_time - serviceTime.buy_foud_min_time;
                consumingTime = new Random().nextInt((int)randomTime+1) + serviceTime.buy_foud_min_time;
            }else if(businessType.equals(BusinessType.TRANSFER.toString())){
                randomTime = serviceTime.transfer_max_time - serviceTime.transfer_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.transfer_min_time;
            }else if(businessType.equals(BusinessType.LOAN_PAYMENT.toString())){
                randomTime = serviceTime.loan_payment_max_time - serviceTime.loan_payment_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1) + serviceTime.loan_payment_min_time;
            }


            try{
                Thread.sleep((long)consumingTime * 100);
            }catch(InterruptedException e){
                //e.printStackTrace();
            }

            int waitTime =new Random().nextInt(4);
            float totalTime =  consumingTime + waitTime;

            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setCustomerName(customerName);
            customerRecord.setArrivingTime(arrivingTime);
            customerRecord.setBusinessType(businessType);
            customerRecord.setTimeUsing(consumingTime);
            customerRecord.setTotalTime(totalTime);

            customerRecordList.add(customerRecord);

            addToMap(businessType,1);

            System.out.println(windowName + "完成为C" + serviceNumber + "号普通客户服务，总共耗时" + consumingTime + "分钟");
        }else{
            //System.out.println(windowName + "窗口当前没有任务，为快速业务客户提供服务！");
            quickService();
        }
    }

    private void vipService(){
        String windowName = windowID + "号" + type + "窗口";

        this.windowName = windowName;
        //System.out.println(windowName + " 正在获取会员任务...");

        String custmorInfo = NumberMachine.getInstance().getVipGenerator().getServiceNumber();
        if(custmorInfo!=null){
            String serviceNumber = custmorInfo.split(",")[0];
            String businessType = custmorInfo.split(",")[1];
            String customerName = custmorInfo.split(",")[2];
            String arrivingTime = custmorInfo.split(",")[3];
            System.out.println(windowName + "开始为V" + serviceNumber + "号会员客户服务");

            float randomTime = 0;
            float consumingTime = 0;
            if(businessType.equals(BusinessType.DEPOSIT.toString())){
                randomTime = serviceTime.deposit_max_time - serviceTime.deposit_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.deposit_min_time;
            }else if(businessType.equals(BusinessType.WITHDRAW.toString())){
                randomTime = serviceTime.withdraw_max_time - serviceTime.withdraw_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.withdraw_min_time;
            }else if(businessType.equals(BusinessType.PAY_PANALTY.toString())){
                randomTime = serviceTime.pay_panalty_max_time - serviceTime.pay_panalty_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1 ) + serviceTime.pay_panalty_min_time;
            }else if(businessType.equals(BusinessType.OPEN_ONLINEBANK.toString())){
                randomTime = serviceTime.open_onlinebank_max_time - serviceTime.open_onlinebank_min_time;
                consumingTime = new Random().nextInt((int) randomTime + 1) + serviceTime.open_onlinebank_min_time;
            }else if(businessType.equals(BusinessType.PAY_UTILITIES.toString())){
                randomTime = serviceTime.pay_utilities_max_time - serviceTime.pay_utilities_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.pay_utilities_min_time;
            }else if(businessType.equals(BusinessType.BUY_FOUD.toString())){
                randomTime = serviceTime.buy_foud_max_time - serviceTime.buy_foud_min_time;
                consumingTime = new Random().nextInt((int)randomTime+1) + serviceTime.buy_foud_min_time;
            }else if(businessType.equals(BusinessType.TRANSFER.toString())){
                randomTime = serviceTime.transfer_max_time - serviceTime.transfer_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.transfer_min_time;
            }else if(businessType.equals(BusinessType.LOAN_PAYMENT.toString())){
                randomTime = serviceTime.loan_payment_max_time - serviceTime.loan_payment_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1) + serviceTime.loan_payment_min_time;
            }


            try{
                Thread.sleep((long)consumingTime * 100);
            }catch(InterruptedException e){
               // e.printStackTrace();
            }

            int waitTime =new Random().nextInt(2);
            float totalTime =  consumingTime + waitTime;

            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setCustomerName(customerName);
            customerRecord.setArrivingTime(arrivingTime);
            customerRecord.setBusinessType(businessType);
            customerRecord.setTimeUsing(consumingTime);
            customerRecord.setTotalTime(totalTime);

            customerRecordList.add(customerRecord);

            addToMap(businessType,1);

            System.out.println(windowName + "完成为V" + serviceNumber + "号会员客户服务，总共耗时" + consumingTime + "分钟");
        }else{
            //System.out.println(windowName + "窗口当前没有任务，为普通业务客户提供服务！");
            commonService();
        }
    }

    private void quickService(){
        String windowName = windowID + "号" + type + "窗口";

        this.windowName = windowName;
       // System.out.println(windowName + " 正在获取快速任务...");

        String custmorInfo = NumberMachine.getInstance().getQuickGenerator().getServiceNumber();
        if(custmorInfo!=null){
            String serviceNumber = custmorInfo.split(",")[0];
            String businessType = custmorInfo.split(",")[1];
            String customerName = custmorInfo.split(",")[2];
            String arrivingTime = custmorInfo.split(",")[3];
            System.out.println(windowName + "开始为Q" + serviceNumber + "号快速客户服务");

            float randomTime = 0;
            float consumingTime = 0;
            if(businessType.equals(BusinessType.DEPOSIT.toString())){
                randomTime = serviceTime.deposit_max_time - serviceTime.deposit_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.deposit_min_time;
            }else if(businessType.equals(BusinessType.WITHDRAW.toString())){
                randomTime = serviceTime.withdraw_max_time - serviceTime.withdraw_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.withdraw_min_time;
            }else if(businessType.equals(BusinessType.PAY_PANALTY.toString())){
                randomTime = serviceTime.pay_panalty_max_time - serviceTime.pay_panalty_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1 ) + serviceTime.pay_panalty_min_time;
            }else if(businessType.equals(BusinessType.OPEN_ONLINEBANK.toString())){
                randomTime = serviceTime.open_onlinebank_max_time - serviceTime.open_onlinebank_min_time;
                consumingTime = new Random().nextInt((int) randomTime + 1) + serviceTime.open_onlinebank_min_time;
            }else if(businessType.equals(BusinessType.PAY_UTILITIES.toString())){
                randomTime = serviceTime.pay_utilities_max_time - serviceTime.pay_utilities_min_time;
                consumingTime = new Random().nextInt((int)randomTime + 1) + serviceTime.pay_utilities_min_time;
            }else if(businessType.equals(BusinessType.BUY_FOUD.toString())){
                randomTime = serviceTime.buy_foud_max_time - serviceTime.buy_foud_min_time;
                consumingTime = new Random().nextInt((int)randomTime+1) + serviceTime.buy_foud_min_time;
            }else if(businessType.equals(BusinessType.TRANSFER.toString())){
                randomTime = serviceTime.transfer_max_time - serviceTime.transfer_min_time;
                consumingTime = new Random().nextInt((int)randomTime +1) + serviceTime.transfer_min_time;
            }else if(businessType.equals(BusinessType.LOAN_PAYMENT.toString())){
                randomTime = serviceTime.loan_payment_max_time - serviceTime.loan_payment_min_time;
                consumingTime = new Random().nextInt((int) randomTime +1) + serviceTime.loan_payment_min_time;
            }


            try{
                Thread.sleep((long)consumingTime * 100);
            }catch(InterruptedException e){
                //e.printStackTrace();
            }

            int waitTime =new Random().nextInt(3);
            float totalTime =  consumingTime + waitTime;

            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setCustomerName(customerName);
            customerRecord.setArrivingTime(arrivingTime);
            customerRecord.setBusinessType(businessType);
            customerRecord.setTimeUsing(consumingTime);
            customerRecord.setTotalTime(totalTime);

            customerRecordList.add(customerRecord);

            addToMap(businessType,1);

            System.out.println(windowName + "完成为Q" + serviceNumber + "号快速客户服务，总共耗时" + consumingTime + "分钟");
        }else{
            //System.out.println(windowName + "窗口当前没有任务！");
        }
    }

    public void printCustomerReport(){
        System.out.println("---------- " + this.windowName + " 日工作量报表----------" );
        System.out.printf("%-16s %-16s %-16s %-10s\n","客户名称","客户到达时间","客户办理业务类型","客户所用时间（分钟）");
        Collections.sort(customerRecordList, new Comparator<CustomerRecord>() {
            public int compare(CustomerRecord arg0, CustomerRecord arg1) {
                return arg0.getArrivingTime().compareTo(arg1.getArrivingTime());
            }
        });
        for (CustomerRecord customerRecord: customerRecordList ) {
            System.out.printf("%-16s   %-16s       %-16s      %-16.1f\n",customerRecord.getCustomerName(),customerRecord.getArrivingTime(),customerRecord.getBusinessType(),customerRecord.getTimeUsing());

        }
        System.out.println("--------------------");
        System.out.println("");
    }

    public int getCustomerCount(){
        return customerRecordList.size();
    }

    public float getCustomerTotalTime(){
        float sum = 0;
        for (CustomerRecord customerRecord: customerRecordList) {
            sum = sum + customerRecord.getTotalTime();
        }
        return sum;
    }

    public void addToMap(String key,Integer value){
        if(businessNumberMap.get(key) != null){
            businessNumberMap.put(key,businessNumberMap.get(key) + value );
        }else{
            businessNumberMap.put(key,value);
        }
    }
    public HashMap<String, Integer> getBusinessNumber(){
        return businessNumberMap;
    }
}
