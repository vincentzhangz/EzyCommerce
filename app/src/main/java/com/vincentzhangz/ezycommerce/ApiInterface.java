package com.vincentzhangz.ezycommerce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("book?nim=2201798383&nama=Vincent")
    Call<GetBook> getBook();

    @GET("book/{bookId}?nim=2201798383&nama=Vincent")
    Call<GetBook> getBookDetail(@Path("bookId") int bookId);
}
