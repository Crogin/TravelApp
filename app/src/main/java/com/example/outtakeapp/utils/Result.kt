package com.example.outtakeapp.utils

data class Response<T: Any> (
    val message: Message,
    val status: Long
)

data class Message (
    /**
     * 封面地址
     */
    val cover: String,

    /**
     * 发布时间
     */
    val createTime: String,

    /**
     * 是否当前版本
     */
    val current: Long,

    /**
     * 是否开启灰度测试，0-否 1-是
     */
    val gray: Long,

    /**
     * 主键
     */
    val id: Long,

    /**
     * 灰度测试的门店id
     */
    val storeID: String,

    /**
     * 上一次更新时间
     */
    val updateTime: String,

    /**
     * 是否强制更新，0-否 1-是
     */
    val urge: Long,

    /**
     * 下载地址
     */
    val url: String,

    /**
     * 版本
     */
    val version: Long
)