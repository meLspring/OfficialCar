package bean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class PhoneBean {
    private String phone_name;
    private String phone_number;
    private String phone_rescue;        //24小时救援电话

    public String getPhone_name() {
        return phone_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPhone_rescue() {
        return phone_rescue;
    }

    public void setPhone_name(String phone_name) {
        this.phone_name = phone_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPhone_rescue(String phone_rescue) {
        this.phone_rescue = phone_rescue;
    }
}
