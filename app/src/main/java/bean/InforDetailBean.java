package bean;

/**
 * Created by Lspring on 2017/3/23.
 */

//信息平台详情
public class InforDetailBean {
    private String detail_userName;
    private String showContent;
    private boolean detail_linear;

    public String getDetail_userName() {
        return detail_userName;
    }

    public String getShowContent() {
        return showContent;
    }

    public boolean isDetail_linear() {
        return detail_linear;
    }

    public void setDetail_userName(String detail_userName) {

        this.detail_userName = detail_userName;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public void setDetail_linear(boolean detail_linear) {
        this.detail_linear = detail_linear;
    }
}
