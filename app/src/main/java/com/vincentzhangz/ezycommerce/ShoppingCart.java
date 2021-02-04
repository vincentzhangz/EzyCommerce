package com.vincentzhangz.ezycommerce;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {
    RecyclerView recyclerView;
    static TextView subtotalView, taxesView, totalView, shippingView;
    Button cancelButton, buyButton;
    static ArrayList<Cart> carts;
    static Double subtotal = 0.0;
    static Double total = 0.0;
    static Double taxes = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        carts = MainActivity.getCart();

        recyclerView = findViewById(R.id.cart_list);
        subtotalView = findViewById(R.id.subtotal);
        shippingView = findViewById(R.id.shipping);
        taxesView = findViewById(R.id.taxes);
        totalView = findViewById(R.id.total);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CartAdapter(carts));
        buyButton = findViewById(R.id.buy_button);
        cancelButton = findViewById(R.id.cancel_button);
        buyButton.setOnClickListener(v -> {
            carts.clear();
            Toast.makeText(getApplicationContext(),
                    "Purchase success", Toast.LENGTH_LONG).show();
            finish();
        });
        cancelButton.setOnClickListener(v -> {
            finish();
        });

        setData();
    }

    public static void setData() {
        subtotal = total = taxes = 0.0;
        for (Cart cart : carts) {
            subtotal = subtotal + cart.getBook().getPrice() * cart.getQuantity();
        }

        taxes = subtotal * 10 / 100;
        total = taxes + subtotal;

        subtotalView.setText(String.format("Subtotal : $%1$,.2f", subtotal));
        taxesView.setText(String.format("Taxes : $%1$,.2f", taxes));
        shippingView.setText(String.format("Shipping : %s", "Free"));
        totalView.setText(String.format("Total : $%1$,.2f", total));
    }
}