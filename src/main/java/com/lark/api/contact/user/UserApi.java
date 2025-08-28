package com.lark.api.contact.user;

import com.lark.data.bean.Response;
import com.lark.data.constants.DefaultConstants;
import com.lark.data.constants.UserIdTypeEnum;
import com.lark.data.dto.contact.UserDTO;
import com.lark.exception.ApiException;
import com.lark.exception.CustomException;
import com.lark.utils.MapUtil;
import com.lark.utils.RetrofitUtils;
import java.util.Map;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class UserApi {
    public UserApi() {
    }

    public Response<Map<String, UserDTO>> queryUserById(String tenantAccessToken, String userId) throws ApiException, CustomException {
        return this.queryUser(tenantAccessToken, DefaultConstants.userIdType, userId);
    }

    public Response<Map<String, UserDTO>> queryUser(String tenantAccessToken, UserIdTypeEnum userIdType, String userId) throws ApiException, CustomException {
        Map<String, Object> queryMap = MapUtil.instance().put("user_id_type", userIdType.value()).put("department_id_type", DefaultConstants.departmentIdType.value()).build();
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        UserClient request = (UserClient)retrofit.create(UserClient.class);
        Call<Response<Map<String, UserDTO>>> call = request.queryUser(RetrofitUtils.getAuthorizationToken(tenantAccessToken), userId, queryMap);
        return (Response)RetrofitUtils.execute(call);
    }
}
