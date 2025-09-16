package com.example.travelplanapp.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.travelplanapp.Activities.LoginActivity;
import com.example.travelplanapp.databinding.FragmentMineBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MineFragment extends Fragment {
    FragmentMineBinding binding;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<Intent> imagePickerLauncher;

    public MineFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册图片选择结果回调
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            //把图片path复制到app的私有目录
                            String savedPath = saveImageToInternalStorage(selectedImageUri);
                            if (savedPath != null) {
                                //用Glide加载图片
                                Glide.with(requireContext())
                                        .load(savedPath)
                                        .circleCrop()
                                        .into(binding.imageView137);

                                //保存图片路径，放到sp
                                sharedPreferences.edit().putString("avatar_path", savedPath).apply();
                            }
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        //初始化头像
        String avatarPath = sharedPreferences.getString("avatar_path", null);
        if (avatarPath != null) {
            Glide.with(requireContext())
                    .load(avatarPath)
                    .circleCrop()
                    .into(binding.imageView137);
        }

        //名字，邮箱
        String name = sharedPreferences.getString("name", "普通用户");
        binding.name.setText(name);
        String email = sharedPreferences.getString("email", "添加你的邮箱@？？？.com");
        binding.email.setText(email);

        //点击名字修改
        binding.name.setOnClickListener(v -> showInputDialog("修改名字", "name", binding.name));

        //点击邮箱修改
        binding.email.setOnClickListener(v -> showInputDialog("修改邮箱", "email", binding.email));

        //点击editTv上传头像,在相册获取
        binding.editTv.setOnClickListener(v -> openImagePicker());

        //TODO设置功能点击，未完成
        View[] aViews = {
                binding.a1, binding.a2, binding.a3, binding.a4, binding.a5
        };
        for (View aView : aViews) {
            aView.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "暂未开放功能", Toast.LENGTH_SHORT).show();
            });
        }

        //退出登录
        binding.a6.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), LoginActivity.class));
        });

        return binding.getRoot();
    }

    //打开系统相册选择图片
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    /**
     * 保存图片到内部存储，返回保存路径
     * **/
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            //打开图片，
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            File file = new File(requireContext().getFilesDir(), "avatar_" + System.currentTimeMillis() + ".jpg");

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                //创建一个缓冲区 buffer，一次读 1024 字节；length可以用来记录每次实际读取的字节数
                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            inputStream.close();

            //返回绝对路径
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "保存头像失败", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 处理输入框，统一处理保存的字符，传递参数1.弹窗标题，2.存储的key，3.TextView
     * **/
    private void showInputDialog(String title, String key, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        final EditText input = new EditText(requireContext());
        input.setText(textView.getText());

        builder.setView(input);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String text = input.getText().toString();
            sharedPreferences.edit().putString(key, text).apply();
            textView.setText(text);
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
