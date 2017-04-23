package bean;

/**
 * Created by Lspring on 2017/4/20.
 */

public class BiaodiBean {
    private String values;  //表底值
    private String number;
    private String time;        //时间值

    public String getValues() {
        return values;
    }



    public String getTime() {
        return time;
    }

    public void setValues(String values) {

        this.values = values;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String nmber) {

        this.number = nmber;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
