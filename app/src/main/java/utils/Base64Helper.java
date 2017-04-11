package utils;

//import android.util.Base64;
import android.util.Base64;

import java.io.IOException;

public class Base64Helper {

//    public static int flag;

    public static String encode(byte[] byteArray) {
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
//        sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
//        return base64Encoder.encode(byteArray);
    }

    public static byte[] decode(String base64EncodedString) {
        return Base64.decode(base64EncodedString, Base64.DEFAULT);
//        sun.misc.BASE64Decoder base64Decoder = new sun.misc.BASE64Decoder();
//        try {
//            return base64Decoder.decodeBuffer(base64EncodedString);
//        } catch (IOException e) {
//            return null;
//        }
    }
}