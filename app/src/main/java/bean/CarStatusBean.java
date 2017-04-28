package bean;

/**
 * Created by Lspring on 2017/4/21.
 */

//车辆状况实体类
public class CarStatusBean {
    private String question;        //具体问题
    private boolean normal;         //正常

    public String getQuestion() {
        return question;
    }

    public boolean isNormal() {
        return normal;
    }



    public void setQuestion(String question) {

        this.question = question;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

}
