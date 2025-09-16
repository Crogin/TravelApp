//package com.example.travelplanapp.Adapter;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.travelplanapp.Activities.PopularDetailActivity;
//import com.example.travelplanapp.Model.ItemModel;
//import com.example.travelplanapp.R;
//import com.example.travelplanapp.databinding.ViewholderPopular2Binding;
//
//import java.util.ArrayList;
//
//public class PopularAdapter2 extends RecyclerView.Adapter<PopularAdapter2.ViewHolder> {
//    ArrayList<ItemModel> list;
//    Context context;
//
//    public PopularAdapter2(ArrayList<ItemModel> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public PopularAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ViewholderPopular2Binding binding = ViewholderPopular2Binding.inflate(
//                LayoutInflater.from(parent.getContext()), parent, false);
//        return new ViewHolder(binding);
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull PopularAdapter2.ViewHolder holder, int position) {
//        ItemModel item = list.get(position);
//        holder.binding.titleTxt.setText(item.getTitle());
//        holder.binding.priceTxt.setText("￥" + item.getPrice());
//        holder.binding.addressTxt.setText(item.getAddress());
//        holder.binding.scoreTxt.setText("" + item.getScore());
//
//        // 加载图片，当时穿的图片是列表来的，按道理取第一个
//        if (item.getPic() != null && item.getPic().size() > 0) {
//            Glide.with(context)
//                    .load(item.getPic().get(0))
//                    .into(holder.binding.pic);
//        } else {
//            Glide.with(context)
//                    .load(R.drawable.loading)
//                    .into(holder.binding.pic);
//        }
//
//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(context, PopularDetailActivity.class);
//            intent.putExtra("object", item);
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        ViewholderPopular2Binding binding;
//        public ViewHolder(ViewholderPopular2Binding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
//}