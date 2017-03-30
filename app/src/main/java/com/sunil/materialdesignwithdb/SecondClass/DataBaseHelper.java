package com.sunil.materialdesignwithdb.SecondClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunil.materialdesignwithdb.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil on 28-03-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_USER_EMAIL,user.getEmail());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());

        db.insert(TABLE_USER,null,values);
        db.close();
    }
    public List<User>getAllUser() {
        String[] columns = {
                COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD
        };

        String sortOrder = COLUMN_USER_NAME + "ASC ";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,columns,null,null,null,null,sortOrder);

        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            }while (cursor.moveToFirst());
        }
        cursor.close();
        db.close();
        return userList;
    }
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_USER_EMAIL,user.getEmail());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());

        db.update(TABLE_USER,values,COLUMN_USER_ID + " =?",new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER,COLUMN_USER_ID + "=?",new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public boolean checkUser(String email){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection =  COLUMN_USER_EMAIL + "=?";
        String[] selectionArgs ={email};
        Cursor curser = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int curserCount = curser.getCount();
        db.close();
        if (curserCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=?"+"AND"+ COLUMN_USER_PASSWORD + "=?";
        String[] selectionArgs = {email,password};
        Cursor curser = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int cuserCount = curser.getCount();
        curser.close();
        db.close();
        if (cuserCount > 0){
            return true;
        }
        return false;
    }
}