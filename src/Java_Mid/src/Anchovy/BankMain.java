package Anchovy;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Anchovy
 * @date 2020/11/20 21:18
 * 主程序部分
 */
public class BankMain {
    public static void main(String[] args) {
        System.out.println("服务基准时间默认为2.");
        float baseTime = (float) 2.0;
        ServiceTime serviceTime = new ServiceTime(baseTime);

        //生成一个普通窗口
        ServiceWindow commonWindow = new ServiceWindow();
        commonWindow.setType(CustomerType.COMMON);
        commonWindow.setWindowID(1);
        commonWindow.setServiceTime(serviceTime);

        //生成两个快速窗口
        ServiceWindow quickWindow1 = new ServiceWindow();
        quickWindow1.setType(CustomerType.QUICK);
        quickWindow1.setWindowID(2);
        quickWindow1.setServiceTime(serviceTime);

        ServiceWindow quickWindow2 = new ServiceWindow();
        quickWindow2.setType(CustomerType.QUICK);
        quickWindow2.setWindowID(3);
        quickWindow2.setServiceTime(serviceTime);

        //生成一个会员窗口
        ServiceWindow vipWindow = new ServiceWindow();
        vipWindow.setType(CustomerType.VIP);
        vipWindow.setWindowID(4);
        vipWindow.setServiceTime(serviceTime);

        //准备开门
        System.out.println("银行准备开门.");
        //模拟
        LocalTime now = LocalTime.parse("07:59:57");
        while(true){
            if(now.equals(LocalTime.parse(ServiceTime.OPEN_DOOR_TIME))){
                break;
            }else{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            now = now.plusSeconds(1);
        }
        System.out.println("");
        System.out.println("----------银行开门营业----------");
        //开启线程
        commonWindow.start();
        quickWindow1.start();
        quickWindow2.start();
        vipWindow.start();
        //4s生成一个普通用户
        ScheduledExecutorService commCustomer = Executors.newScheduledThreadPool(1);

        commCustomer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                LocalTime now = LocalTime.parse("08:00:00");
                int index = random.nextInt(1000);
                int x =0;
                if(index >= 0 && index <200) x = 0;
                else if(index >= 200 && index <400) x= 1;
                else if(index >= 400 && index <500) x= 2;
                else if(index >= 500 && index <600) x= 3;
                else if(index >= 600 && index <650) x= 4;
                else if(index >= 650 && index <800) x= 5;
                else if(index >= 800 && index <900) x= 6;
                else if(index >= 900 && index <1000) x= 7;
                BusinessType businessType = BusinessType.class.getEnumConstants()[x];
                index = random.nextInt(1000);
                String customerName = "普通仔" + index;

                LocalTime arrivingTime = now.plusHours(random.nextInt(9)).plusSeconds(index).withNano(0);
                Integer serviceNumber = NumberMachine.getInstance().getCommnGenerator().generateServiceNumber(customerName,arrivingTime,businessType);
                System.out.println("---------- C"+serviceNumber + "号普通用户等待" + businessType + "服务...");
            }
        },0, 4, TimeUnit.SECONDS);


        //2s生成一个快速用户
        ScheduledExecutorService quickCustomer = Executors.newScheduledThreadPool(1);
        quickCustomer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int index;
                int x =5;
                LocalTime now = LocalTime.parse("08:00:00");
                Random random = new Random();
                do{

                    index = random.nextInt(1000);
                    if(index >= 0 && index <200) x = 0;
                    else if(index >= 200 && index <400) x= 1;
                    else if(index >= 400 && index <500) x= 2;
                    else if(index >= 500 && index <600) x= 3;
                    else if(index >= 600 && index <650) x= 4;
                    else if(index >= 650 && index <800) x= 5;
                    else if(index >= 800 && index <900) x= 6;
                    else if(index >= 900 && index <1000) x= 7;
                }while(x==2 || x==5 || x==7 );
                BusinessType businessType = BusinessType.class.getEnumConstants()[x];
                index = new Random().nextInt(1000);
                String customerName = "快速崽"+ index;
                LocalTime arrivingTime = now.plusHours(random.nextInt(9)).plusSeconds(index).withNano(0);
                Integer serviceNumber = NumberMachine.getInstance().getQuickGenerator().generateServiceNumber(customerName,arrivingTime,businessType);
                System.out.println("---------- Q" + serviceNumber + "号快速用户等待" + businessType + "服务...");
            }
        },0,2,TimeUnit.SECONDS);


        //8s生成一个会员用户
        ScheduledExecutorService vipCustomer = Executors.newScheduledThreadPool(1);
        vipCustomer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int index = random.nextInt(1000);
                LocalTime now = LocalTime.parse("08:00:00");
                int x =0;
                if(index >= 0 && index <200) x = 0;
                else if(index >= 200 && index <400) x= 1;
                else if(index >= 400 && index <500) x= 2;
                else if(index >= 500 && index <600) x= 3;
                else if(index >= 600 && index <650) x= 4;
                else if(index >= 650 && index <800) x= 5;
                else if(index >= 800 && index <900) x= 6;
                else if(index >= 900 && index <1000) x= 7;
                BusinessType businessType = BusinessType.class.getEnumConstants()[x];
                index = random.nextInt(1000);
                String customerName = "会员哥" + index;
                LocalTime arrivingTime = now.plusHours(random.nextInt(9)).plusSeconds(index).withNano(0);
                Integer serviceNumber = NumberMachine.getInstance().getVipGenerator().generateServiceNumber(customerName,arrivingTime,businessType);
                System.out.println("---------- V" + serviceNumber + "号会员用户等待" + businessType + "服务...");
            }
        },0,8,TimeUnit.SECONDS);

        now = LocalTime.parse("16:58:06");
        while(true){
            if(now.equals(LocalTime.parse(ServiceTime.CLOSE_DOOR_TIME))){
                break;
            }else{
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            now = now.plusSeconds(1);
        }

        //停止客户线程
        stop(commCustomer);
        stop(quickCustomer);
        stop(vipCustomer);

        //停止窗口服务线程
        commonWindow.stop();
        quickWindow1.stop();
        quickWindow2.stop();
        vipWindow.stop();


        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            //e.printStackTrace();
        }
        System.out.println("---------- 银行关门结账 ----------");
        System.out.println("");

        commonWindow.printCustomerReport();
        quickWindow1.printCustomerReport();
        quickWindow2.printCustomerReport();
        vipWindow.printCustomerReport();

        System.out.println("---------- 具体数据统计 ----------");
        System.out.println("");

        float totalTime = commonWindow.getCustomerTotalTime() + quickWindow1.getCustomerTotalTime() + quickWindow2.getCustomerTotalTime() + vipWindow.getCustomerTotalTime();
        int totalCustomer = commonWindow.getCustomerCount() + quickWindow1.getCustomerCount() + quickWindow2.getCustomerCount() + vipWindow.getCustomerCount();
        System.out.println("共有 " +totalCustomer + " 名客户进行业务办理.");
        System.out.println("当天的所有客户平均办理时间：" + String.format("%4.2f",totalTime/totalCustomer) + "分钟");

        HashMap<String, Integer> commonMap = commonWindow.getBusinessNumber();
        HashMap<String, Integer> quickMap1 = quickWindow1.getBusinessNumber();
        HashMap<String, Integer> quickMap2 = quickWindow2.getBusinessNumber();
        HashMap<String, Integer> vipMap = vipWindow.getBusinessNumber();

        HashMap<String, Integer> totalMap = mergeMultipleMap(commonMap,quickMap1,quickMap2,vipMap);
        for (Map.Entry<String, Integer> me: totalMap.entrySet()) {
            String key = me.getKey();
            Integer value = me.getValue();
            System.out.printf("办理%s业务： %d人\n",key,value);
        }

        float sum = (float) 0.0;
        Collection<Integer> values = totalMap.values();
        for (Integer v: values) {
            sum = sum + v;
        }

        System.out.println("");
        System.out.println("不同业务在所有办理业务中所占的比例:");
        System.out.printf("%8s %8s","业务类型","占比");
        System.out.println("");
        System.out.println("--------------------------");

        for (Map.Entry<String, Integer> me: totalMap.entrySet()) {
            String key = me.getKey();
            Integer value = me.getValue();
            System.out.printf("%8s %8s",key,String.format("%.0f",(value/sum)*100));
            System.out.println("%");
        }


    }

    public static void stop(ScheduledExecutorService customerThread){
        try {
            customerThread.shutdown();
            if(!customerThread.awaitTermination(1,TimeUnit.SECONDS)){
                customerThread.shutdown();
            }
        }catch (InterruptedException e){
            System.out.println("cjb!");
            customerThread.shutdownNow();
        }
        System.out.println(" 客户线程停止！ ");
    }

    public static HashMap<String,Integer> mergeMultipleMap(HashMap<String,Integer>...maps){
        List<HashMap<String, Integer>> list = Arrays.asList(maps);
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isEmpty() || list.get(i) == null){
                continue;
            }else{
                for (String key: list.get(i).keySet()) {
                    if(result.containsKey(key)){
                        result.put(key,result.get(key) + list.get(i).get(key));
                    }else{
                        result.put(key,list.get(i).get(key));
                    }
                }
            }
        }
        return result;
    }


}
