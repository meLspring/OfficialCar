package bean;

/**
 * Created by Lspring on 2017/3/23.
 */

//信息平台详情
public class InforDetailBean {
   private String title;        //标题
    private String user;        //用户
    private String time;        //时间
    private String content;     //内容
    private boolean isIcon;     //是否有图片
    private boolean isTitle;    //判断是否有title

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {

        isTitle = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public boolean isIcon() {
        return isIcon;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(boolean icon) {
        isIcon = icon;
    }
}
