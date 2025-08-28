package com.lark.api.auth;

import com.lark.data.bean.Response;
import com.lark.data.dto.token.GetTenantAccessTokenRequest;
import com.lark.data.dto.token.GetUserAccessTokenRequest;
import com.lark.data.dto.token.RefreshUserAccessTokenRequest;
import com.lark.data.dto.token.TenantAccessTokenDTO;
import com.lark.data.dto.token.UserAccessTokenDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccessTokenClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("auth/v3/tenant_access_token/internal/")
    Call<TenantAccessTokenDTO> queryTenantAccessToken(@Body GetTenantAccessTokenRequest request);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("authen/v1/access_token")
    Call<Response<UserAccessTokenDTO>> queryUserAccessTokenInfo(@Header("Authorization") String token, @Body GetUserAccessTokenRequest request);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("authen/v1/refresh_access_token")
    Call<Response<UserAccessTokenDTO>> refreshUserAccessTokenInfo(@Header("Authorization") String token, @Body RefreshUserAccessTokenRequest request);
}
