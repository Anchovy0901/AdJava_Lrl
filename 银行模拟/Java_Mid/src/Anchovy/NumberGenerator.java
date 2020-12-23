package Anchovy;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anchovy
 * @date 2020/11/18 18:20
 * 生成客户号码，递增
 */
public class NumberGenerator {
    //生成的号码，初值为0
    private int lastNumber = 0;
    //生成的号码的集合
    private List<Integer> numberList = new ArrayList<Integer>();
    //生成的客户名的集合
    private List<String> customerNameList = new ArrayList<String>();
    //生成客户到达时间的集合
    private List<LocalTime> arrivingTimeList = new ArrayList<LocalTime>();
    //生成客户办理业务类型的集合
    private List<BusinessType> businessTypeList = new ArrayList<BusinessType>();

    //生成号码，返回下一次生成的号码；采用同步机制；
    public synchronized Integer generateServiceNumber(String customerName, LocalTime arrivingTime, BusinessType businessType) {
        // 先生成，再将生成的号码存入集合
        lastNumber = lastNumber + 1;
        numberList.add(lastNumber);
        // 将客户线程传来的客户姓名/客户到达时间/业务类型分别保存到相应的集合中
        customerNameList.add(customerName);
        arrivingTimeList.add(arrivingTime);
        businessTypeList.add(businessType);
        return lastNumber;
    }

    //取出 号码、业务类型、客户姓名、客户到达时间；4个值用逗号连接起来
    public synchronized String getServiceNumber(){
        if (numberList.size() > 0)
            // remove方法：从集合中取走的号码并将其删除。下标0表示总是从集合的头部取号，
            // 这正好是客户的排号顺序
            return numberList.remove(0).toString() + "," + businessTypeList.remove(0).toString() + ","
                    + customerNameList.remove(0).toString() + "," + arrivingTimeList.remove(0).toString();
        return null;
    }
}
