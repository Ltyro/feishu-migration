package com.lark.api.auth;

import com.lark.data.bean.Response;
import com.lark.data.dto.token.GetTenantAccessTokenRequest;
import com.lark.data.dto.token.GetUserAccessTokenRequest;
import com.lark.data.dto.token.RefreshUserAccessTokenRequest;
import com.lark.data.dto.token.TenantAccessTokenDTO;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.exception.ApiException;
import com.lark.utils.RetrofitUtils;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class AccessTokenApi {
    public AccessTokenApi() {
    }

    public TenantAccessTokenDTO getTenantAccessTokenResponse(String appId, String appSecret) throws ApiException {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        AccessTokenClient request = (AccessTokenClient)retrofit.create(AccessTokenClient.class);
        GetTenantAccessTokenRequest accessTokenRequest = new GetTenantAccessTokenRequest(appId, appSecret);
        Call<TenantAccessTokenDTO> call = request.queryTenantAccessToken(accessTokenRequest);
        return (TenantAccessTokenDTO)RetrofitUtils.execute(call);
    }

    public Response<UserAccessTokenDTO> getUserAccessTokenInfoResponse(String appAccessToken, String code) throws ApiException {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        AccessTokenClient request = (AccessTokenClient)retrofit.create(AccessTokenClient.class);
        GetUserAccessTokenRequest userAccessTokenInfoRequest = new GetUserAccessTokenRequest("authorization_code", code);
        Call<Response<UserAccessTokenDTO>> call = request.queryUserAccessTokenInfo(RetrofitUtils.getAuthorizationToken(appAccessToken), userAccessTokenInfoRequest);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<UserAccessTokenDTO> refreshUserAccessTokenInfoResponse(String appAccessToken, String refreshToken) throws ApiException {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        AccessTokenClient request = (AccessTokenClient)retrofit.create(AccessTokenClient.class);
        RefreshUserAccessTokenRequest refreshUserAccessTokenInfoRequest = new RefreshUserAccessTokenRequest("refresh_token", refreshToken);
        Call<Response<UserAccessTokenDTO>> call = request.refreshUserAccessTokenInfo(RetrofitUtils.getAuthorizationToken(appAccessToken), refreshUserAccessTokenInfoRequest);
        return (Response)RetrofitUtils.execute(call);
    }
}
