package com.example.outtakeapp.Activities.testActivities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityPhoneBinding
import com.example.outtakeapp.utils.build

class PhoneActivity : BaseActivity() {
    lateinit var binding: ActivityPhoneBinding
    private lateinit var contactList:ArrayList<Pair<String, String>>
    private lateinit var adapter: ArrayAdapter<Pair<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactList = ArrayList()
        // 修改 ArrayAdapter 构造函数，使用支持双行显示的构造函数
        adapter = object : ArrayAdapter<Pair<String, String>>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contactList) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                val item = getItem(position)
                item?.let {
                    // 设置第一个文本为联系人姓名
                    view.findViewById<android.widget.TextView>(android.R.id.text1).textSize = 20f
                    view.findViewById<android.widget.TextView>(android.R.id.text1).text = it.first
                    // 设置第二个文本为联系人电话
                    view.findViewById<android.widget.TextView>(android.R.id.text2).text = it.second
                }
                return view
            }
        }

        binding.ListView.adapter = adapter
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        }else{
            readContacts()
        }
    }

    //通过contentResolver.query()查询系统联系人数据库
    //遍历查询结果，获取每个联系人的姓名和电话号码
    //将姓名和号码组合成字符串添加到contactList列表中
    //通知适配器数据已更新，并关闭游标
    @SuppressLint("Range")
    private fun readContacts() {
        //1.要查询的Content Provider的URI，
        //2.projection: 要查询的列名数组（相当于SQL的SELECT子句）
        //3.selection: 查询条件（相当于SQL的WHERE子句）
        //4.selectionArgs: 查询条件参数
        //5.sortOrder: 排序规则（相当于SQL的ORDER BY子句）
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)?.build{
            while (moveToNext()) {
                //确保获取的列名存在，使用?.
                val name = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add(Pair(name, number))
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    //权限申请结果处理，每次请求都会返回到函数
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                //grantResults.isNotEmpty() - 确保授权结果数组不为空
                //grantResults[0] == PackageManager.PERMISSION_GRANTED - 检查第一个权限是否被授予
                //如果条件满足，则调用readContacts()函数读取联系人信息
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts()
                }else{
                    Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}
