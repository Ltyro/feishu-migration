package com.lark.api.doc;

import com.lark.data.bean.Response;
import com.lark.data.dto.doc.DocDTO;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DocClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("doc/v2/create")
    Call<Response<DocDTO>> create(@Header("Authorization") String authorization, @Body Map<String, Object> data);
}
