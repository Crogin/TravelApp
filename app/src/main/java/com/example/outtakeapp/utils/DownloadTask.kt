package com.example.outtakeapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class DownloadTask(
    context: Context,
    private val progressDialog: ProgressDialog?
) : AsyncTask<String, Int, Boolean>() {
    
    private val contextRef = WeakReference<Context>(context)

    /**
     * 执行下载任务前的准备工作
     */
    override fun onPreExecute() {
        super.onPreExecute()
        // 显示开始下载的提示
        contextRef.get()?.let { context ->
            Toast.makeText(context, "开始下载...", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 在后台线程执行下载任务
     * @param params 包含文件URL的参数数组
     * @return Boolean 下载是否成功
     */
    override fun doInBackground(vararg params: String?): Boolean {
        // 创建HttpURLConnection对象
        var connection: HttpURLConnection? = null
        // 创建输入流和输出流
        var inputStream: InputStream? = null
        // 创建文件输出流
        var fileOutputStream: FileOutputStream? = null

        try {
            // 创建URL对象并打开连接
            val urlString = params[0] ?: return false
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            // 设置请求方法为GET
            connection.requestMethod = "GET"
            // 连接
            connection.connect()

            // 判断响应码
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                // 获取文件长度
                val fileLength = connection.contentLength
                inputStream = connection.inputStream
                //生成本地下载的文件路径
                //解析文件名
                val fileName = urlString.substring(urlString.lastIndexOf("/") + 1)
                //找到下载目录
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                //创建目标文件对象
                val file = File(downloadsDir, fileName)
                //准备输出流（写入到文件）
                fileOutputStream = FileOutputStream(file)
                //准备缓冲区和计数器
                /**
                 * buffer：每次从网络读取 4096 字节（4KB）。分块读比一口气全读完好得多，能避免内存爆炸。
                 * totalBytesRead：记录“从下载开始到目前为止，一共读了多少字节”。
                 * bytesRead：每次从 inputStream 中读到的实际字节数（最后一次可能小于 4096）。
                 * */
                val buffer = ByteArray(4096)
                var totalBytesRead = 0
                var bytesRead: Int

                //核心循环：不断读 → 写 → 更新进度
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    totalBytesRead += bytesRead
                    fileOutputStream.write(buffer, 0, bytesRead)

                    //计算并发布进度
                    if (fileLength > 0) {
                        val progress = (totalBytesRead * 100) / fileLength
                        publishProgress(progress)
                    }
                }

                //把输出流中缓冲区尚未写入磁盘的数据强制写入文件
                fileOutputStream.flush()
                Log.d("DownloadTask", "文件下载完成: ${file.absolutePath}")
                return true
            } else {
                Log.e("DownloadTask", "下载失败，响应码: ${connection.responseCode}")
                return false
            }
        } catch (e: Exception) {
            Log.e("DownloadTask", "下载异常", e)
            return false
        } finally {
            try {
                inputStream?.close()
                fileOutputStream?.close()
                connection?.disconnect()
            } catch (e: Exception) {
                Log.e("DownloadTask", "关闭资源异常", e)
            }
        }
    }

    /**
     * 更新下载进度
     * @param values 进度值数组
     */
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        // 更新进度条
        val progress = values[0]
        if (progress != null) {
            progressDialog?.progress = progress
            Log.d("DownloadTask", "下载进度: $progress%")
        }
    }

    /**
     * 下载任务完成后的处理
     * @param result 下载结果，true表示成功，false表示失败
     */
    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        // 隐藏进度对话框
        progressDialog?.dismiss()
        
        contextRef.get()?.let { context ->
            if (result == true) {
                Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}