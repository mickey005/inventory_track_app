package com.example.android.inventroytrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bitsbridge on 21/4/17.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "inventory.db";
    public final static int DB_VERSION = 1;
    public final static String LOG_TAG =InventoryDbHelper.class.getCanonicalName();

    public InventoryDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(StockContract.StockEntry.COLUMN_TABLE_STOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItem(StockItem item){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_NAME,item.getProductName());
        values.put(StockContract.StockEntry.COLUMN_PRICE,item.getPrice());
        values.put(StockContract.StockEntry.COLUMN_QUANTITY,item.getQuantity());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_NAME,item.getSupplierName());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,item.getSupplierName());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,item.getSupplierPhone());
        values.put(StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL,item.getSupplierPhone());
        values.put(StockContract.StockEntry.COLUMN_IMAGE,item.getImage());
        long id = db.insert(StockContract.StockEntry.TABLE_NAME,null,values);
    }

    public Cursor readStock(){
        SQLiteDatabase db = getReadableDatabase();
        String [] projection={
            StockContract.StockEntry._ID,
                StockContract.StockEntry.COLUMN_NAME,
                StockContract.StockEntry.COLUMN_PRICE,
                StockContract.StockEntry.COLUMN_QUANTITY,
                StockContract.StockEntry.COLUMN_SUPPLIER_NAME,
                StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,
                StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL,
                StockContract.StockEntry.COLUMN_IMAGE
        };

        Cursor cursor = db.query(StockContract.StockEntry.TABLE_NAME,
                projection,null,null,null,null,null);


        return cursor;
    }

    public Cursor readItem(long itemId){

        SQLiteDatabase db = getReadableDatabase();
        String [] projection ={
                StockContract.StockEntry._ID,
                 StockContract.StockEntry.COLUMN_NAME,
                StockContract.StockEntry.COLUMN_PRICE,
                StockContract.StockEntry.COLUMN_QUANTITY,
                StockContract.StockEntry.COLUMN_SUPPLIER_NAME,
                StockContract.StockEntry.COLUMN_SUPPLIER_PHONE,
                StockContract.StockEntry.COLUMN_SUPPLIER_EMAIL,
                StockContract.StockEntry.COLUMN_IMAGE
        };

        Cursor cursor = db.query(StockContract.StockEntry.TABLE_NAME,
                projection,null,null,null,null,null);


        return cursor;

    }

    public void updateItem(long currentItemId, int quantity){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_QUANTITY,quantity);
        String selection = StockContract.StockEntry._ID+"=?";
        String [] selectionArgs = new String[]{String.valueOf(currentItemId)};
        db.update(StockContract.StockEntry.TABLE_NAME,values,selection,selectionArgs);

    }

    public void sellOneItem(long itemId,int quantity){
        SQLiteDatabase db = getReadableDatabase();
        int newQuantity = 0;
        if (quantity>0){
            newQuantity =quantity-1;
        }

        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.COLUMN_QUANTITY,newQuantity);
        String selection = StockContract.StockEntry._ID+"=?";
        String [] selectionArgs = new String[]{String.valueOf(itemId)};
        db.update(StockContract.StockEntry.TABLE_NAME,values,selection,selectionArgs);
    }

}
