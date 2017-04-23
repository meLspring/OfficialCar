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
        db.execSQL("create table if not exists myUser(_id integer primary key autoincrement," +
                "userId,token)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            db.execSQL("drop table if exists myUser");
            onCreate(db);
        }
    }
}
