package com.citynote.sanky.ut_preliminary.Photos;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by pw on 17/3/2559.
 */
public class Api {

    public static final String API_URL = "http://blessindia.in";

    private static LoadList Iapi_loadList;

    public static LoadList Iapi_loadList() {
        if (Iapi_loadList == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Iapi_loadList = retrofit.create(LoadList.class);
        }
        return Iapi_loadList;
    }

    public interface LoadList {
        @POST("/webservice/api_img.php")


        retrofit.Call<PrettyModel> getPrettyModel();
    }

}