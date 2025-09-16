package com.example.travelplanapp.Fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.travelplanapp.Adapter.CategoryAdapter;
import com.example.travelplanapp.Adapter.PopularAdapter2;
import com.example.travelplanapp.Model.CategoryModel;
import com.example.travelplanapp.Model.ItemModel;
import com.example.travelplanapp.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private PopularAdapter2 adapter;
    private FragmentHomeBinding binding;
    private Call currentCall = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //初始化RecyclerView，瀑布流
        binding.recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        adapter = new PopularAdapter2(requireContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        //初始化分类
        initCategory();
        initPopular();

        binding.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "暂未开发", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    //网络请求的数据展示，本地也有json文件
    private void initPopular() {
        //显示加载进度可见
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setVisibility(View.GONE);

        OkHttpClient client = new OkHttpClient();
        String url = "http://8.138.243.249:8080/spots";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        currentCall = client.newCall(request);
        currentCall.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                //切回主线程更新UI，用binding.recyclerView.post()
                if (binding != null) {
                    binding.recyclerView.post(() -> {
                        binding.progressBarCategory.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "网络请求失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body() != null ? response.body().string() : "";
                Log.d("HomeFragment", "onResponse: " + body);
                //创建一个ArrayList，用来请求回来的json数据列表
                final ArrayList<ItemModel> items = new ArrayList<>();

                if (response.isSuccessful()) {
                    try {
                        JSONObject root = new JSONObject(body);
                        int status = root.optInt("status", -1);
                        if (status == 0) {
                            JSONArray dataArray = root.optJSONArray("data");
                            if (dataArray != null) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject obj = dataArray.optJSONObject(i);
                                    if (obj == null) continue;

                                    ItemModel model = new ItemModel();
                                    model.setTitle(obj.optString("title", ""));
                                    model.setAddress(obj.optString("address", ""));
                                    model.setPrice(obj.optInt("price", 0));
                                    model.setScore(obj.optDouble("score", 0.0));
                                    model.setDescription(obj.optString("description", "没有哈哈"));
                                    model.setTimeTour(obj.optString("tourCount", ""));
                                    model.setDistance(obj.optString("distance", ""));
                                    model.setDuration(obj.optString("duration", ""));

                                    JSONArray picArray = obj.optJSONArray("pic");
                                    ArrayList<String> pics = new ArrayList<>();
                                    if (picArray != null) {
                                        for (int p = 0; p < picArray.length(); p++) {
                                            String picUrl = picArray.optString(p, "");
                                            if (!picUrl.isEmpty()) pics.add(picUrl);
                                        }
                                    }
                                    model.setPic(pics);
                                    items.add(model);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (binding != null) {
                    binding.recyclerView.post(() -> {
                        binding.progressBarCategory.setVisibility(View.GONE);
                        if (!items.isEmpty()) {
                            adapter.submitList(new ArrayList<>(items));
                            binding.recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            binding.recyclerView.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "没有热门数据", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // 初始化分类，获取数据来源是assets/category.json，本地json文件解析出来的数据
    private void initCategory() {
        //显示加载进度可见
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        ArrayList<CategoryModel> items = new ArrayList<>();

        try {
            InputStream is = getActivity().getAssets().open("category.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray categoryArray = jsonObject.getJSONArray("Category");

            for (int i = 0; i < categoryArray.length(); i++) {
                JSONObject obj = categoryArray.getJSONObject(i);
                CategoryModel model = new CategoryModel();
                model.setId(obj.getInt("Id"));
                model.setName(obj.getString("Name"));
                model.setImagePath(obj.getString("ImagePath"));
                items.add(model);
            }

            if (!items.isEmpty()) {
                binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 5));
                CategoryAdapter adapter = new CategoryAdapter(items);
                binding.recyclerViewCategory.setAdapter(adapter);
            }

            binding.progressBarCategory.setVisibility(View.GONE);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            binding.progressBarCategory.setVisibility(View.GONE);
        }
    }
}