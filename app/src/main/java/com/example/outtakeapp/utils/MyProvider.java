package com.example.outtakeapp.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 自定义内容提供者(MyProvider)
 * 用于在不同应用程序之间共享数据
 * 继承ContentProvider类并实现其抽象方法
 */
public class MyProvider extends ContentProvider {

    private static final int TABLE1_DIR = 0;
    private static final int TABLE1_ITEM = 1;
    private static final int TABLE2_DIR = 2;
    private static final int TABLE2_ITEM = 3;

    private static  final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("com.example.outtakeapp.provider", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.outtakeapp.provider", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.outtakeapp.provider", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.outtakeapp.provider", "table2/#", TABLE2_ITEM);
    }

    /**
     * 初始化提供者，在创建时调用
     * 通常用于初始化数据库连接或其他资源
     *
     * @return boolean 返回true表示提供者已成功加载，false表示失败
     */
    @Override
    public boolean onCreate() {
        return false;
    }

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
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                // 处理 table1 目录查询逻辑
                break;
            case TABLE1_ITEM:
                // 处理单个 table1 条目查询逻辑
                break;
            case TABLE2_DIR:
                // 处理 table2 目录查询逻辑
                break;
            case TABLE2_ITEM:
                // 处理单个 table2 条目查询逻辑
                break;
        }
        return null;
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
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                // 返回 table1 的 MIME 类型
                return "vnd.android.cursor.dir/vnd.com.example.outtakeapp.provider.table1";
            case TABLE1_ITEM:
                // 返回单个 table1 条目的 MIME 类型
                return "vnd.android.cursor.item/vnd.com.example.outtakeapp.provider.table1";
            case TABLE2_DIR:
                // 返回 table2 的 MIME 类型
                return "vnd.android.cursor.dir/vnd.com.example.outtakeapp.provider.table2";
            case TABLE2_ITEM:
                // 返回单个 table2 条目的 MIME 类型
                return "vnd.android.cursor.item/vnd.com.example.outtakeapp.provider.table2";
        }
        return "";
    }

    /**
     * 向内容提供者中插入新数据
     * 将一组值插入到指定URI对应的数据存储中
     *
     * @param uri 指定要插入数据的位置的URI
     * @param values 要插入的数据键值对
     * @return Uri 新插入行的URI，如果插入失败则返回null
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    /**
     * 删除指定条件的数据
     * 从内容提供者中删除满足条件的数据行
     *
     * @param uri 指定要删除数据位置的URI
     * @param selection WHERE子句的条件语句
     * @param selectionArgs WHERE子句中占位符的替换值
     * @return int 被删除的行数
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 更新现有数据
     * 修改内容提供者中满足条件的现有数据
     *
     * @param uri 指定要更新数据位置的URI
     * @param values 包含要更新的新值的ContentValues对象
     * @param selection WHERE子句的条件语句
     * @param selectionArgs WHERE子句中占位符的替换值
     * @return int 被更新的行数
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}