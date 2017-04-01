package bean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class RepairRecordBean {
    private String  orderNumber;       //序号
    private String name;            //名称
    private String time;            //时间
    private String mileage;     //里程
    private  String changeMileageTable;  //更换里程表
    private String settlement;          //结算金额

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public void setChangeMileageTable(String changeMileageTable) {
        this.changeMileageTable = changeMileageTable;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getMileage() {
        return mileage;
    }

    public String getChangeMileageTable() {
        return changeMileageTable;
    }

    public String getSettlement() {
        return settlement;
    }
}
