package com.example.meituandemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        String foodName = getIntent().getStringExtra("food_name");
        String foodPrice = getIntent().getStringExtra("food_price");
        String foodImg = getIntent().getStringExtra("food_img");

        if (foodName != null) {
            ((TextView) findViewById(R.id.detail_food_name)).setText(foodName);
            ImageView bigImg = findViewById(R.id.food_big_icon);
            if (foodImg != null) {
                int imgResId = getResources().getIdentifier(foodImg, "drawable", getPackageName());
                bigImg.setImageResource(imgResId);
            }
        }
        if (foodPrice != null) {
            ((TextView) findViewById(R.id.detail_food_price)).setText("¥" + foodPrice);
        }

        TextView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> finish());

        Button addCartBtn = findViewById(R.id.add_cart_btn);
        addCartBtn.setOnClickListener(v -> {
            Toast.makeText(FoodDetailActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
