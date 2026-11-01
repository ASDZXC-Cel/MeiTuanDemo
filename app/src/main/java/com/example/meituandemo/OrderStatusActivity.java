package com.example.meituandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OrderStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        String shopName = getIntent().getStringExtra("shop_name");
        double payAmount = getIntent().getDoubleExtra("pay_amount", 0);

        ((TextView) findViewById(R.id.order_amount)).setText("实付：¥" + String.format("%.1f", payAmount));
        ((TextView) findViewById(R.id.order_shop)).setText("店铺：" + shopName);

        Button backHomeBtn = findViewById(R.id.back_home_btn);
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderStatusActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
