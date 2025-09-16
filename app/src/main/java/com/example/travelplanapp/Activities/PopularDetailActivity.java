package com.example.travelplanapp.Activities;

import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.travelplanapp.Model.ItemModel;
import com.example.travelplanapp.databinding.ActivityPopularDetailBinding;

public class PopularDetailActivity extends BaseActivity {
    ActivityPopularDetailBinding binding;
    private ItemModel object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopularDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        getIntentExtra();
        setVariable();

    }
    private void setVariable() {
        binding.titleTxt.setText(object.getTitle());
        binding.addressTxt.setText(object.getAddress());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.bedTxt.setText(object.getTimeTour());
        binding.descriptionTxt.setText(object.getDescription());
        binding.durationTxt.setText(object.getDuration());
        binding.distanceTxt.setText(object.getDistance());
        binding.addressTxt.setText(object.getAddress());
        binding.priceTxt.setText("￥"+object.getPrice());
        binding.ratingTxt.setText(""+object.getScore()+"评分");
        binding.ratingBar.setRating((float) object.getScore());

        Glide.with(this)
                .load(object.getPic().get(0))
                .into(binding.imageView4);

        binding.addToCartBtn.setOnClickListener(v -> {
        });
    }

    private void getIntentExtra() {
        object = (ItemModel) getIntent().getSerializableExtra("object");
    }
}