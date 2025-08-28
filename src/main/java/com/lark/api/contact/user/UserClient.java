package com.lark.api.contact.user;

import com.lark.data.bean.Response;
import com.lark.data.dto.contact.UserDTO;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface UserClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("contact/v3/users/{user_id}")
    Call<Response<Map<String, UserDTO>>> queryUser(@Header("Authorization") String authorization, @Path("user_id") String userId, @QueryMap Map<String, Object> params);
}
