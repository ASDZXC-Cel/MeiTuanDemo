package com.example.meituandemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.AlertDialog;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 菜单项点击
        LinearLayout menuAddress = findViewById(R.id.menu_address);
        LinearLayout menuCoupon = findViewById(R.id.menu_coupon);
        LinearLayout menuSetting = findViewById(R.id.menu_setting);

        // 修改收货地址
        menuAddress.setOnClickListener(v -> showAddressDialog());

        menuCoupon.setOnClickListener(v -> Toast.makeText(this, "您有 0 张优惠券", Toast.LENGTH_SHORT).show());
        menuSetting.setOnClickListener(v -> Toast.makeText(this, "设置（示例）", Toast.LENGTH_SHORT).show());

        // 底部导航
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        findViewById(R.id.nav_order).setOnClickListener(v -> {
            startActivity(new Intent(this, OrderListActivity.class));
            finish();
        });
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            // 已经在我的页面
        });
    }

    private void showAddressDialog() {
        EditText editText = new EditText(this);
        editText.setText("湖南省长沙市天心区黄兴广场55号");
        editText.setSelection(editText.getText().length());

        new AlertDialog.Builder(this)
            .setTitle("修改收货地址")
            .setView(editText)
            .setPositiveButton("保存", (dialog, which) -> {
                String newAddress = editText.getText().toString();
                Toast.makeText(this, "地址已保存：" + newAddress, Toast.LENGTH_LONG).show();
            })
            .setNegativeButton("取消", null)
            .show();
    }
}
