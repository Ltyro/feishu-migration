package com.lark.utils;

import com.google.gson.Gson;
import com.lark.data.bean.ResponseEntity;
import com.lark.data.dto.api.ApiErrorDTO;
import com.lark.exception.ApiException;
import com.lark.exception.CustomException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {
    private static final Logger log = LoggerFactory.getLogger(RetrofitUtils.class);
    static String baseUrl = "https://open.feishu.cn/open-apis/";
    static OkHttpClient client;

    public RetrofitUtils() {
    }

    public static void setBaseUrl(String baseUrl) {
        RetrofitUtils.baseUrl = baseUrl;
    }

    public static Retrofit createRetrofit() {
        return createRetrofit(baseUrl);
    }

    public static Retrofit createRetrofit(String baseUrl) {
        return (new Retrofit.Builder()).baseUrl(baseUrl).client(client).addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public static String getAuthorizationToken(String token) {
        return "Bearer " + token;
    }

    public static <T> T execute(Call<T> call) throws ApiException, CustomException {
        try {
            Response<T> data = call.execute();
            ResponseBody errorBody = data.errorBody();
            ApiErrorDTO apiError = null;
            if (errorBody != null) {
                String errorBodyStr = errorBody.string();
                log.error("接口调用出错：{}", errorBodyStr);
                log.info("接口的详细信息为：{}， {}", (new Gson()).toJson(call.request().url()), (new Gson()).toJson(call.request().body()));
                if ((apiError = (ApiErrorDTO)JacksonUtils.string2Obj(errorBodyStr, ApiErrorDTO.class)) != null) {
                    throw new ApiException(apiError.getCode(), apiError.getMsg());
                } else {
                    throw new CustomException("接口请求失败:" + errorBodyStr);
                }
            } else {
                T body = (T)data.body();
                if (body instanceof ResponseEntity) {
                    ResponseEntity response = (ResponseEntity)body;
                    if (!Integer.valueOf(0).equals(response.getCode())) {
                        throw new ApiException(response.getCode(), response.getMsg());
                    }
                }

                return body;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            log.error("接口请求有误：{}", exception.getMessage());
            throw new CustomException("接口请求有误", exception);
        }
    }

    static {
        client = (new OkHttpClient.Builder()).connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).build();
    }
}
