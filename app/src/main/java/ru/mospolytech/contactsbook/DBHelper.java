package ru.mospolytech.contactsbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contactdb.sqlite";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID  = "id";
    public static final String CONTACTS_COLUMN_SURNAME = "surname";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_SECNAME = "secname";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_EMAIL = "email";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key autoincrement, surname text, name text,secname text,phone text,email text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
    public boolean addContact(String contactsurname,String contactname,String contactsecname,String contactphone,String contactemail){
        /*,*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("surname",contactsurname);
        contantValues.put("name",contactname);
        contantValues.put("secname",contactsecname);
        contantValues.put("phone", contactphone);
        contantValues.put("email",contactemail);
        db.insert("contacts", null, contantValues);
        db.close();
        return true;
    }
    public boolean updateContact(Integer contactid,String contactsurname,String contactname,String contactsecname,String contactphone,String contactemail)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("surname",contactsurname);
        contantValues.put("name",contactname);
        contantValues.put("secname",contactsecname);
        contantValues.put("phone", contactphone);
        contantValues.put("email",contactemail);
        db.update("contacts", contantValues, "id = ?", new String[]{Integer.toString(contactid)});
        db.close();
        return true;
    }
    public Integer deleteContact(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("contacts","id = ?",new String[]{Integer.toString(id)});
    }
    public Cursor getData(int contactid){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from contacts where id = " + contactid + "", null);
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db=this.getWritableDatabase();
        int numRows=(int) DatabaseUtils.queryNumEntries(db,CONTACTS_TABLE_NAME);
        return numRows;
    }
    public ArrayList<String> getAllContacts(){
        ArrayList<String> arraylist= new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from contacts",null);

        if (cursor.moveToFirst()) {
            do {
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));

            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}
