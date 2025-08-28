package com.lark.api.doc;

import com.lark.data.bean.Response;
import com.lark.data.dto.doc.DocDTO;
import com.lark.utils.MapUtil;
import com.lark.utils.RetrofitUtils;
import java.util.Map;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class DocApi {
    public DocApi() {
    }

    public Response<DocDTO> createDoc(String userAccessToken, String folderToken, String content) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DocClient request = (DocClient)retrofit.create(DocClient.class);
        Map<String, Object> body = MapUtil.instance().put("FolderToken", folderToken).put("Content", content).build();
        Call<Response<DocDTO>> call = request.create(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }
}
