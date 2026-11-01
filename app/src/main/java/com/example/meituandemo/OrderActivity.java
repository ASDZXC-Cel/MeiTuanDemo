package com.example.meituandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private double totalPrice;
    private String shopName;
    private String cartInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 接收从店铺详情传来的数据
        shopName = getIntent().getStringExtra("shop_name");
        totalPrice = getIntent().getDoubleExtra("total_price", 0);
        cartInfo = getIntent().getStringExtra("cart_info");

        double deliveryFee = 3.0;
        double finalPay = totalPrice + deliveryFee;

        // 设置店铺名
        ((TextView) findViewById(R.id.order_shop_name)).setText("🏪 " + shopName);

        // 设置购物车明细
        ((TextView) findViewById(R.id.order_cart_list)).setText(cartInfo);

        // 设置金额
        ((TextView) findViewById(R.id.order_goods_total)).setText("商品总价：¥" + String.format("%.1f", totalPrice));
        ((TextView) findViewById(R.id.order_total_pay)).setText("实付：¥" + String.format("%.1f", finalPay));

        // 返回
        TextView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 去支付 → 支付界面
        Button goPayBtn = findViewById(R.id.go_pay_btn);
        goPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, PayActivity.class);
                intent.putExtra("pay_amount", finalPay);
                intent.putExtra("shop_name", shopName);
                startActivity(intent);
            }
        });
    }
}
