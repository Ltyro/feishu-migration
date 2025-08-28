package com.lark.api.message;

import com.lark.data.bean.Response;
import com.lark.data.constants.DefaultConstants;
import com.lark.data.dto.message.MessageContentDTO;
import com.lark.data.dto.message.SendMessageRequest;
import com.lark.utils.JacksonUtils;
import com.lark.utils.RetrofitUtils;
import java.io.IOException;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class MessageApi {
    public MessageApi() {
    }

    public Response<Object> sendMessage(String tenantAccessToken, SendMessageRequest sendMessageRequest) throws IOException {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        MessageClient request = (MessageClient)retrofit.create(MessageClient.class);
        MessageContentDTO content = MessageContentDTO.builder().text(sendMessageRequest.getContent()).build();
        sendMessageRequest.setContent(JacksonUtils.obj2String(content));
        Call<Response<Object>> call = request.sendMessage(RetrofitUtils.getAuthorizationToken(tenantAccessToken), DefaultConstants.userIdType.value(), sendMessageRequest);
        return (Response)RetrofitUtils.execute(call);
    }
}
