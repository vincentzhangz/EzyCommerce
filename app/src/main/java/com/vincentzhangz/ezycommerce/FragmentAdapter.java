package com.vincentzhangz.ezycommerce;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {
    int totalTabs;
    private Context context;
    private ArrayList<Book> books;

    public FragmentAdapter(Context context, FragmentManager fm, int totalTabs, ArrayList<Book> books) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.totalTabs = totalTabs;
        this.books = books;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ListBook businessFragment = new ListBook();
                ArrayList<Book> businessBook = new ArrayList<>();
                for (Book book : books) {
                    if (book.getCategory().equals("business")) {
                        businessBook.add(book);
                    }
                }
                businessFragment.setBooks(businessBook);
                return businessFragment;
            case 1:
                ListBook mysteryFragment = new ListBook();
                ArrayList<Book> mysteryBook = new ArrayList<>();
                for (Book book : books) {
                    if (book.getCategory().equals("mystery")) {
                        mysteryBook.add(book);
                    }
                }
                mysteryFragment.setBooks(mysteryBook);
                return mysteryFragment;
            case 2:
                ListBook cookbooksFragment = new ListBook();
                ArrayList<Book> cookbooksBook = new ArrayList<>();
                for (Book book : books) {
                    if (book.getCategory().equals("cookbooks")) {
                        cookbooksBook.add(book);
                    }
                }
                cookbooksFragment.setBooks(cookbooksBook);
                return cookbooksFragment;
            case 3:
                ListBook accessoriesFragment = new ListBook();
                ArrayList<Book> accessoriesBook = new ArrayList<>();
                for (Book book : books) {
                    if (book.getCategory().equals("accessories")) {
                        accessoriesBook.add(book);
                    }
                }
                accessoriesFragment.setBooks(accessoriesBook);
                return accessoriesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
