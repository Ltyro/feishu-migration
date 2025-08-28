package com.lark.api.drive;

import com.lark.data.bean.Response;
import com.lark.data.dto.drive.CopyDocResponseDTO;
import com.lark.data.dto.drive.CreateFolderResponseDTO;
import com.lark.data.dto.drive.FinishUploadResponseDTO;
import com.lark.data.dto.drive.FolderChildrenDTO;
import com.lark.data.dto.drive.FolderMetaInfoDTO;
import com.lark.data.dto.drive.PreUploadResponseDTO;
import com.lark.data.dto.drive.QueryDocMemberResponseDTO;
import com.lark.data.dto.drive.SecureLevelDto;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DriveClient {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("drive/explorer/v2/root_folder/meta")
    Call<Response<FolderMetaInfoDTO>> queryRootFolderMetaInfo(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/permission/member/list")
    Call<Response<QueryDocMemberResponseDTO>> queryMemberList(@Header("Authorization") String authorization, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("drive/explorer/v2/folder/{folderToken}/children")
    Call<Response<FolderChildrenDTO>> queryFolderChildrenInfo(@Header("Authorization") String authorization, @Path("folderToken") String folderToken, @Query("types") List<String> types);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/explorer/v2/folder/{folderToken}")
    Call<Response<CreateFolderResponseDTO>> createFolder(@Header("Authorization") String authorization, @Path("folderToken") String folderToken, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/v1/permissions/{token}/members")
    Call<Response<Object>> addPermission(@Header("Authorization") String authorization, @Path("token") String token, @QueryMap Map<String, Object> queryMap, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @DELETE("drive/v1/permissions/{token}/members/{member_id}")
    Call<Response<Object>> removePermission(@Header("Authorization") String authorization, @Path("token") String token, @Path("member_id") String memberId, @QueryMap Map<String, Object> queryMap);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/explorer/v2/file/copy/files/{fileToken}")
    Call<Response<CopyDocResponseDTO>> copyDoc(@Header("Authorization") String authorization, @Path("fileToken") String token, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/v1/files/upload_prepare")
    Call<Response<PreUploadResponseDTO>> preUpload(@Header("Authorization") String authorization, @Body Map<String, Object> body);

    @POST("drive/v1/files/upload_part")
    Call<Response<Object>> partUpload(@Header("Authorization") String authorization, @Body MultipartBody multipartBody);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("drive/v1/files/upload_finish")
    Call<Response<FinishUploadResponseDTO>> finishUpload(@Header("Authorization") String authorization, @Body Map<String, Object> body);

    @POST("drive/v1/files/upload_all")
    Call<Response<FinishUploadResponseDTO>> upload(@Header("Authorization") String authorization, @Body MultipartBody multipartBody);

    @GET("drive/v1/files/{file_token}/download")
    Call<ResponseBody> download(@Header("Authorization") String authorization, @Header("Range") String range, @Path("file_token") String fileToken);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @PUT("drive/v2/files/{fileToken}/secure_label")
    Call<Response<Object>> updateDocSecureLevel(@Header("Authorization") String authorization, @Path("fileToken") String fileToken, @QueryMap Map<String, Object> queryMap, @Body Map<String, Object> body);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET("drive/v2/my_secure_labels")
    Call<Response<SecureLevelDto>> getSecureLevels(@Header("Authorization") String authorization);
}
