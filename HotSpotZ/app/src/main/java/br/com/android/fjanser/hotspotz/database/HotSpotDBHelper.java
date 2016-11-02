package br.com.android.fjanser.hotspotz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HotSpotDBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "dbHotSpotZ";
    private static final int DB_VERSION = 1;

    public HotSpotDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+ HotSpotContract.TABLE_NAME +" (" +
                        HotSpotContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        HotSpotContract.COL_ENDERECO  + " TEXT, "+
                        HotSpotContract.COL_PAIS  + " TEXT, "+
                        HotSpotContract.COL_LATITUDE  + " REAL, "+
                        HotSpotContract.COL_LONGITUDE + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
