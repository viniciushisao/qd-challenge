package com.hisao.qdrestaurant;

import com.hisao.qdrestaurant.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vinicius on 24/03/17.
 */

public interface QdApiInterface {

    @GET("customer-list.json")
    Call<List<Customer>> getCustomers();

    @GET("table-map.json")
    Call<boolean[]> getTables();

}
