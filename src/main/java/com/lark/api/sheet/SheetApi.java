package com.lark.api.sheet;

import com.lark.data.bean.Response;
import com.lark.data.dto.sheet.CreateSheetResponseDTO;
import com.lark.data.dto.sheet.SheetMetaInfoDTO;
import com.lark.utils.MapUtil;
import com.lark.utils.RetrofitUtils;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class SheetApi {
    public SheetApi() {
    }

    public Response<Map<String, CreateSheetResponseDTO>> create(String userAccessToken, String folderToken, String title) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        SheetClient request = (SheetClient)retrofit.create(SheetClient.class);
        Map<String, Object> body = MapUtil.instance().put("folder_token", folderToken).put("title", title).build();
        Call<Response<Map<String, CreateSheetResponseDTO>>> call = request.create(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<SheetMetaInfoDTO> getMetaInfo(String userAccessToken, String token) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        SheetClient request = (SheetClient)retrofit.create(SheetClient.class);
        Call<Response<SheetMetaInfoDTO>> call = request.getMetaInfo(RetrofitUtils.getAuthorizationToken(userAccessToken), token);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Map<String, Object>> addDimensionRange(String userAccessToken, String token, String sheetId, int length) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        SheetClient request = (SheetClient)retrofit.create(SheetClient.class);
        Map<String, Object> dimension = MapUtil.instance().put("sheetId", sheetId).put("majorDimension", "ROWS").put("length", length).build();
        Map<String, Object> body = MapUtil.instance().put("dimension", dimension).build();
        Call<Response<Map<String, Object>>> call = request.addDimensionRange(RetrofitUtils.getAuthorizationToken(userAccessToken), token, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Map<String, Object>> deleteDimensionRange(String userAccessToken, String token, String sheetId, int startIndex, int endIndex) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        SheetClient request = (SheetClient)retrofit.create(SheetClient.class);
        Map<String, Object> dimension = MapUtil.instance().put("sheetId", sheetId).put("majorDimension", "ROWS").put("startIndex", startIndex).put("endIndex", endIndex).build();
        Map<String, Object> body = MapUtil.instance().put("dimension", dimension).build();
        Call<Response<Map<String, Object>>> call = request.deleteDimensionRange(RetrofitUtils.getAuthorizationToken(userAccessToken), token, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Map<String, Object>> appendRows(String userAccessToken, String token, String range, List<List<String>> values) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        SheetClient request = (SheetClient)retrofit.create(SheetClient.class);
        Map<String, Object> valueRange = MapUtil.instance().put("range", range).put("values", values).build();
        Map<String, Object> body = MapUtil.instance().put("valueRange", valueRange).build();
        Call<Response<Map<String, Object>>> call = request.appendRows(RetrofitUtils.getAuthorizationToken(userAccessToken), token, body);
        return (Response)RetrofitUtils.execute(call);
    }
}
