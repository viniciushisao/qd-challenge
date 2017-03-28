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


//    private static final String[] COLUMNS = {KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME};

    //    public void addPerson(Person person) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put(KEY_CPF, person.getCpf()); // get cpf
//        values.put(KEY_NAME, person.getName()); // get name
//
//        // 3. insert
//        db.insert(TABLE_CUSTOMER, // table
//                null, //nullColumnHack
//                values); // key/value -> keys = column names/ values = column values
//
//        // 4. close
//        db.close();
//    }
    public void addCustomer(Customer customer) {

        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_KEY_FIRST_NAME, customer.getFirstName());
        values.put(CUSTOMER_KEY_LAST_NAME, customer.getLastName());
        values.put(CUSTOMER_KEY_ID, customer.getId());

        // 3. insert
        db.insert(CUSTOMER_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addTable(Table table) {

        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(TABLE_KEY_ID, table.getId());
        values.put(TABLE_KEY_OCUPIED, table.isOcupiedInteger());

        // 3. insert
        db.insert(TABLE_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

//    public Person getPerson(int id) {
//
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // 2. build query
//        Cursor cursor =
//                db.query(TABLE_CUSTOMER, // a. table
//                        COLUMNS, // b. column names
//                        " id = ?", // c. selections
//                        new String[]{String.valueOf(id)}, // d. selections args
//                        null, // e. group by
//                        null, // f. having
//                        null, // g. order by
//                        null); // h. limit
//
//        // 3. if we got results get the first one
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // 4. build person object
//        Person person = new Person();
//        person.setId(Integer.parseInt(cursor.getString(0)));
//        person.setCpf(cursor.getString(1));
//        person.setName(cursor.getString(2));
//
//        // 5. return person
//        return person;
//    }

//
//    public Person getLastInsertedPerson() {
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // 2. build query
//        Cursor cursor =
//                db.query(TABLE_CUSTOMER, // a. table
//                        COLUMNS, // b. column names
//                        null, // c. selections
//                        null, // d. selections args
//                        null, // e. group by
//                        null, // f. having
//                        null, // g. order by
//                        null); // h. limit
//
//        // 3. if we got results get the first one
//        if (cursor != null) {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//            cursor.moveToLast();
//        } else if (cursor == null) {
//            return null;
//        }
//
//        // 4. build person object
//        Person person = new Person();
//        person.setId(Integer.parseInt(cursor.getString(0)));
//        person.setCpf(cursor.getString(1));
//        person.setName(cursor.getString(2));
//
//        // 5. return person
//        return person;
//
//    }

    // Get All Persons
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<Customer>();

        // 1. build the query
        String query = "SELECT  * FROM " + CUSTOMER_TABLE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build person and add it to list
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

    // Updating single person
//    public int updatePerson(Person person) {
//
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put(KEY_FIRST_NAME, person.getCpf());
//        values.put(KEY_NAME, person.getName());
//
//        // 3. updating row
//        int i = db.update(TABLE_CUSTOMER, //table
//                values, // column/value
//                KEY_ID + " = ?", // selections
//                new String[]{String.valueOf(person.getId())}); //selection args
//
//        // 4. close
//        db.close();
//
//        return i;
//
//    }

//    public int deleteAllPerson() {
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//        int a = db.delete(TABLE_CUSTOMER, "1", null);
//        return a;
//    }

    // Deleting single person
//    public void deletePerson(int id) {
//
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // 2. delete
//        db.delete(TABLE_CUSTOMER,
//                KEY_ID + " = ?",
//                new String[]{String.valueOf(id)});
//
//        // 3. close
//        db.close();
//
//    }
}