[33mcommit 324b1061830e26a336205a0f58f762fe49fa81c8[m[33m ([m[1;36mHEAD[m[33m -> [m[1;32mmain[m[33m, [m[1;31morigin/main[m[33m, [m[1;31morigin/HEAD[m[33m)[m
Author: 严浩俊 <3254059519@qq.com>
Date:   Sun Nov 23 21:53:04 2025 +0800

    广播学习

[1mdiff --git a/.idea/deploymentTargetSelector.xml b/.idea/deploymentTargetSelector.xml[m
[1mindex 165f799..fd60a6c 100644[m
[1m--- a/.idea/deploymentTargetSelector.xml[m
[1m+++ b/.idea/deploymentTargetSelector.xml[m
[36m@@ -4,10 +4,10 @@[m
     <selectionStates>[m
       <SelectionState runConfigName="app">[m
         <option name="selectionMode" value="DROPDOWN" />[m
[31m-        <DropdownSelection timestamp="2025-11-19T04:35:00.907190800Z">[m
[32m+[m[32m        <DropdownSelection timestamp="2025-11-22T14:06:12.065041100Z">[m
           <Target type="DEFAULT_BOOT">[m
             <handle>[m
[31m-              <DeviceId pluginId="LocalEmulator" identifier="path=C:\Users\32540\.android\avd\Medium_Tablet.avd" />[m
[32m+[m[32m              <DeviceId pluginId="LocalEmulator" identifier="path=C:\Users\32540\.android\avd\Pixel_9_Pro.avd" />[m
             </handle>[m
           </Target>[m
         </DropdownSelection>[m
[1mdiff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml[m
[1mindex e9eb779..65795d0 100644[m
[1m--- a/app/src/main/AndroidManifest.xml[m
[1m+++ b/app/src/main/AndroidManifest.xml[m
[36m@@ -5,6 +5,8 @@[m
     <uses-permission android:name="android.permission.INTERNET" />[m
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />[m
     <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />[m
 [m
     <application[m
         android:allowBackup="true"[m
[36m@@ -37,10 +39,19 @@[m
             android:exported="false" />[m
         <activity[m
             android:name=".Activities.LoginActivity"[m
[31m-            android:exported="false" />[m
[32m+[m[32m            android:exported="true">[m
[32m+[m[32m            <intent-filter>[m
[32m+[m[32m                <action android:name="android.intent.action.MAIN" />[m
[32m+[m[32m                <category android:name="android.intent.category.LAUNCHER" />[m
[32m+[m[32m            </intent-filter>[m
[32m+[m[32m            <intent-filter>[m
[32m+[m[32m                <action android:name="android.intent.action.VIEW" />[m
[32m+[m[32m                <category android:name="android.intent.category.DEFAULT" />[m
[32m+[m[32m            </intent-filter>[m
[32m+[m[32m        </activity>[m
         <activity[m
             android:name=".Activities.PopularDetailActivity"[m
[31m-            android:exported="false" />[m
[32m+[m[32m            android:exported="false"/>[m
         <activity[m
             android:name=".Activities.IntroActivity"[m
             android:exported="false" />[m
[36m@@ -49,13 +60,7 @@[m
             android:exported="false" />[m
         <activity[m
             android:name=".Activities.MainActivity"[m
[31m-            android:exported="true">[m
[31m-            <intent-filter>[m
[31m-                <action android:name="android.intent.action.MAIN" />[m
[31m-[m
[31m-                <category android:name="android.intent.category.LAUNCHER" />[m
[31m-            </intent-filter>[m
[31m-        </activity>[m
[32m+[m[32m            android:exported="false"></activity>[m
     </application>[m
 [m
 </manifest>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Activities/BaseActivity.java b/app/src/main/java/com/example/outtakeapp/Activities/BaseActivity.java[m
[1mindex 8c36aeb..38c130b 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Activities/BaseActivity.java[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Activities/BaseActivity.java[m
[36m@@ -1,18 +1,76 @@[m
 package com.example.outtakeapp.Activities;[m
 [m
 import android.annotation.SuppressLint;[m
[32m+[m[32mimport android.content.BroadcastReceiver;[m
[32m+[m[32mimport android.content.Context;[m
[32m+[m[32mimport android.content.Intent;[m
[32m+[m[32mimport android.content.IntentFilter;[m
[32m+[m[32mimport android.os.Build;[m
 import android.os.Bundle;[m
[32m+[m[32mimport android.widget.Toast;[m
 [m
[32m+[m[32mimport androidx.appcompat.app.AlertDialog;[m
 import androidx.appcompat.app.AppCompatActivity;[m
[32m+[m[32mimport com.example.outtakeapp.utils.ActivityCollector;[m
 [m
 public class BaseActivity extends AppCompatActivity {[m
[31m-    //TODO: 基类，但是没开工，后续完善[m
[32m+[m[32m    ForceOfflineReceiver receiver;//强制下线[m
 [m
[31m-    @SuppressLint("ObsoleteSdkInt")[m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
[31m-[m
         super.onCreate(savedInstanceState);[m
[32m+[m[32m        //添加Activity到容器中[m
[32m+[m[32m        ActivityCollector.INSTANCE.addActivity(this);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @SuppressLint({"UnspecifiedRegisterReceiverFlag", "WrongConstant", "InlinedApi"})[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onResume() {[m
[32m+[m[32m        super.onResume();[m
[32m+[m[32m        //注册广播[m
[32m+[m[32m        IntentFilter filter = new IntentFilter();[m
[32m+[m[32m        filter.addAction("com.example.outtakeapp.FORCE_OFFLINE");[m
[32m+[m[32m        receiver = new ForceOfflineReceiver();[m
[32m+[m
[32m+[m[32m        // 确保在所有版本中都指定 RECEIVER_EXPORTED 或 RECEIVER_NOT_EXPORTED[m
[32m+[m[32m        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {[m
[32m+[m[32m            // Android 14+ 需要明确指定[m
[32m+[m[32m            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);[m
[32m+[m[32m        } else {[m
[32m+[m[32m            // Android 13 及以下版本[m
[32m+[m[32m            registerReceiver(receiver, filter);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onPause() {[m
[32m+[m[32m        super.onPause();[m
[32m+[m[32m        if (receiver != null) {[m
[32m+[m[32m            unregisterReceiver(receiver);[m
[32m+[m[32m            receiver = null;[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    protected void onDestroy() {[m
[32m+[m[32m        super.onDestroy();[m
[32m+[m[32m        ActivityCollector.INSTANCE.removeActivity(this);[m
     }[m
 [m
[32m+[m[32m    public static class ForceOfflineReceiver extends BroadcastReceiver {[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public void onReceive(Context context, Intent intent) {[m
[32m+[m[32m            Toast.makeText(context, "强制下线", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                AlertDialog.Builder builder = new AlertDialog.Builder(context);[m
[32m+[m[32m                builder.setTitle("提示")[m
[32m+[m[32m                        .setMessage("您已被强制下线")[m
[32m+[m[32m                        .setCancelable(false)[m
[32m+[m[32m                        .setPositiveButton("确定", (dialog, which) -> {[m
[32m+[m[32m                            ActivityCollector.INSTANCE.finishAll();[m
[32m+[m[32m                            Intent i = new Intent(context, LoginActivity.class);[m
[32m+[m[32m                            context.startActivity(i);[m
[32m+[m[32m                        });[m
[32m+[m[32m                builder.show();[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Activities/ChatActivity.kt b/app/src/main/java/com/example/outtakeapp/Activities/ChatActivity.kt[m
[1mindex accfa3e..acc8ece 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Activities/ChatActivity.kt[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Activities/ChatActivity.kt[m
[36m@@ -1,17 +1,17 @@[m
 package com.example.outtakeapp.Activities[m
 [m
 import android.annotation.SuppressLint[m
[32m+[m[32mimport android.content.Intent[m
 import android.os.Bundle[m
 import androidx.activity.enableEdgeToEdge[m
[31m-import androidx.appcompat.app.AppCompatActivity[m
 import androidx.recyclerview.widget.LinearLayoutManager[m
 import com.example.outtakeapp.Adapter.MessageAdapter[m
 import com.example.outtakeapp.Model.Message[m
 import com.example.outtakeapp.R[m
 [m
[31m-class ChatActivity : AppCompatActivity() {[m
[32m+[m[32mclass ChatActivity : BaseActivity() {[m
     val messageList = ArrayList<Message>()[m
[31m-    @SuppressLint("MissingInflatedId")[m
[32m+[m[32m    @SuppressLint("MissingInflatedId", "UnsafeImplicitIntentLaunch")[m
     override fun onCreate(savedInstanceState: Bundle?) {[m
         super.onCreate(savedInstanceState)[m
         enableEdgeToEdge()[m
[36m@@ -24,15 +24,10 @@[m [mclass ChatActivity : AppCompatActivity() {[m
         val adapter = MessageAdapter(messageList)[m
         recyclerView.adapter = adapter[m
         send.setOnClickListener(){[m
[31m-            send->[m
[31m-            val send = findViewById<android.widget.EditText>(R.id.input_text)[m
[31m-            if (send.text.isNotEmpty()){[m
[31m-                val msg = Message(send.text.toString(), Message.TYPE_SENT)[m
[31m-                messageList.add(msg)[m
[31m-                adapter.notifyItemInserted(messageList.size-1)// 刷新数据[m
[31m-                recyclerView.scrollToPosition(messageList.size-1) // 滚动到底部[m
[31m-                send.setText("")[m
[31m-            }[m
[32m+[m[32m            //发送广播[m
[32m+[m[32m            val intent = Intent("com.example.outtakeapp.FORCE_OFFLINE")[m
[32m+[m[32m            intent.setPackage(packageName)[m
[32m+[m[32m            sendBroadcast(intent)[m
         }[m
     }[m
 [m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Activities/ListViewActivity.kt b/app/src/main/java/com/example/outtakeapp/Activities/ListViewActivity.kt[m
[1mindex ae9fc5f..5c0689e 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Activities/ListViewActivity.kt[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Activities/ListViewActivity.kt[m
[36m@@ -1,7 +1,12 @@[m
 package com.example.outtakeapp.Activities[m
 [m
 import android.annotation.SuppressLint[m
[32m+[m[32mimport android.content.BroadcastReceiver[m
[32m+[m[32mimport android.content.Context[m
[32m+[m[32mimport android.content.Intent[m
[32m+[m[32mimport android.content.IntentFilter[m
 import android.os.Bundle[m
[32m+[m[32mimport android.widget.Button[m
 import android.widget.ListView[m
 import android.widget.Toast[m
 import androidx.activity.enableEdgeToEdge[m
[36m@@ -10,13 +15,15 @@[m [mimport com.example.outtakeapp.Model.Fruit[m
 import com.example.outtakeapp.R[m
 [m
 class ListViewActivity : BaseActivity() {[m
[32m+[m[32m    lateinit var timeReceiver: TimeReceiver[m
 [m
     private val fruitList = ArrayList<Fruit>()[m
[31m-    @SuppressLint("MissingInflatedId")[m
[32m+[m[32m    @SuppressLint("MissingInflatedId", "UnspecifiedRegisterReceiverFlag")[m
     override fun onCreate(savedInstanceState: Bundle?) {[m
         super.onCreate(savedInstanceState)[m
         enableEdgeToEdge()[m
         setContentView(R.layout.activity_new)[m
[32m+[m[32m        val back = findViewById<Button>(R.id.back)[m
         val listView = findViewById<ListView>(R.id.ListView)[m
         initData()[m
         val adapter = ListViewAdapter(this,R.layout.item_new,fruitList)[m
[36m@@ -24,7 +31,19 @@[m [mclass ListViewActivity : BaseActivity() {[m
         listView.setOnItemClickListener{_, _, position, _ ->[m
             val fruit = fruitList[position][m
             Toast.makeText(this,"点击了${fruit.name}", Toast.LENGTH_SHORT).show()[m
[32m+[m[32m            val intent = Intent("aaaaaaaaaaaa")[m
[32m+[m[32m            //限定广播接收器只能接收本应用的广播[m
[32m+[m[32m            intent.setPackage(packageName)[m
[32m+[m[32m            //设置广播优先级[m
[32m+[m[32m            sendBroadcast(intent,null)[m
         }[m
[32m+[m[32m        back.setOnClickListener {[m
[32m+[m
[32m+[m[32m        }[m
[32m+[m[32m        val intentFilter = IntentFilter()[m
[32m+[m[32m        intentFilter.addAction("android.intent.action.TIME_TICK")[m
[32m+[m[32m        timeReceiver = TimeReceiver()[m
[32m+[m[32m        registerReceiver(timeReceiver,intentFilter)[m
     }[m
 [m
     private fun initData() {[m
[36m@@ -37,4 +56,11 @@[m [mclass ListViewActivity : BaseActivity() {[m
             fruitList.add(Fruit("小小是事实", R.drawable.ali))[m
         }[m
     }[m
[32m+[m
[32m+[m[32m    inner class TimeReceiver: BroadcastReceiver(){[m
[32m+[m[32m        override fun onReceive(context: Context?, intent: Intent?) {[m
[32m+[m[32m            Toast.makeText(context,"时间到了",Toast.LENGTH_SHORT).show()[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m    }[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Activities/LoginActivity.java b/app/src/main/java/com/example/outtakeapp/Activities/LoginActivity.java[m
[1mindex 319dc23..6ab3480 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Activities/LoginActivity.java[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Activities/LoginActivity.java[m
[36m@@ -3,6 +3,8 @@[m [mpackage com.example.outtakeapp.Activities;[m
 import android.content.Intent;[m
 import android.os.Bundle;[m
 import android.view.View;[m
[32m+[m[32mimport android.widget.Toast;[m
[32m+[m
 import com.example.outtakeapp.databinding.ActivityLoginBinding;[m
 [m
 public class LoginActivity extends BaseActivity {[m
[36m@@ -17,7 +19,12 @@[m [mpublic class LoginActivity extends BaseActivity {[m
         binging.loginBtn.setOnClickListener(new View.OnClickListener() {[m
             @Override[m
             public void onClick(View v) {[m
[31m-                startActivity(new Intent(LoginActivity.this,MainActivity.class));[m
[32m+[m[32m                if (binging.editText1.getText().toString().equals("admin") && binging.editText2.getText().toString().equals("123456")){[m
[32m+[m[32m                    startActivity(new Intent(LoginActivity.this,MainActivity.class));[m
[32m+[m[32m                    finish();[m
[32m+[m[32m                }else{[m
[32m+[m[32m                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();[m
[32m+[m[32m                }[m
             }[m
         });[m
     }[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Activities/NewsContentActivity.kt b/app/src/main/java/com/example/outtakeapp/Activities/NewsContentActivity.kt[m
[1mindex 497f284..19d5758 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Activities/NewsContentActivity.kt[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Activities/NewsContentActivity.kt[m
[36m@@ -5,6 +5,7 @@[m [mimport android.content.Intent[m
 import android.os.Bundle[m
 import android.widget.TextView[m
 import androidx.activity.enableEdgeToEdge[m
[32m+[m[32mimport com.example.outtakeapp.Fragment.NewsContentFragment[m
 import com.example.outtakeapp.R[m
 [m
 class NewsContentActivity : BaseActivity() {[m
[36m@@ -15,8 +16,8 @@[m [mclass NewsContentActivity : BaseActivity() {[m
         val title = intent.getStringExtra("title")[m
         val content = intent.getStringExtra("content")[m
         if (title != null && content != null){[m
[31m-            findViewById<TextView>(R.id.title).text = title[m
[31m-            findViewById<TextView>(R.id.content).text = content[m
[32m+[m[32m            val fragment = supportFragmentManager.findFragmentById(R.id.newsContentFrag) as NewsContentFragment[m
[32m+[m[32m            fragment.reFlash(title, content)[m
         }[m
     }[m
 [m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Fragment/MineFragment.java b/app/src/main/java/com/example/outtakeapp/Fragment/MineFragment.java[m
[1mindex 2bdc7a9..9f2dcfb 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Fragment/MineFragment.java[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Fragment/MineFragment.java[m
[36m@@ -17,6 +17,7 @@[m [mimport android.widget.EditText;[m
 import android.widget.TextView;[m
 import android.widget.Toast;[m
 import com.bumptech.glide.Glide;[m
[32m+[m[32mimport com.example.outtakeapp.Activities.BaseActivity;[m
 import com.example.outtakeapp.Activities.ChatActivity;[m
 import com.example.outtakeapp.Activities.FragmentActivity;[m
 import com.example.outtakeapp.Activities.LoginActivity;[m
[36m@@ -24,6 +25,7 @@[m [mimport com.example.outtakeapp.Activities.ListViewActivity;[m
 import com.example.outtakeapp.Activities.NewsActivty;[m
 import com.example.outtakeapp.Activities.RecycleViewActivity;[m
 import com.example.outtakeapp.databinding.FragmentMineBinding;[m
[32m+[m
 import java.io.File;[m
 import java.io.FileOutputStream;[m
 import java.io.InputStream;[m
[36m@@ -113,7 +115,11 @@[m [mpublic class MineFragment extends Fragment {[m
 [m
         //退出登录[m
         binding.a6.setOnClickListener(v -> {[m
[31m-            startActivity(new Intent(requireContext(), LoginActivity.class));[m
[32m+[m[32m            Intent intent = new Intent("com.example.outtakeapp.FORCE_OFFLINE");[m
[32m+[m[32m            intent.setClass(requireContext(), BaseActivity.ForceOfflineReceiver.class);[m
[32m+[m[32m            requireContext().sendBroadcast(intent);[m
[32m+[m
[32m+[m[32m//            startActivity(new Intent(requireContext(), LoginActivity.class));[m
         });[m
 [m
         return binding.getRoot();[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/Fragment/NewsTitleFragment.kt b/app/src/main/java/com/example/outtakeapp/Fragment/NewsTitleFragment.kt[m
[1mindex 30415a2..0e03ae3 100644[m
[1m--- a/app/src/main/java/com/example/outtakeapp/Fragment/NewsTitleFragment.kt[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/Fragment/NewsTitleFragment.kt[m
[36m@@ -11,6 +11,7 @@[m [mimport androidx.recyclerview.widget.RecyclerView[m
 import com.example.outtakeapp.Activities.NewsContentActivity[m
 import com.example.outtakeapp.Model.News[m
 import com.example.outtakeapp.R[m
[32m+[m[32mimport com.example.outtakeapp.utils.lettersCount[m
 [m
 @Suppress("DEPRECATION")[m
 class NewsTitleFragment: Fragment() {[m
[36m@@ -26,8 +27,8 @@[m [mclass NewsTitleFragment: Fragment() {[m
         isTwoPane = activity?.findViewById<View>(R.id.newsTitleFrag) != null[m
         val layoutManager = LinearLayoutManager(activity)[m
         val recyclerView = view?.findViewById<View>(R.id.recyclerView) as RecyclerView[m
[31m-        val adapter = NewsAdapter(getNews())[m
         recyclerView.layoutManager = layoutManager[m
[32m+[m[32m        val adapter = NewsAdapter(getNews())[m
         recyclerView.adapter = adapter[m
 [m
 [m
[36m@@ -69,16 +70,16 @@[m [mclass NewsTitleFragment: Fragment() {[m
                 val news = newsList[holder.adapterPosition][m
                 if (isTwoPane){[m
                     // 如果是双页模式，则将内容展示在右侧的NewsContentFragment中[m
[31m-                    val fragment = NewsContentFragment()[m
[32m+[m[32m                    val fragmentManager = parent.context as androidx.fragment.app.FragmentActivity[m
[32m+[m[32m                    val fragment = fragmentManager.supportFragmentManager.findFragmentById(R.id.newsContentFrag) as NewsContentFragment[m
                     fragment.reFlash(news.title, news.content)[m
[32m+[m[32m                    print(news.title.lettersCount())[m
                 } else {[m
                     // 如果是单页模式，则直接启动NewsContentActivity[m
                     NewsContentActivity.startAty(view.context, news.title, news.content)[m
                 }[m
             }[m
[31m-[m
[31m-[m
[31m-            return ViewHolder(view)[m
[32m+[m[32m            return holder[m
         }[m
 [m
         override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/utils/ActivityCollector.kt b/app/src/main/java/com/example/outtakeapp/utils/ActivityCollector.kt[m
[1mnew file mode 100644[m
[1mindex 0000000..dc28625[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/utils/ActivityCollector.kt[m
[36m@@ -0,0 +1,24 @@[m
[32m+[m[32mpackage com.example.outtakeapp.utils[m
[32m+[m
[32m+[m[32mimport android.app.Activity[m
[32m+[m
[32m+[m[32mobject ActivityCollector {[m
[32m+[m[32m    private val activities = ArrayList<Activity>()[m
[32m+[m
[32m+[m[32m    fun addActivity(activity: Activity) {[m
[32m+[m[32m        activities.add(activity)[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    fun removeActivity(activity: Activity) {[m
[32m+[m[32m        activities.remove(activity)[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    fun finishAll() {[m
[32m+[m[32m        for (activity in activities) {[m
[32m+[m[32m            if (!activity.isFinishing) {[m
[32m+[m[32m                activity.finish()[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        activities.clear()[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/com/example/outtakeapp/utils/StringUtil.kt b/app/src/main/java/com/example/outtakeapp/utils/StringUtil.kt[m
[1mnew file mode 100644[m
[1mindex 0000000..f20ce22[m
[1m--- /dev/null[m
[1m+++ b/app/src/main/java/com/example/outtakeapp/utils/StringUtil.kt[m
[36m@@ -0,0 +1,38 @@[m
[32m+[m[32mpackage com.example.outtakeapp.utils[m
[32m+[m
[32m+[m[32m/**[m
[32m+[m[32m * 拓展函数和重载运算符[m
[32m+[m[32m * **/[m
[32m+[m[32mfun String.lettersCount(): Int {[m
[32m+[m[32m    var count = 0[m
[32m+[m[32m    for (char in this) {[m
[32m+[m[32m        if (char.isLetter()) count++[m
[32m+[m[32m    }[m
[32m+[m[32m    return count[m
[32m+[m[32m}[m
[32m+[m
[32m+[m
[32m+[m[32mclass Money(val amount: Int){[m
[32m+[m[32m    operator fun plus(other: Money): Money {[m
[32m+[m[32m        return Money(amount + other.amount)[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    operator fun plus(other: Int): Money {[m
[32m+[m[32m        return Money(amount + other)[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32mfun main() {[m
[32m+[m[32m    val money1 = Money(10)[m
[32m+[m[32m    val money2 = Money(20)[m
[32m+[m[32m    val money3 = money1 + money2[m
[32m+[m[32m    val money4 = money1 + 5[m
[32m+[m[32m    println(money3.amount)[m
[32m+[m[32m    println(money4.amount)[m
[32m+[m[32m    println("你好".times(3))[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32mfun String.times(other: Int): String {[m
[32m+[m[32m    return  repeat(other)[m
[32m+[m[32m}[m
[32m+[m
[1mdiff --git a/app/src/main/res/layout/news_content_frag.xml b/app/src/main/res/layout/news_content_frag.xml[m
[1mindex 9f97718..9288bd5 100644[m
[1m--- a/app/src/main/res/layout/news_content_frag.xml[m
[1m+++ b/app/src/main/res/layout/news_content_frag.xml[m
[36m@@ -5,6 +5,7 @@[m
     android:layout_height="match_parent">[m
 [m
     <LinearLayout[m
[32m+[m[32m        android:layout_marginStart="10dp"[m
         android:id="@+id/contentLayout"[m
         android:layout_width="match_parent"[m
         android:layout_height="match_parent"[m
[36m@@ -36,7 +37,7 @@[m
     </LinearLayout>[m
 [m
     <View[m
[31m-        android:layout_width="1dp"[m
[32m+[m[32m        android:layout_width="10dp"[m
         android:layout_height="match_parent"[m
         android:layout_alignParentLeft="true"[m
         android:background="#000" />[m
[1mdiff --git a/app/src/main/res/layout/news_item.xml b/app/src/main/res/layout/news_item.xml[m
[1mindex 4434538..bb87286 100644[m
[1m--- a/app/src/main/res/layout/news_item.xml[m
[1m+++ b/app/src/main/res/layout/news_item.xml[m
[36m@@ -1,15 +1,17 @@[m
 <?xml version="1.0" encoding="utf-8"?>[m
 <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"[m
     android:layout_width="match_parent"[m
[31m-    android:layout_height="match_parent"[m
[32m+[m[32m    android:layout_height="wrap_content"[m
     xmlns:app="http://schemas.android.com/apk/res-auto">[m
 [m
     <TextView[m
         android:id="@+id/news_title"[m
         android:maxLines="1"[m
         android:layout_width="match_parent"[m
[31m-        android:layout_height="wrap_content"[m
[32m+[m[32m        android:layout_height="60dp"[m
[32m+[m[32m        android:gravity="center_vertical"[m
         android:text="TextView"[m
[32m+[m[32m        android:textSize="20sp"[m
         app:layout_constraintLeft_toLeftOf="parent"[m
         app:layout_constraintRight_toRightOf="parent"[m
         app:layout_constraintTop_toTopOf="parent" />[m
