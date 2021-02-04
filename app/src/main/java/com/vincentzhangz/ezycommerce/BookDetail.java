package com.vincentzhangz.ezycommerce;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetail extends AppCompatActivity {
    ApiInterface apiInterface;
    TextView bookName, bookPrice, bookDescription;
    ImageView bookImage;
    Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookName = findViewById(R.id.book_name_detail);
        bookPrice = findViewById(R.id.book_price_detail);
        bookDescription = findViewById(R.id.book_description_detail);
        bookImage = findViewById(R.id.book_image_detail);
        buyButton = findViewById(R.id.buy_button);
        int bookId = Integer.parseInt(getIntent().getStringExtra("book_id"));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetBook> bookCall = apiInterface.getBookDetail(bookId);
        bookCall.enqueue(new Callback<GetBook>() {

            @Override
            public void onResponse(Call<GetBook> call, Response<GetBook> response) {
                ArrayList<Book> bookList = response.body().getBooks();
                Book book = bookList.get(0);
                bookName.setText(book.getName());
                bookPrice.setText(String.format("$%s", book.getPrice().toString()));
                bookDescription.setText(book.getDescription());
                Picasso.get()
                        .load(book.getImg())
                        .resize(400, 400)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_image_24)
                        .into(bookImage);

                buyButton.setOnClickListener(v -> {
                    ArrayList<Cart> cart = MainActivity.getCart();
                    boolean alreadyInCart = false;
                    for (Cart c : cart) {
                        if (c.getBook().getId() == book.getId()) {
                            c.setQuantity(c.getQuantity() + 1);
                            alreadyInCart = true;
                            break;
                        }
                    }

                    if (!alreadyInCart) {
                        cart.add(new Cart(book, 1));
                    }

                });
            }

            @Override
            public void onFailure(Call<GetBook> call, Throwable t) {

            }
        });
    }
}