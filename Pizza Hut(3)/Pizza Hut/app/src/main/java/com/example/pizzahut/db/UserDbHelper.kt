package com.example.login.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.login.entity.UserInfo


private val CREATE_USER = " create table user_table ( " +
        " user_id integer primary key autoincrement " +
        " , username text " +
        " , phone text " +
        " , password text )"
class UserDbHelper(val context: Context, val name:String,
                   val version: Int): SQLiteOpenHelper(context,name,null,version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    companion object {
        private var sHelper: UserDbHelper? = null

        @Synchronized
        fun getInstance(context: Context): UserDbHelper {
            if (sHelper == null) {
                sHelper = UserDbHelper(context, "user_info.db", 1)
            }
            return sHelper!!
        }
    }

    //登录
    @Suppress("Range")
    fun login(phone: String): UserInfo? {
        val db: SQLiteDatabase = readableDatabase
        var userInfo: UserInfo? = null

        val sql = "select user_id, username,phone, password from user_table where phone=?"
        val selectionArgs = arrayOf(phone)
        val cursor = db.rawQuery(sql, selectionArgs)
        if (cursor.moveToFirst()) {
            val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
            val username = cursor.getString(cursor.getColumnIndex("username"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val password = cursor.getString(cursor.getColumnIndex("password"))
            userInfo = UserInfo(user_id,username, phone, password)
        }
        cursor.close()
        //db.close()
        return userInfo
    }


    //注册
    fun register(phone: String, password: String): Int {
        // 获取SQLiteDatabase实例
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        // 填充占位符
        values.put("phone",phone)
        values.put("username","必胜星人")
        values.put("password", password)
        //val nullColumnHack = "values(null,?,?)"
        // 执行
        val insert = db.insert("user_table", null, values)
        //db.close()
        return insert.toInt()
    }

    fun updatePwd(phone: String, password: String): Int { // 获取SQLiteDatabase实例
        val db = writableDatabase // 直接调用writableDatabase而不是getWritableDatabase
        // 填充占位符
        val values = ContentValues()
        values.put("password", password)
        // 执行SQL
        val update = db.update("user_table", values, "phone=?", arrayOf(phone))

        // 关闭数据库连接
        //db.close()

        return update
    }

    fun updateUsername(phone: String,username: String): Int { // 获取SQLiteDatabase实例
        val db = writableDatabase // 直接调用writableDatabase而不是getWritableDatabase
        // 填充占位符
        val values = ContentValues()
        values.put("username", username)
        // 执行SQL
        val update = db.update("user_table", values, "phone=?", arrayOf(phone))

        // 关闭数据库连接
        //db.close()

        return update
    }

}