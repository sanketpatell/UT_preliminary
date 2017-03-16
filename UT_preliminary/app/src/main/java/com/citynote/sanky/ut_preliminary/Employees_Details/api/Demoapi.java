package com.citynote.sanky.ut_preliminary.Employees_Details.api;

import retrofit.Callback;
import retrofit.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by sanky on 15-03-2017.
 */
public interface Demoapi {
    @FormUrlEncoded
    @POST("/webservice/all_employee_details.php")
    public void reg_signup(@Field("employee_id") String user_name,
                           @Field("password") String mobile,
                           Callback<Response> callback);
}