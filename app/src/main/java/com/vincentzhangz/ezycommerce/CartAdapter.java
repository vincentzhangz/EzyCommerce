package com.vincentzhangz.ezycommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<Cart> carts;

    public CartAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = this.carts.get(position);
        TextView name = holder.name;
        TextView price = holder.price;
        TextView quantity = holder.quantity;
        Button increase = holder.increase;
        Button decrease = holder.decrease;

        name.setText(cart.getBook().getName());
        quantity.setText(cart.getQuantity().toString());
        price.setText(String.format("$%s", cart.getBook().getPrice().toString()));
        holder.setImage(cart.getBook().getImg());

        increase.setOnClickListener(v -> {
            this.carts.get(position).setQuantity(this.carts.get(position).getQuantity() + 1);
            quantity.setText(cart.getQuantity().toString());
            ShoppingCart.setData();
        });

        decrease.setOnClickListener(v -> {
            if (this.carts.get(position).getQuantity() > 1) {
                this.carts.get(position).setQuantity(this.carts.get(position).getQuantity() - 1);
            }
            quantity.setText(cart.getQuantity().toString());
            ShoppingCart.setData();
        });
    }

    @Override
    public int getItemCount() {
        return this.carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        TextView quantity;
        Button increase, decrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.book_name);
            price = itemView.findViewById(R.id.book_price);
            image = itemView.findViewById(R.id.book_image);
            quantity = itemView.findViewById(R.id.quantity);
            increase = itemView.findViewById(R.id.increase);
            decrease = itemView.findViewById(R.id.decrease);
        }

        public void setImage(String url) {
            Picasso.get()
                    .load(url)
                    .resize(100, 100)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_image_24)
                    .into(image);
        }
    }
}
