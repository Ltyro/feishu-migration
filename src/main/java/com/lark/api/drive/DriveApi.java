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
import com.lark.utils.MapUtil;
import com.lark.utils.RetrofitUtils;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

@Component
public class DriveApi {
    public DriveApi() {
    }

    public Response<FolderMetaInfoDTO> queryRootFolderMetaInfo(String userAccessToken) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Call<Response<FolderMetaInfoDTO>> call = request.queryRootFolderMetaInfo(RetrofitUtils.getAuthorizationToken(userAccessToken));
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<QueryDocMemberResponseDTO> queryMemberList(String userAccessToken, String token, String type) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> body = MapUtil.instance().put("token", token).put("type", type).build();
        Call<Response<QueryDocMemberResponseDTO>> call = request.queryMemberList(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<FolderChildrenDTO> queryFolderChildrenInfo(String userAccessToken, String folderToken) {
        return this.queryFolderChildrenInfo(userAccessToken, folderToken, (List)null);
    }

    public Response<FolderChildrenDTO> queryFolderChildrenInfo(String userAccessToken, String folderToken, List<String> types) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Call<Response<FolderChildrenDTO>> call = request.queryFolderChildrenInfo(RetrofitUtils.getAuthorizationToken(userAccessToken), folderToken, types);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<CreateFolderResponseDTO> createFolder(String userAccessToken, String folderToken, String title) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> body = MapUtil.instance().put("title", title).build();
        Call<Response<CreateFolderResponseDTO>> call = request.createFolder(RetrofitUtils.getAuthorizationToken(userAccessToken), folderToken, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Object> addPermission(String userAccessToken, String fileToken, String type, String openId) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> queryMap = MapUtil.instance().put("type", type).put("need_notification", false).build();
        Map<String, Object> body = MapUtil.instance().put("member_type", "openid").put("member_id", openId).put("perm", "full_access").build();
        Call<Response<Object>> call = request.addPermission(RetrofitUtils.getAuthorizationToken(userAccessToken), fileToken, queryMap, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Object> removePermission(String userAccessToken, String fileToken, String type, String openId) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> queryMap = MapUtil.instance().put("type", type).put("member_type", "openid").build();
        Call<Response<Object>> call = request.removePermission(RetrofitUtils.getAuthorizationToken(userAccessToken), fileToken, openId, queryMap);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<CopyDocResponseDTO> copyDoc(String userAccessToken, String fileToken, String type, String dstFolderToken, String dstName) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> body = MapUtil.instance().put("type", type).put("dstFolderToken", dstFolderToken).put("dstName", dstName).put("commentNeedEd", false).build();
        Call<Response<CopyDocResponseDTO>> call = request.copyDoc(RetrofitUtils.getAuthorizationToken(userAccessToken), fileToken, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<PreUploadResponseDTO> preUpload(String userAccessToken, String fileName, String parentFileToken, long size) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> body = MapUtil.instance().put("file_name", fileName).put("parent_type", "explorer").put("parent_node", parentFileToken).put("size", size).build();
        Call<Response<PreUploadResponseDTO>> call = request.preUpload(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<Object> partUpload(String userAccessToken, String uploadId, int seq, int size, long checkSum, byte[] partFile) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("upload_id", uploadId).addFormDataPart("seq", String.valueOf(seq)).addFormDataPart("checksum", String.valueOf(checkSum)).addFormDataPart("size", String.valueOf(size));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), partFile);
        builder.addFormDataPart("file", (String)null, requestBody);
        MultipartBody body = builder.build();
        Call<Response<Object>> call = request.partUpload(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<FinishUploadResponseDTO> finishUpload(String userAccessToken, String uploadId, int blockNum) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> body = MapUtil.instance().put("upload_id", uploadId).put("block_num", blockNum).build();
        Call<Response<FinishUploadResponseDTO>> call = request.finishUpload(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<FinishUploadResponseDTO> upload(String userAccessToken, String fileName, String parentFileToken, long size, long checksum, byte[] file) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file_name", fileName).addFormDataPart("parent_type", "explorer").addFormDataPart("size", String.valueOf(size)).addFormDataPart("parent_node", parentFileToken).addFormDataPart("checksum", String.valueOf(checksum)).addFormDataPart("size", String.valueOf(size));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        builder.addFormDataPart("file", (String)null, requestBody);
        MultipartBody body = builder.build();
        Call<Response<FinishUploadResponseDTO>> call = request.upload(RetrofitUtils.getAuthorizationToken(userAccessToken), body);
        return (Response)RetrofitUtils.execute(call);
    }

    public ResponseBody download(String userAccessToken, String fileToken, long start, long end) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        String range = "bytes=" + start + "-" + end;
        Call<ResponseBody> call = request.download(RetrofitUtils.getAuthorizationToken(userAccessToken), range, fileToken);
        return (ResponseBody)RetrofitUtils.execute(call);
    }

    public Response<Object> updateDocSecureLevel(String userAccessToken, String fileToken, String fileType, String secureLabelId) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Map<String, Object> queryMap = MapUtil.instance().put("type", fileType).build();
        Map<String, Object> body = MapUtil.instance().put("id", secureLabelId).build();
        Call<Response<Object>> call = request.updateDocSecureLevel(RetrofitUtils.getAuthorizationToken(userAccessToken), fileToken, queryMap, body);
        return (Response)RetrofitUtils.execute(call);
    }

    public Response<SecureLevelDto> getSecureLevels(String userAccessToken) {
        Retrofit retrofit = RetrofitUtils.createRetrofit();
        DriveClient request = (DriveClient)retrofit.create(DriveClient.class);
        Call<Response<SecureLevelDto>> call = request.getSecureLevels(RetrofitUtils.getAuthorizationToken(userAccessToken));
        return (Response)RetrofitUtils.execute(call);
    }
}
