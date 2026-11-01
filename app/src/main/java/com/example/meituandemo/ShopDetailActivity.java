package com.example.meituandemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class ShopDetailActivity extends AppCompatActivity {

    private HashMap<String, Integer> cartCount = new HashMap<>();
    private HashMap<String, Double> cartPrice = new HashMap<>();

    private TextView cartList;
    private TextView totalPrice;
    private String shopName;
    private int shopType;

    // 5个店铺，每个店6个菜：[店铺][菜序号][0=菜名, 1=价格, 2=图标emoji]
    private String[][][] menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        shopType = getIntent().getIntExtra("shop_type", 0);

        // 5个店铺的菜单：[菜名, 价格, emoji, 图片资源]
        menus = new String[][][]{
            // 0 肯德基
            {
                {"香辣鸡腿堡", "15.5", "🍔", "food_burger"},
                {"新奥尔良烤翅", "12.0", "🍗", "food_orleans"},
                {"百事可乐(中)", "9.5", "🥤", "food_cola"},
                {"薯条(大份)", "11.0", "🍟", "food_fries"},
                {"香辣鸡翅(2块)", "10.5", "🍗", "food_spicywing"},
                {"葡式蛋挞", "8.0", "🥧", "food_eggtart"}
            },
            // 1 杨国福麻辣烫
            {
                {"经典麻辣拌", "18.0", "🍲", "food_malatang"},
                {"骨汤麻辣烫", "22.0", "🍜", "food_malatang"},
                {"酸梅汤", "6.0", "🧃", "food_suanmeitang"},
                {"麻酱凉皮", "10.0", "🥗", "food_liangpi"},
                {"炸酥肉", "15.0", "🍢", "food_surou"},
                {"冰粉", "7.0", "🍮", "food_bingfen"}
            },
            // 2 星巴克
            {
                {"美式咖啡", "28.0", "☕", "food_coffee"},
                {"拿铁", "32.0", "🍶", "food_coffee"},
                {"提拉米苏", "35.0", "🍰", "food_tiramisu"},
                {"卡布奇诺", "30.0", "☕", "food_cappuccino"},
                {"蓝莓麦芬", "25.0", "🧁", "food_muffin"},
                {"焦糖玛奇朵", "34.0", "🍮", "food_macchiato"}
            },
            // 3 麦当劳
            {
                {"巨无霸汉堡", "22.0", "🍔", "food_burger"},
                {"麦乐鸡(6块)", "18.0", "🍗", "food_nuggets"},
                {"麦旋风", "12.0", "🍦", "food_icecream"},
                {"大薯条", "10.0", "🍟", "food_fries"},
                {"吉士汉堡", "13.0", "🍔", "food_burger"},
                {"橙汁", "8.0", "🧃", "food_orangejuice"}
            },
            // 4 喜茶
            {
                {"多肉葡萄", "29.0", "🍇", "food_grape"},
                {"芝士奶盖茶", "25.0", "🍵", "food_naigai"},
                {"波波茶", "27.0", "🧋", "food_bobo"},
                {"金凤茶王", "22.0", "🍵", "food_coffee"},
                {"烤黑糖波波", "26.0", "🧋", "food_bobo"},
                {"芒果糯米饭", "24.0", "🥭", "food_mango"}
            }
        };

        String[] shopNames = {
            "肯德基(黄兴广场店)",
            "杨国福麻辣烫",
            "星巴克(悦方店)",
            "麦当劳(黄兴路店)",
            "喜茶(IFS店)"
        };
        String[] notices = {
            "营业中 | 欢迎光临肯德基，点餐满30元免配送费！",
            "营业中 | 杨国福麻辣烫，新鲜食材现选现煮！",
            "营业中 | 星巴克咖啡，第二杯半价！",
            "营业中 | 麦当劳，超值套餐优惠中！",
            "营业中 | 喜茶，多肉葡萄新品上市！"
        };
        String[] deliveryFees = {"配送费 ¥3", "配送费 ¥2", "配送费 ¥5", "配送费 ¥3", "配送费 ¥4"};
        String[] deliveryTimes = {"约30分钟", "约25分钟", "约40分钟", "约28分钟", "约35分钟"};

        shopName = shopNames[shopType];
        ((TextView) findViewById(R.id.shop_name_title)).setText(shopName);
        ((TextView) findViewById(R.id.shop_notice)).setText(notices[shopType]);
        ((TextView) findViewById(R.id.shop_delivery_fee)).setText(deliveryFees[shopType]);
        ((TextView) findViewById(R.id.shop_delivery_time)).setText(deliveryTimes[shopType]);

        // 设置6个菜品
        for (int i = 0; i < 6; i++) {
            String foodName = menus[shopType][i][0];
            String foodPrice = menus[shopType][i][1];
            String foodIcon = menus[shopType][i][2];

            int nameId = getResources().getIdentifier("food" + (i+1) + "_name", "id", getPackageName());
            int priceId = getResources().getIdentifier("food" + (i+1) + "_price", "id", getPackageName());
            int iconId = getResources().getIdentifier("food" + (i+1) + "_icon", "id", getPackageName());

            ((TextView) findViewById(nameId)).setText(foodName);
            ((TextView) findViewById(priceId)).setText("¥" + foodPrice);
            // 设置真实图片
            String imgName = menus[shopType][i][3];
            int imgResId = getResources().getIdentifier(imgName, "drawable", getPackageName());
            ((ImageView) findViewById(iconId)).setImageResource(imgResId);

            cartPrice.put(foodName, Double.parseDouble(foodPrice));
            cartCount.put(foodName, 0);
        }

        cartList = findViewById(R.id.cart_empty);
        totalPrice = findViewById(R.id.total_price);

        // 返回
        findViewById(R.id.back_btn).setOnClickListener(v -> finish());

        // 6个+按钮
        for (int i = 0; i < 6; i++) {
            int btnId = getResources().getIdentifier("add_food" + (i+1), "id", getPackageName());
            final int index = i;
            findViewById(btnId).setOnClickListener(v -> {
                String foodName = menus[shopType][index][0];
                cartCount.put(foodName, cartCount.get(foodName) + 1);
                updateCart();
                Toast.makeText(ShopDetailActivity.this, "已加入：" + foodName, Toast.LENGTH_SHORT).show();
            });
        }

        // 点击菜品跳详情
        for (int i = 0; i < 6; i++) {
            int foodId = getResources().getIdentifier("food" + (i+1), "id", getPackageName());
            final int index = i;
            findViewById(foodId).setOnClickListener(v -> {
                Intent intent = new Intent(ShopDetailActivity.this, FoodDetailActivity.class);
                intent.putExtra("food_name", menus[shopType][index][0]);
                intent.putExtra("food_price", menus[shopType][index][1]);
                intent.putExtra("food_icon", menus[shopType][index][2]);
                intent.putExtra("food_img", menus[shopType][index][3]);
                startActivity(intent);
            });
        }

        // 去结算
        findViewById(R.id.go_order_btn).setOnClickListener(v -> {
            double total = calculateTotal();
            if (total <= 0) {
                Toast.makeText(ShopDetailActivity.this, "购物车是空的，先加点菜吧～", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ShopDetailActivity.this, OrderActivity.class);
            intent.putExtra("shop_name", shopName);
            intent.putExtra("total_price", total);
            StringBuilder cartInfo = new StringBuilder();
            for (String name : cartCount.keySet()) {
                int count = cartCount.get(name);
                double price = cartPrice.get(name);
                if (count > 0) {
                    cartInfo.append(name)
                            .append("  x").append(count)
                            .append("  ¥").append(String.format("%.1f", count * price))
                            .append("\n");
                }
            }
            intent.putExtra("cart_info", cartInfo.toString().trim());
            startActivity(intent);
        });
    }

    private double calculateTotal() {
        double total = 0;
        for (String name : cartCount.keySet()) {
            total += cartCount.get(name) * cartPrice.get(name);
        }
        return total;
    }

    private void updateCart() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        boolean hasItem = false;
        for (String name : cartCount.keySet()) {
            int count = cartCount.get(name);
            double price = cartPrice.get(name);
            if (count > 0) {
                hasItem = true;
                sb.append(name).append(" x").append(count)
                  .append("  ¥").append(String.format("%.1f", count * price)).append("\n");
                total += count * price;
            }
        }
        cartList.setText(hasItem ? sb.toString().trim() : "购物车是空的");
        totalPrice.setText("合计：¥" + String.format("%.1f", total));
    }
}
