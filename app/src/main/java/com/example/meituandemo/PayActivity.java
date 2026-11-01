package com.example.meituandemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        double payAmount = getIntent().getDoubleExtra("pay_amount", 0);
        String shopName = getIntent().getStringExtra("shop_name");
        String cartList = getIntent().getStringExtra("cart_list");

        // 设置支付金额
        ((TextView) findViewById(R.id.pay_amount_text)).setText("¥" + String.format("%.2f", payAmount));

        // 返回
        TextView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> finish());

        // 确认支付
        Button confirmPayBtn = findViewById(R.id.confirm_pay_btn);
        confirmPayBtn.setOnClickListener(v -> {
            // 保存订单到SharedPreferences
            saveOrder(shopName, cartList, payAmount);

            Toast.makeText(PayActivity.this, "支付成功！", Toast.LENGTH_SHORT).show();
            // 跳转到订单状态页
            Intent intent = new Intent(PayActivity.this, OrderStatusActivity.class);
            intent.putExtra("shop_name", shopName);
            intent.putExtra("pay_amount", payAmount);
            startActivity(intent);
            finish();
        });
    }

    private void saveOrder(String shopName, String cartList, double price) {
        SharedPreferences sp = getSharedPreferences("orders", MODE_PRIVATE);
        int count = sp.getInt("order_count", 0);

        // 生成下单时间
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd HH:mm", java.util.Locale.getDefault());
        String orderTime = sdf.format(new java.util.Date());

        // 随机配送时长
        String[] deliveryTimes = {"约25分钟", "约30分钟", "约35分钟", "约40分钟"};
        String deliveryTime = deliveryTimes[(int)(Math.random() * deliveryTimes.length)];

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("order_" + count + "_shop", shopName);
        editor.putString("order_" + count + "_items", cartList);
        editor.putString("order_" + count + "_price", String.format("%.1f", price));
        editor.putString("order_" + count + "_status", "待收货");
        editor.putString("order_" + count + "_time", orderTime);
        editor.putString("order_" + count + "_delivery", deliveryTime);
        editor.putInt("order_count", count + 1);
        editor.apply();
    }
}
