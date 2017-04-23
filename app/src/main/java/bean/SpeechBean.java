package bean;

/**
 * Created by Lspring on 2017/4/19.
 */

//语音类
public class SpeechBean {
    private float time;     //时间
    private String filePathString;      //路径

    public SpeechBean(float time, String filePathString) {
        super();
        this.time = time;
        this.filePathString = filePathString;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePathString() {
        return filePathString;
    }

    public void setFilePathString(String filePathString) {
        this.filePathString = filePathString;
    }

}
