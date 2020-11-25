package Anchovy;

/**
 * @author Anchovy
 * @date 2020/11/18 16:06
 * 枚举类型，8种类型的业务
 */
public enum BusinessType {
    DEPOSIT,WITHDRAW,PAY_PANALTY,OPEN_ONLINEBANK,PAY_UTILITIES,BUY_FOUD,TRANSFER,LOAN_PAYMENT;
    @Override
    public String toString() {
        String name = null;
        switch (this) {
            case DEPOSIT :
                name = "个人存款";
                break;
            case WITHDRAW :
                name = "个人取款";
                break;
            case PAY_PANALTY :
                name = "缴纳罚款";
                break;
            case OPEN_ONLINEBANK :
                name = "开通网银" ;
                break;
            case PAY_UTILITIES :
                name = "交水电费" ;
                break;
            case BUY_FOUD :
                name = "购买基金" ;
                break;
            case TRANSFER :
                name = "转账汇款" ;
                break;
            case LOAN_PAYMENT :
                name = "个贷还款" ;
                break;
        }
        return name;
    }
}
