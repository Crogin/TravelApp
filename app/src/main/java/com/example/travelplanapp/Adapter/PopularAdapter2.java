package com.example.travelplanapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelplanapp.Activities.PopularDetailActivity;
import com.example.travelplanapp.Model.ItemModel;
import com.example.travelplanapp.R;
import com.example.travelplanapp.databinding.ViewholderPopular2Binding;

public class PopularAdapter2 extends ListAdapter<ItemModel, PopularAdapter2.ViewHolder> {

    private final Context context;

    public PopularAdapter2(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<ItemModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ItemModel>() {
        /**
         * 为了能够识别新的数据，DiffUtil 需要您重写 areItemsTheSame()和 areContentsTheSame()。
         * areItemsTheSame() 检查两个元素是否为同一元素。
         * areContentsTheSame() 检查两个元素是否包含相同的数据。
         * **/

                @Override
                public boolean areItemsTheSame(@NonNull ItemModel oldItem, @NonNull ItemModel newItem) {
                    //判断id是否相同
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull ItemModel oldItem, @NonNull ItemModel newItem) {
                    //判断内容是否相同
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public PopularAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPopular2Binding binding = ViewholderPopular2Binding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PopularAdapter2.ViewHolder holder, int position) {
        ItemModel item = getItem(position);

        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("￥" + item.getPrice());
        holder.binding.addressTxt.setText(item.getAddress());
        holder.binding.scoreTxt.setText("" + item.getScore());

        // 图片path不是空，则加载第一张图片
        if (item.getPic() != null && item.getPic().size() > 0) {
            Glide.with(context)
                    .load(item.getPic().get(0))
                    .error(R.drawable.loading)
                    .into(holder.binding.pic);
        } else {
            //展位图
            Glide.with(context)
                    .load(R.drawable.loading)
                    .into(holder.binding.pic);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PopularDetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderPopular2Binding binding;
        public ViewHolder(ViewholderPopular2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
