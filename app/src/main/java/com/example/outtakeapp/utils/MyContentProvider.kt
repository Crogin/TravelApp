package com.example.outtakeapp.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

/**
 * 自定义内容提供者(MyProvider)
 * 用于在不同应用程序之间共享数据
 * 继承ContentProvider类并实现其抽象方法
 */
class MyContentProvider : ContentProvider() {
    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.example.outtakeapp.provider"
    private var databaseHelper: MyDatabaseHelper? = null

    private val uriMatcher by Later{
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.apply {
            addURI(authority, "book", bookDir)
            addURI(authority, "book/#", bookItem)
            addURI(authority, "category", categoryDir)
            addURI(authority, "category/#", categoryItem)
        }
        matcher
    }

    /**
     * 初始化提供者，在创建时调用
     * 通常用于初始化数据库连接或其他资源
     *
     * @return boolean 返回true表示提供者已成功加载，false表示失败
     */
    override fun onCreate() = context?.let {
        databaseHelper = MyDatabaseHelper(it, "BookStore.db", 2)
        true
    }?: false

    /**
     * 查询数据的方法
     * 根据指定的URI和其他条件从内容提供者中检索数据
     *
     * @param uri 要查询的统一资源标识符
     * @param projection 需要返回的列名数组，如果为null则返回所有列
     * @param selection WHERE子句的条件语句
     * @param selectionArgs WHERE子句中占位符的替换值
     * @param sortOrder 结果排序规则
     * @return Cursor 包含查询结果的游标对象
     */
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = databaseHelper?.writableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            bookDir -> db?.query("Book", projection, selection, selectionArgs, null, null, sortOrder)
            bookItem -> db?.query("Book", projection, selection, selectionArgs, null, null, sortOrder, sortOrder)
            categoryDir -> db?.query("Category", projection, selection, selectionArgs, null, null, sortOrder)
            categoryItem -> db?.query("Category", projection, selection, selectionArgs, null, null, sortOrder, sortOrder)
            else -> null
        }
        return cursor
    }

    /**
     * 向内容提供者中插入新数据
     * 将一组值插入到指定URI对应的数据存储中
     *
     * @param uri 指定要插入数据的位置的URI
     * @param values 要插入的数据键值对
     * @return Uri 新插入行的URI，如果插入失败则返回null
     */
    override fun insert(uri: Uri, values: ContentValues?) = databaseHelper?.let{
        val db = it.writableDatabase
        val returnUri = when (uriMatcher.match(uri)) {
            bookDir,bookItem -> {
                val newBookId = db.insert("Book", null, values)
                Uri.parse("content://$authority/book/$newBookId")
            }
            categoryDir,categoryItem -> {
                val newCategoryId = db.insert("Category", null, values)
                Uri.parse("content://$authority/category/$newCategoryId")
            }
            else ->  null
        }
        returnUri
    }

    /**
     * 删除内容提供者中的数据
     * 根据指定的URI和条件从内容提供者中删除数据
     *
     * @param uri 要删除数据的统一资源标识符
     * @param selection WHERE子句的条件语句
     * @param selectionArgs WHERE子句中占位符的替换值
     * @return int 删除的行数
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = databaseHelper?.writableDatabase
        val rowsDeleted = when (uriMatcher.match(uri)) {
            bookDir -> db?.delete("Book", selection, selectionArgs)
            bookItem -> db?.delete("Book", "id=?", arrayOf(uri.pathSegments[1]))
            categoryDir -> db?.delete("Category", selection, selectionArgs)
            categoryItem -> db?.delete("Category", "id=?", arrayOf(uri.pathSegments[1]))
            else -> 0
        }
        return rowsDeleted ?: 0
    }

    /**
     * 更新内容提供者中的数据
     * 根据指定的URI和条件更新内容提供者中的数据
     *
     * @param uri 要更新的数据的统一资源标识符
     * @param values 要更新的数据键值对
     * @param selection WHERE子句的条件语句
     * @param selectionArgs WHERE子句中占位符的替换值
     * @return int 更新的行数
     */
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = databaseHelper?.writableDatabase
        val rowsUpdated = when (uriMatcher.match(uri)) {
            bookDir -> db?.update("Book", values, selection, selectionArgs)
            bookItem -> db?.update("Book", values, "id=?", arrayOf(uri.pathSegments[1]))
            categoryDir -> db?.update("Category", values, selection, selectionArgs)
            categoryItem -> db?.update("Category", values, "id=?", arrayOf(uri.pathSegments[1]))
            else -> 0
        }
        return rowsUpdated ?: 0
    }

    /**
     * 获取指定URI的MIME类型
     * 返回与给定URI关联的数据的MIME类型
     *
     * @param uri 指定的统一资源标识符
     * @return String MIME类型字符串
     * <p>
     * URI遵循Android标准的MIME类型命名规范：
     * 前缀部分：vnd.android.cursor.dir 或 vnd.android.cursor.item
     * 后缀部分：vnd.[authority].[path]
     */
    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            bookDir -> "vnd.android.cursor.dir/vnd.com.example.outtakeapp.provider.book"
            bookItem -> "vnd.android.cursor.item/vnd.com.example.outtakeapp.provider.book"
            categoryDir -> "vnd.android.cursor.dir/vnd.com.example.outtakeapp.provider.category"
            categoryItem -> "vnd.android.cursor.item/vnd.com.example.outtakeapp.provider.category"
            else -> null
        }
    }

}