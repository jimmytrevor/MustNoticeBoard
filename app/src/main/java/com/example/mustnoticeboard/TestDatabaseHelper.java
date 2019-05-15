package com.example.mustnoticeboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.jar.Attributes;

import static android.support.constraint.Constraints.TAG;

public class TestDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="test.db";
    public static final String PASSWORD="PASSWORD";
    public static final String NAME="NAME";
    public static final String EMAIL="EMAIL";
    public static final String AGE="AGE";
    String cols[]={NAME,EMAIL,PASSWORD};
    public static final String TABLE_NAME="users";


    public static final  int VERSION=1;
    public TestDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    public void onCreate(SQLiteDatabase db){
//       db.execSQL("CREATE TABLE "+TABLE_NAME+"("+PASSWORD+" integer primary key autoincrement not null, "+NAME +" text not null, "+PASSWORD +" text not null ,"+EMAIL +" text not null"+")");
db.execSQL("CREATE table users(id integer primary key autoincrement not null, NAME varchar(100) not null,EMAIL varchar(100) not null,PASSWORD varchar(100) not null)");
    }
    public void onUpgrade(SQLiteDatabase db,int newVersion,int oldVersion){
        db.execSQL("DROP table if  exists users");
        onCreate(db);
    }
    public boolean sendData(String name,String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(EMAIL,email);
        contentValues.put(PASSWORD,password);
    long send=db.insert(TABLE_NAME,null,contentValues);

    if (send==-1)
        return false;
    else
        return true;

    }
    public boolean getData(String email, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from "+TABLE_NAME +" where EMAIL=? AND PASSWORD=?",new String[]{email,password});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkEmailExistance(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor courso=db.rawQuery("SELECT EMAIL from "+TABLE_NAME +" where EMAIL =?",new String[]{email});
        if (courso.getCount()>0){
            return true;
        }
        else{
          return false;
        }
    }


}