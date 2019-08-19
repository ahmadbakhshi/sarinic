package ir.sarinic.www.appmorghdari.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tbl_items";
    SQLiteDatabase mydb;
    private static String DATABASE_NAME = "sarinic_db";
    public final String CREATE_TABLE = "CREATE TABLE IF  NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, NAME TEXT,STATUS INTEGER,TYPE INTEGER);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //---------------"tbl_items" methods-----------------//

    /*
     * insert an items
     */
    public void insertItem(int id, String name, int status, int type) {
        mydb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("NAME", name);
        values.put("STATUS", status);
        values.put("TYPE", type);

        mydb.insert(TABLE_NAME, null, values);
    }


    /*
     * delete an items
     */
    public void deleteItem(int id) {
        mydb = this.getWritableDatabase();
        mydb.delete(TABLE_NAME, "ID" + " = ?",
                new String[]{String.valueOf(id)});
    }

    /*
     * update an items
     */
    public void updateItem
    (String name, int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("NAME", name);
        values.put("STATUS", status);

        // updating row
        db.update(TABLE_NAME, values, "ID" + " = ?", new String[]{String.valueOf(id)});
    }

    /*
     * update an items
     */
    public void updateSms
    (int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update tbl_items set STATUS=" + status + " where ID=" + id);
    }


}
