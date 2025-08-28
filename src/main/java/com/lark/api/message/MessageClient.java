package com.lark.api.message;

import com.lark.data.bean.Response;
import com.lark.data.dto.message.SendMessageRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MessageClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("im/v1/messages")
    Call<Response<Object>> sendMessage(@Header("Authorization") String authorization, @Query("receive_id_type") String receiveType, @Body SendMessageRequest request);
}
