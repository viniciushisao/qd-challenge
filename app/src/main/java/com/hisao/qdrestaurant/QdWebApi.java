package com.hisao.qdrestaurant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinicius on 25/03/17.
 */

public class QdWebApi {

    private static QdApiInterface qdApiInterface;

    private QdWebApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://s3-eu-west-1.amazonaws.com/quandoo-assessment/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        qdApiInterface = retrofit.create(QdApiInterface.class);
    }

    public static QdApiInterface getQdApiInterface() {
        if (qdApiInterface == null) {
            new QdWebApi();
        }
        return qdApiInterface;
    }

}
