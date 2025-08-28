package com.lark.api.sheet;

import com.lark.data.bean.Response;
import com.lark.data.dto.sheet.CreateSheetResponseDTO;
import com.lark.data.dto.sheet.SheetMetaInfoDTO;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SheetClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("sheets/v3/spreadsheets")
    Call<Response<Map<String, CreateSheetResponseDTO>>> create(@Header("Authorization") String authorization, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("sheets/v2/spreadsheets/{spreadSheetToken}/metainfo")
    Call<Response<SheetMetaInfoDTO>> getMetaInfo(@Header("Authorization") String authorization, @Path("spreadSheetToken") String spreadSheetToken);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("sheets/v2/spreadsheets/{spreadSheetToken}/dimension_range")
    Call<Response<Map<String, Object>>> addDimensionRange(@Header("Authorization") String authorization, @Path("spreadSheetToken") String spreadSheetToken, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @HTTP(
            method = "DELETE",
            path = "sheets/v2/spreadsheets/{spreadSheetToken}/dimension_range",
            hasBody = true
    )
    Call<Response<Map<String, Object>>> deleteDimensionRange(@Header("Authorization") String authorization, @Path("spreadSheetToken") String spreadSheetToken, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("sheets/v2/spreadsheets/{spreadSheetToken}/values_prepend")
    Call<Response<Map<String, Object>>> appendRows(@Header("Authorization") String authorization, @Path("spreadSheetToken") String spreadSheetToken, @Body Map<String, Object> body);
}
