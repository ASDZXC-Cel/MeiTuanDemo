package com.example.meituandemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置5个店铺的门店图片
        ((ImageView) findViewById(R.id.shop1_icon)).setImageResource(R.drawable.shop_kfc);
        ((ImageView) findViewById(R.id.shop2_icon)).setImageResource(R.drawable.shop_yangguofu);
        ((ImageView) findViewById(R.id.shop3_icon)).setImageResource(R.drawable.shop_starbucks);
        ((ImageView) findViewById(R.id.shop4_icon)).setImageResource(R.drawable.shop_mcdonalds);
        ((ImageView) findViewById(R.id.shop5_icon)).setImageResource(R.drawable.shop_heytea);

        CardView shop1 = findViewById(R.id.shop1);
        CardView shop2 = findViewById(R.id.shop2);
        CardView shop3 = findViewById(R.id.shop3);
        CardView shop4 = findViewById(R.id.shop4);
        CardView shop5 = findViewById(R.id.shop5);

        shop1.setOnClickListener(v -> openShop(0));
        shop2.setOnClickListener(v -> openShop(1));
        shop3.setOnClickListener(v -> openShop(2));
        shop4.setOnClickListener(v -> openShop(3));
        shop5.setOnClickListener(v -> openShop(4));

        // 搜索栏点击
        findViewById(R.id.search_bar).setOnClickListener(v -> showSearchDialog());

        // 底部导航
        findViewById(R.id.nav_home).setOnClickListener(v -> {});
        findViewById(R.id.nav_order).setOnClickListener(v ->
            startActivity(new Intent(this, OrderListActivity.class)));
        findViewById(R.id.nav_profile).setOnClickListener(v ->
            startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void showSearchDialog() {
        EditText editText = new EditText(this);
        editText.setHint("输入店铺或美食名称");

        new AlertDialog.Builder(this)
            .setTitle("搜索")
            .setView(editText)
            .setPositiveButton("搜索", (dialog, which) -> {
                String keyword = editText.getText().toString();
                if (keyword.isEmpty()) {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "搜索：" + keyword + "（示例功能）", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }

    private void openShop(int type) {
        Intent intent = new Intent(this, ShopDetailActivity.class);
        intent.putExtra("shop_type", type);
        startActivity(intent);
    }
}
