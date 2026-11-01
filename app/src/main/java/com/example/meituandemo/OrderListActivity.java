package com.example.meituandemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Typeface;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderListActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        sp = getSharedPreferences("orders", MODE_PRIVATE);
        loadOrders();

        // 底部导航
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        findViewById(R.id.nav_order).setOnClickListener(v -> {});
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }

    private void loadOrders() {
        int count = sp.getInt("order_count", 0);

        LinearLayout container = findViewById(R.id.order_list_container);
        container.removeAllViews();

        if (count == 0) {
            TextView emptyView = new TextView(this);
            emptyView.setText("📭 暂无订单\n\n快去首页点餐吧～");
            emptyView.setTextSize(15);
            emptyView.setTextColor(0xFF999999);
            emptyView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 800
            );
            container.addView(emptyView, params);
            return;
        }

        // 倒序显示（最新的在上面）
        for (int i = count - 1; i >= 0; i--) {
            String shop = sp.getString("order_" + i + "_shop", "");
            String items = sp.getString("order_" + i + "_items", "");
            String price = sp.getString("order_" + i + "_price", "0");
            String status = sp.getString("order_" + i + "_status", "待收货");
            String time = sp.getString("order_" + i + "_time", "");
            String delivery = sp.getString("order_" + i + "_delivery", "");

            addOrderCard(container, i, shop, items, price, status, time, delivery);
        }
    }

    private void addOrderCard(LinearLayout container, final int index, String shop, String items,
                              String price, String status, String time, String delivery) {
        // 订单卡片
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(36, 36, 36, 36);
        card.setBackgroundColor(0xFFFFFFFF);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 20);
        card.setLayoutParams(cardParams);

        // 第一行：店铺名 + 状态
        LinearLayout topRow = new LinearLayout(this);
        topRow.setOrientation(LinearLayout.HORIZONTAL);

        TextView shopView = new TextView(this);
        shopView.setText(shop);
        shopView.setTextSize(16);
        shopView.setTypeface(shopView.getTypeface(), Typeface.BOLD);
        LinearLayout.LayoutParams shopParams = new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        topRow.addView(shopView, shopParams);

        TextView statusView = new TextView(this);
        statusView.setText(status);
        statusView.setTextSize(13);
        statusView.setTextColor(0xFFFF6B35);
        topRow.addView(statusView);
        card.addView(topRow);

        // 第二行：配送时长
        TextView deliveryView = new TextView(this);
        deliveryView.setText("🛵 预计送达：" + delivery);
        deliveryView.setTextSize(12);
        deliveryView.setTextColor(0xFF999999);
        LinearLayout.LayoutParams deliveryParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        deliveryParams.topMargin = 12;
        card.addView(deliveryView, deliveryParams);

        // 实付价格
        TextView priceView = new TextView(this);
        priceView.setText("实付：¥" + price);
        priceView.setTextSize(15);
        priceView.setTypeface(priceView.getTypeface(), Typeface.BOLD);
        priceView.setTextColor(0xFFFF6B35);
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        priceParams.topMargin = 16;
        card.addView(priceView, priceParams);

        // 点击订单查看详情
        card.setOnClickListener(v -> showOrderDetail(index, shop, items, price, status, time, delivery));

        container.addView(card);
    }

    private void showOrderDetail(int index, String shop, String items, String price,
                                  String status, String time, String delivery) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 36, 48, 24);

        TextView shopText = new TextView(this);
        shopText.setText(shop);
        shopText.setTextSize(18);
        shopText.setTypeface(shopText.getTypeface(), Typeface.BOLD);
        layout.addView(shopText);

        TextView statusText = new TextView(this);
        statusText.setText("订单状态：" + status);
        statusText.setTextSize(14);
        statusText.setTextColor(0xFFFF6B35);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        statusParams.topMargin = 24;
        layout.addView(statusText, statusParams);

        TextView timeText = new TextView(this);
        timeText.setText("下单时间：" + time);
        timeText.setTextSize(14);
        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        timeParams.topMargin = 12;
        layout.addView(timeText, timeParams);

        TextView deliveryText = new TextView(this);
        deliveryText.setText("配送时长：" + delivery);
        deliveryText.setTextSize(14);
        LinearLayout.LayoutParams deliveryParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        deliveryParams.topMargin = 12;
        layout.addView(deliveryText, deliveryParams);

        TextView itemsText = new TextView(this);
        itemsText.setText("商品明细：\n" + items);
        itemsText.setTextSize(14);
        LinearLayout.LayoutParams itemsParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        itemsParams.topMargin = 24;
        layout.addView(itemsText, itemsParams);

        TextView priceText = new TextView(this);
        priceText.setText("实付金额：¥" + price);
        priceText.setTextSize(16);
        priceText.setTypeface(priceText.getTypeface(), Typeface.BOLD);
        priceText.setTextColor(0xFFFF6B35);
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        priceParams.topMargin = 24;
        layout.addView(priceText, priceParams);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setTitle("订单详情")
            .setView(layout)
            .setPositiveButton("关闭", null);

        // 如果是待收货状态，加确认收货按钮
        if (status.equals("待收货")) {
            builder.setNegativeButton("确认收货", (dialog, which) -> {
                sp.edit().putString("order_" + index + "_status", "已完成").apply();
                Toast.makeText(this, "已确认收货，感谢光临！", Toast.LENGTH_SHORT).show();
                loadOrders();
            });
        }

        builder.show();
    }
}
