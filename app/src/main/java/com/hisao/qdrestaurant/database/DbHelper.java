package com.hisao.qdrestaurant.database;

import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hisao.qdrestaurant.model.Customer;
import com.hisao.qdrestaurant.model.Table;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RestaurantDB";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create person table
        db.execSQL(CUSTOMER_CREATE_TABLE);
        db.execSQL(TABLE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older persons table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLE);
        // create fresh persons table
        this.onCreate(db);
    }


    // Customer table name
    private static final String CUSTOMER_TABLE = "customer";
    private static final String CUSTOMER_KEY_ID = "id";
    private static final String CUSTOMER_KEY_FIRST_NAME = "first_name";
    private static final String CUSTOMER_KEY_LAST_NAME = "last_name";
    private static final String CUSTOMER_CREATE_TABLE = "CREATE TABLE " + CUSTOMER_TABLE + " ( " +
            CUSTOMER_KEY_ID + " INTEGER primary key," +
            CUSTOMER_KEY_FIRST_NAME + " TEXT, " +
            CUSTOMER_KEY_LAST_NAME + " TEXT )";


    // Table table name
    private static final String TABLE_TABLE = "restaurant_table";
    private static final String TABLE_KEY_ID = "id";
    private static final String TABLE_KEY_OCUPIED = "ocupied";
    private static final String TABLE_CREATE_TABLE = "CREATE TABLE " + TABLE_TABLE + " ( " +
            TABLE_KEY_ID + " INTEGER primary key," +
            TABLE_KEY_OCUPIED + " INTEGER )";

    public void addCustomer(Customer customer) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CUSTOMER_KEY_FIRST_NAME, customer.getFirstName());
        values.put(CUSTOMER_KEY_LAST_NAME, customer.getLastName());
        values.put(CUSTOMER_KEY_ID, customer.getId());

        db.insert(CUSTOMER_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }

    public void addTable(Table table) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_KEY_ID, table.getId());
        values.put(TABLE_KEY_OCUPIED, table.isOcupiedInteger());
        db.insert(TABLE_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<Customer>();
        String query = "SELECT  * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {
                customer = new Customer();
                customer.setId(Integer.parseInt(cursor.getString(0)));
                customer.setFirstName(cursor.getString(1));
                customer.setLastName(cursor.getString(2));

                // Add person to persons
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        return customers;
    }

    public List<Table> getAllTables() {
        List<Table> tables = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Table table;
        if (cursor.moveToFirst()) {
            do {
                table = new Table();
                table.setId(Integer.parseInt(cursor.getString(0)));
                table.setOcupied(cursor.getInt(1));
                tables.add(table);
            } while (cursor.moveToNext());
        }
        return tables;
    }
}