package com.melfouly.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.melfouly.domain.model.Transition
import io.reactivex.rxjava3.core.Observable

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, $FROM_AMOUNT_COL REAL, $FROM_SYMBOL_COL TEXT, $TO_AMOUNT_COL REAL, $TO_SYMBOL_COL TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTransition(transition: Transition) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FROM_AMOUNT_COL, transition.fromAmount)
        contentValues.put(FROM_SYMBOL_COL, transition.fromSymbol)
        contentValues.put(TO_AMOUNT_COL, transition.toAmount)
        contentValues.put(TO_SYMBOL_COL, transition.toSymbol)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    fun getAllTransitions(): Observable<MutableList<Transition>> {
        val database = this.readableDatabase
        val transitions: MutableList<Transition> = ArrayList()
        val result = database.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $ID_COL DESC", null)
        if (result.moveToFirst()) {
            do {
                val transition = Transition(
                    result.getString(result.getColumnIndexOrThrow(FROM_AMOUNT_COL)).toDouble(),
                    result.getString(result.getColumnIndexOrThrow(FROM_SYMBOL_COL)),
                    result.getString(result.getColumnIndexOrThrow(TO_AMOUNT_COL)).toDouble(),
                    result.getString(result.getColumnIndexOrThrow(TO_SYMBOL_COL))
                )
                transitions.add(transition)
            } while (result.moveToNext())
        }
        result.close()
        return Observable.just(transitions)
    }

    companion object {
        private const val DATABASE_NAME = "SQLiteDatabase"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "transitions_table"
        const val ID_COL = "id"
        const val FROM_AMOUNT_COL = "from_amount"
        const val FROM_SYMBOL_COL = "from_symbol"
        const val TO_AMOUNT_COL = "to_amount"
        const val TO_SYMBOL_COL = "to_symbol"
    }
}

