package com.example.agrix.dbhandler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.agrix.model.Product

const val DATABASE_NAME = "productsDB"
const val TABLE_NAME = "products"
const val COL_ID = "Id"
const val COL_PRODUCT_NAME = "ProductName"
const val COL_MEASUREMENT = "Measurement"
const val COL_QUANTITY = "Quantity"
const val COL_LAST_EDITED = "LastEdited"

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_PRODUCT_NAME + " TEXT NOT NULL UNIQUE," +
                        COL_MEASUREMENT + " TEXT NOT NULL," +
                        COL_QUANTITY + " REAL NOT NULL," +
                        COL_LAST_EDITED + " TEXT NOT NULL"+
                  ")"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    // CRUD OPERATION
    // CREATE
    fun insertData(product: Product): Int {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_PRODUCT_NAME, product.productName)
        cv.put(COL_MEASUREMENT, product.measurement)
        cv.put(COL_QUANTITY, product.quantity)
        cv.put(COL_LAST_EDITED, product.lastEdited)

        return db.insert(TABLE_NAME, null, cv).toInt()

    }


//     RETRIEVE ALL
    fun retrieveData() : ArrayList<Product> {
        val productsList: ArrayList<Product> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM products"
        val result = db.rawQuery(query, null)
        Log.d("TTT", "$result")

        if (result.moveToNext()) {

            do{
                val productName: String = result.getString(1)
                val measurement: String = result.getString(2)
                val quantity: Double = result.getDouble(3)
                val lastEdited: String = result.getString(4)

                val product = Product(productName, measurement, quantity, lastEdited)
                productsList.add(product)
                Log.d("TTT", "Adding $product")
            } while (result.moveToNext())

        }

        result.close()
        Log.d("TTT", "Returned $productsList")
        return productsList
    }

    // RETRIEVE ONE
    fun retrieveData(queryProductName: String) : Product{
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_PRODUCT_NAME = '$queryProductName'"
        Log.d("TTT", query)
        val result = db.rawQuery(query, null)

        val product = if (result.moveToFirst()) {

            val productName: String = result.getString(1)
            val measurement: String = result.getString(2)
            val quantity: Double = result.getDouble(3)
            val lastEdited: String = result.getString(4)

            Product(productName, measurement, quantity, lastEdited)
        } else {
            Product("None","None", 0.0 , "None")
        }

        Log.d("TTT", product.toString())

        result.close()
        db.close()
        return product
    }

    // UPDATE
    fun updateData(productName: String, productQuantity: Double, productLastEdited: String): Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_QUANTITY, productQuantity)
        cv.put(COL_LAST_EDITED, productLastEdited)
        val result = db.update(
            TABLE_NAME,
            cv,
            "$COL_PRODUCT_NAME=?",
            arrayOf(productName)
        )

        return result
    }

//     DELETE
    fun deleteData(productName: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_PRODUCT_NAME=?", arrayOf(productName))
    db.close()
    return result
    }

}