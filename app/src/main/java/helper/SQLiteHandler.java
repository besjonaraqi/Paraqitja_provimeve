package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "paraqitja_provimeve.db";

    private static final String TABLE_USER = "user";
    private static final String TABLE_LENDA = "lenda";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_DEPARTAMENTI = "departamenti";
    private static final String KEY_KAMPUSI = "kampusi";
    private static final String KEY_DATELINDJA = "datelindja";
    private static final String KEY_TIPI = "tipi";
    private static final String KEY_INDEKSI = "indeksi";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    private static final String KEY_LENDA_ID = "l_id";
    private static final String KEY_EMRI = "emri";
    private static final String KEY_SEMESTRI_ID = "semestri_id";
    private static final String KEY_KREDI = "kredi";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createLendaTable(db);
    }

    private void createLendaTable(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LENDA + "("
                + KEY_LENDA_ID + " INTEGER PRIMARY KEY," + KEY_EMRI + " TEXT," + KEY_SEMESTRI_ID + " TEXT,"
                + KEY_KREDI + " INTEGER " + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    private void createUserTable(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LASTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT," + KEY_KAMPUSI + " TEXT," + KEY_DEPARTAMENTI + " TEXT,"
                + KEY_DATELINDJA + " TEXT," + KEY_TIPI + " TEXT," + KEY_INDEKSI + " TEXT,"
                + KEY_UPDATED_AT + " TEXT," + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }
    public void addUser(String name, String lastname, String email, String uid, String kampusi, String departamenti,
                        String indeksi, String tipi, String datelindja, String updated_at, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_LASTNAME, lastname);
        values.put(KEY_EMAIL, email);
        values.put(KEY_KAMPUSI, kampusi);
        values.put(KEY_INDEKSI, indeksi);
        values.put(KEY_DEPARTAMENTI, departamenti);
        values.put(KEY_TIPI, tipi);
        values.put(KEY_DATELINDJA, datelindja);
        values.put(KEY_UPDATED_AT, updated_at);
        values.put(KEY_UID, uid);
        values.put(KEY_CREATED_AT, created_at);

        long id = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put(KEY_LASTNAME, cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)));
            user.put(KEY_EMAIL, cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put(KEY_KAMPUSI, cursor.getString(cursor.getColumnIndex(KEY_KAMPUSI)));
            user.put(KEY_DEPARTAMENTI, cursor.getString(cursor.getColumnIndex(KEY_DEPARTAMENTI)));
            user.put(KEY_DATELINDJA, cursor.getString(cursor.getColumnIndex(KEY_DATELINDJA)));
            user.put(KEY_TIPI, cursor.getString(cursor.getColumnIndex(KEY_TIPI)));
            user.put(KEY_INDEKSI, cursor.getString(cursor.getColumnIndex(KEY_INDEKSI)));
            user.put(KEY_CREATED_AT, cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
            user.put(KEY_UPDATED_AT, cursor.getString(cursor.getColumnIndex(KEY_UPDATED_AT)));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getLendaDetails() {
        HashMap<String, String> lenda = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LENDA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            lenda.put(KEY_LENDA_ID, cursor.getString(cursor.getColumnIndex(KEY_LENDA_ID)));
            lenda.put(KEY_EMRI, cursor.getString(cursor.getColumnIndex(KEY_EMRI)));
            lenda.put(KEY_SEMESTRI_ID, cursor.getString(cursor.getColumnIndex(KEY_SEMESTRI_ID)));
            lenda.put(KEY_KREDI, cursor.getString(cursor.getColumnIndex(KEY_KREDI)));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + lenda.toString());

        return lenda;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}