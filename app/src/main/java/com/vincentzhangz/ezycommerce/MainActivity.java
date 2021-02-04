package com.vincentzhangz.ezycommerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ApiInterface apiInterface;
    FloatingActionButton cartButton;
    static ArrayList<Cart> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cart = new ArrayList<>();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        cartButton = findViewById(R.id.cart);
        tabLayout.addTab(tabLayout.newTab().setText("Business"));
        tabLayout.addTab(tabLayout.newTab().setText("Mystery"));
        tabLayout.addTab(tabLayout.newTab().setText("Cookbooks"));
        tabLayout.addTab(tabLayout.newTab().setText("Accessories"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        refresh(this);

        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingCart.class);
            startActivity(intent);
        });

    }

    protected void refresh(Context context) {
        Call<GetBook> bookCall = apiInterface.getBook();
        bookCall.enqueue(new Callback<GetBook>() {

            @Override
            public void onResponse(Call<GetBook> call, Response<GetBook> response) {
                ArrayList<Book> bookList = response.body().getBooks();
                Log.d("Retrofit Get", "Data count: " + bookList.size());
                final FragmentAdapter adapter = new FragmentAdapter(context, getSupportFragmentManager(), tabLayout.getTabCount(), bookList);
                viewPager.setAdapter(adapter);

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }

            @Override
            public void onFailure(Call<GetBook> call, Throwable t) {

            }
        });
    }

    public static ArrayList<Cart> getCart() {
        return cart;
    }

    public static void setCart(ArrayList<Cart> cart) {
        MainActivity.cart = cart;
    }
}