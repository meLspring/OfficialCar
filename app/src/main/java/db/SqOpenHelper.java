package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lspring on 2017/3/27.
 */

public class SqOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME="user.db";
    private static final int DB_VERSION=1;
    public SqOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //qrcode是判断当前是否扫描二维码，以及交接班后扫描变为没有扫描，1代表扫描，0代表没有扫描
        db.execSQL("create table if not exists myUser(_id integer primary key autoincrement," +
                "userId,token,qrcode integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            db.execSQL("drop table if exists myUser");
            onCreate(db);
        }
    }
}
