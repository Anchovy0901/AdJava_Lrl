package Anchovy;

/**
 * @author Anchovy
 * @date 2020/11/18 16:01
 * 枚举类型，3种类型的客户
 */
public enum CustomerType {
    COMMON,QUICK,VIP;
    @Override
    public String toString(){
        String name = null;
        switch (this){
            case COMMON:
                name = "普通";
                break;
            case QUICK:
                name = "快速";
                break;
            case VIP:
                name = "会员";
                break;
        }
        return name;
    }
}
