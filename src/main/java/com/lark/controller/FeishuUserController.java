package com.lark.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.gson.Gson;
import com.lark.api.drive.DriveApi;
import com.lark.data.bean.Response;
import com.lark.data.constants.DocStatusEnZh;
import com.lark.data.constants.TenantType;
import com.lark.data.constants.UserStatusEnZh;
import com.lark.data.dto.drive.FolderChildDTO;
import com.lark.data.dto.drive.FolderChildrenDTO;
import com.lark.data.dto.drive.SecureLevel;
import com.lark.data.dto.drive.SecureLevelDto;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.data.feishu.FeishuRes;
import com.lark.data.feishu.RootMetaRes;
import com.lark.data.pojo.DocInfo;
import com.lark.data.pojo.Node;
import com.lark.data.pojo.UserInfo;
import com.lark.oapi.Client;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.service.contact.v3.model.GetUserReq;
import com.lark.oapi.service.contact.v3.model.GetUserResp;
import com.lark.oapi.service.contact.v3.model.GetUserRespBody;
import com.lark.oapi.service.contact.v3.model.User;
import com.lark.oapi.service.drive.v1.enums.DeleteFileTypeEnum;
import com.lark.oapi.service.drive.v1.model.BatchQueryMetaReq;
import com.lark.oapi.service.drive.v1.model.BatchQueryMetaResp;
import com.lark.oapi.service.drive.v1.model.BatchQueryMetaRespBody;
import com.lark.oapi.service.drive.v1.model.DeleteFileReq;
import com.lark.oapi.service.drive.v1.model.DeleteFileResp;
import com.lark.oapi.service.drive.v1.model.File;
import com.lark.oapi.service.drive.v1.model.GetPermissionPublicReq;
import com.lark.oapi.service.drive.v1.model.GetPermissionPublicResp;
import com.lark.oapi.service.drive.v1.model.GetPermissionPublicRespBody;
import com.lark.oapi.service.drive.v1.model.ListFileReq;
import com.lark.oapi.service.drive.v1.model.ListFileResp;
import com.lark.oapi.service.drive.v1.model.ListFileRespBody;
import com.lark.oapi.service.drive.v1.model.MetaRequest;
import com.lark.oapi.service.drive.v1.model.PatchPermissionPublicReq;
import com.lark.oapi.service.drive.v1.model.PatchPermissionPublicResp;
import com.lark.oapi.service.drive.v1.model.PermissionPublicRequest;
import com.lark.oapi.service.drive.v1.model.RequestDoc;
import com.lark.service.AccessTokenService;
import com.lark.service.DocInfoService;
import com.lark.service.DriveService;
import com.lark.service.MessageService;
import com.lark.service.NodeService;
import com.lark.service.UserInfoService;
import com.lark.service.repository.DocInfoRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class FeishuUserController {
    private static final Logger log = LoggerFactory.getLogger(FeishuUserController.class);
    private static final Logger logger = LoggerFactory.getLogger(FeishuUserController.class);
    @Value("${lark.new.name}")
    private String newTenantName;
    @Value("${lark.new.appId}")
    private String newTenantAppId;
    @Value("${lark.new.appSecret}")
    private String newTenantAppSecret;
    @Value("${lark.new.lowestLevelName}")
    private String newLowestLevelName;
    @Value("${lark.old.name}")
    private String oldTenantName;
    @Value("${lark.old.appId}")
    private String oldTenantAppId;
    @Value("${lark.old.appSecret}")
    private String oldTenantAppSecret;
    @Value("${lark.old.lowestLevelName}")
    private String oldLowestLevelName;
    @Value("${lark.url}")
    private String url;
    @Value("${lark.maxCountInQueue}")
    private String maxCountInQueue;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DriveService driveService;
    @Autowired
    private DocInfoService docInfoService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private DocInfoRepository docInfoRepository;
    @Autowired
    private DriveApi driveApi;
    @Autowired
    private MessageService messageService;

    public FeishuUserController() {
    }

    @GetMapping({"/feishu-trans-report"})
    public String newTenant(Model model) {
        return "feishu-trans-report";
    }

    @GetMapping({"/feishu-login"})
    public String feishuLogin(@RequestParam(required = false) String code, @RequestParam(required = false) String state, HttpSession session) {
        if (StringUtils.isBlank(code)) {
            return this.getRedirectUrl("/error/login_error.html");
        } else {
            logger.info("用户code：" + code);
            return !StringUtils.isBlank(state) && !"OLD".equalsIgnoreCase(state) ? this.createOrUpdateUser(code, session) : this.oldUserLogin(code, session);
        }
    }

    private String oldUserLogin(String code, HttpSession session) {
        logger.info("老用户登陆");
        UserAccessTokenDTO userAccessToken = this.accessTokenService.getUserAccessTokenByCode(TenantType.OLD, code);
        if (userAccessToken != null) {
            String openId = userAccessToken.getOpenId();
            session.setAttribute("old_user_name", userAccessToken.getName());
            session.setAttribute("old_user_open_id", userAccessToken.getOpenId());
            return this.getRedirectUrl("/feishu-new");
        } else {
            return this.getRedirectUrl("/error/login_error.html");
        }
    }

    @GetMapping({"/feishu-clean-new"})
    @ResponseBody
    public void cleanNew() {
        this.cleanNewFolder();
    }

    private void cleanNewFolder() {
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, "ou_7c803842d6581abde3463011ad8c5e63");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + userToken);
        HttpEntity<String> requestEntity = new HttpEntity(headers);
        String apiUrl = "https://open.feishu.cn/open-apis/drive/explorer/v2/root_folder/meta";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FeishuRes<RootMetaRes>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<FeishuRes<RootMetaRes>>() {
        }, new Object[0]);
        log.info("响应的结果为：{}", (new Gson()).toJson(response));
        RootMetaRes rootMetaRes = (RootMetaRes)((FeishuRes)response.getBody()).getData();
        String rootToken = rootMetaRes.getToken();
        HttpHeaders headersFolderList = new HttpHeaders();
        headersFolderList.setContentType(MediaType.APPLICATION_JSON);
        headersFolderList.set("Authorization", "Bearer " + userToken);
        ListFileReq requestBodyFolderList = new ListFileReq();
        requestBodyFolderList.setFolderToken(rootToken);
        HttpEntity<ListFileReq> requestEntityFolderList = new HttpEntity(requestBodyFolderList, headers);
        String apiUrlFolderList = "https://open.feishu.cn/open-apis/drive/v1/files";
        RestTemplate restTemplateFolderList = new RestTemplate();
        ResponseEntity<ListFileResp> responseFolderList = restTemplateFolderList.exchange(apiUrlFolderList, HttpMethod.GET, requestEntityFolderList, new ParameterizedTypeReference<ListFileResp>() {
        }, new Object[0]);
        log.info("响应的结果为：{}", (new Gson()).toJson(responseFolderList));
        ListFileRespBody listFileResp = (ListFileRespBody)((ListFileResp)responseFolderList.getBody()).getData();
        File[] files = listFileResp.getFiles();

        for(File file : files) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Client client = Client.newBuilder(this.newTenantAppId, this.newTenantAppSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(3L, TimeUnit.SECONDS).logReqAtDebug(true).build();
            DeleteFileReq req = new DeleteFileReq();
            req.setType(DeleteFileTypeEnum.FOLDER.getValue());
            req.setFileToken(file.getToken());
            RequestOptions reqOptions = RequestOptions.newBuilder().userAccessToken(userToken).headers(headers).build();
            DeleteFileResp delete = null;

            try {
                delete = client.drive().file().delete(req, reqOptions);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            log.info("Delete fileName:{}，删除的结果为：{}", file.getName(), (new Gson()).toJson(delete.getData()));
        }

    }

    private String createOrUpdateUser(String code, HttpSession session) {
        if (session.getAttribute("old_user_open_id") != null && session.getAttribute("old_user_name") != null) {
            UserAccessTokenDTO newUserAccessToken = this.accessTokenService.getUserAccessTokenByCode(TenantType.NEW, code);
            if (newUserAccessToken == null) {
                return this.getRedirectUrl("/error/login_error.html");
            } else {
                String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
                String newUserOpenId = newUserAccessToken.getOpenId();
                Client client = Client.newBuilder(this.oldTenantAppId, this.oldTenantAppSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(3L, TimeUnit.SECONDS).logReqAtDebug(true).build();
                GetUserReq req = GetUserReq.newBuilder().userId(oldUserOpenId).userIdType("open_id").departmentIdType("open_department_id").build();
                User oldUser = null;

                try {
                    GetUserResp resp = client.contact().user().get(req, RequestOptions.newBuilder().build());
                    oldUser = ((GetUserRespBody)resp.getData()).getUser();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String oldUserRootFolderToken = this.driveService.getRootFolderToken(TenantType.OLD, oldUserOpenId);
                String newUserRootFolderToken = this.driveService.getRootFolderToken(TenantType.NEW, newUserOpenId);
                if (StringUtils.isAllBlank(new CharSequence[]{oldUserRootFolderToken, newUserRootFolderToken})) {
                    return this.getRedirectUrl("/error/login_error.html");
                } else {
                    UserInfo userInfoData = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
                    session.setAttribute("new_user_open_id", newUserOpenId);
                    if (userInfoData == null) {
                        UserInfo userInfo = UserInfo.builder().oldUserOpenId(oldUserOpenId).oldUserName(oldUser.getName()).oldUserId(oldUser.getUserId()).oldUserEmail(oldUser.getEmail()).oldUserMobile(oldUser.getMobile()).oldUserToken(this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId)).oldUserRefreshToken(this.userInfoService.queryUserRefreshTokenByOpenId(TenantType.OLD, oldUserOpenId)).oldRootFolderToken(oldUserRootFolderToken).newUserName(newUserAccessToken.getName()).newUserId(newUserAccessToken.getUserId()).newUserEmail(newUserAccessToken.getEmail()).newUserMobile(newUserAccessToken.getMobile()).newUserOpenId(newUserOpenId).newUserToken(this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId)).newUserRefreshToken(this.userInfoService.queryUserRefreshTokenByOpenId(TenantType.NEW, newUserOpenId)).newRootFolderToken(newUserRootFolderToken).status(UserStatusEnZh.PENDING.getEnglish()).procStatus("未开始").procMsg("任务未开始，点击开始").build();
                        this.userInfoService.insertUserInfo(userInfo);
                    }

                    return this.getRedirectUrl("/feishu-begin-scan");
                }
            }
        } else {
            return this.getRedirectUrl("/error/login_error.html");
        }
    }

    @GetMapping({"/feishu-old-scan"})
    @ResponseBody
    public String feishuLogin(Model model, HttpSession session) {
        log.info("session: {}", (new Gson()).toJson(session));
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        if (!Objects.isNull(oldUserOpenId) && !Objects.isNull(newUserOpenId)) {
            UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
            String transferStatus = userInfo.getStatus();
            log.info("查询用户为：{}", (new Gson()).toJson(userInfo));
            if (!UserStatusEnZh.PENDING.getEnglish().equals(transferStatus)) {
                return this.getRedirectUrl("/feishu-begin-scan");
            } else {
                log.info("只扫描 PENDING 状态的！");
                QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper();
                ((QueryWrapper)((QueryWrapper)userQueryWrapper.eq("status", UserStatusEnZh.SCANNING.getEnglish())).or()).eq("status", UserStatusEnZh.TRANSFERRING.getEnglish());
                List<UserInfo> users = this.userInfoService.queryUsers(userQueryWrapper);
                String inQueueCount = "0";
                String canTrans = "TRUE";
                if (!ObjectUtils.isEmpty(users)) {
                    inQueueCount = String.valueOf(users.size());
                    if (this.maxCountInQueue.equals(inQueueCount)) {
                        canTrans = "FALSE";
                    }
                }

                model.addAttribute("inQueueCount", inQueueCount).addAttribute("canTrans", canTrans).addAttribute("transferStatus", transferStatus).addAttribute("transferStatusZh", UserStatusEnZh.getChineseByEnglish(transferStatus));
                this.getDocInfoFromLark(oldUserOpenId, newUserOpenId);
                return this.getRedirectUrl("/feishu-begin-scan");
            }
        } else {
            log.warn("会话中没有用户信息，需要重新登录。");
            return this.getRedirectUrl("/begin");
        }
    }

    public void getDocInfoFromLark(String oldUserOpenId, String newUserOpenId) {
        log.info("扫描开始。");
        long startTime = System.currentTimeMillis();
        int level = 0;
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        userInfo.setStatus(UserStatusEnZh.SCANNING.getEnglish());
        userInfo.setProcStatus("扫描");
        userInfo.setProcMsg("开始扫描迁出租户文档信息");
        this.userInfoService.updateUserInfo(userInfo);
        String rootFolderToken = userInfo.getOldRootFolderToken();
        LinkedHashMap<String, LinkedList<FolderChildDTO>> fileCollections = new LinkedHashMap();
        fileCollections.put(rootFolderToken, this.getFolderChildrenFromLark(oldUserOpenId, rootFolderToken));
        LinkedList<String> parentDirTokens = new LinkedList();
        parentDirTokens.add(rootFolderToken);
        List<DocInfo> docInfoList = new ArrayList();
        QueryWrapper<DocInfo> queryListWrapper = new QueryWrapper();
        ((QueryWrapper)queryListWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
        List<DocInfo> docInfoListFromDb = this.docInfoRepository.selectList(queryListWrapper);
        List<Node> nodeList = new ArrayList();

        while(!fileCollections.isEmpty()) {
            int size = fileCollections.size();
            int count = 0;
            String parentToken = (String)parentDirTokens.removeFirst();
            LinkedList<FolderChildDTO> folderChildren = (LinkedList)fileCollections.remove(parentToken);

            while(!folderChildren.isEmpty()) {
                FolderChildDTO folderChild = (FolderChildDTO)folderChildren.removeFirst();
                DocInfo docInfo = DocInfo.builder().oldUserOpenId(oldUserOpenId).oldToken(folderChild.getToken()).oldName(folderChild.getName()).oldType(folderChild.getType()).oldLevel(level).oldParentToken(parentToken).oldSecureLabel("").oldExternalAccess("").newUserOpenId(newUserOpenId).procMsg("获取数据正常").procStatus("获取到云文档").status("pending").build();
                Node treeNode = new Node(folderChild.getToken(), parentToken);
                nodeList.add(treeNode);
                Client client = Client.newBuilder(this.oldTenantAppId, this.oldTenantAppSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(30L, TimeUnit.SECONDS).logReqAtDebug(true).build();
                String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
                RequestOptions reqOptions = RequestOptions.newBuilder().userAccessToken(userToken).build();
                GetPermissionPublicReq req = GetPermissionPublicReq.newBuilder().token(folderChild.getToken()).type(folderChild.getType()).build();
                AtomicReference<GetPermissionPublicResp> resp = new AtomicReference();
                RequestDoc[] requestDocs = new RequestDoc[1];
                RequestDoc requestDoc = new RequestDoc();
                requestDoc.setDocToken(folderChild.getToken());
                requestDoc.setDocType(folderChild.getType());
                requestDocs[0] = requestDoc;
                BatchQueryMetaReq reqBatchQueryMetaReq = BatchQueryMetaReq.newBuilder().metaRequest(MetaRequest.newBuilder().requestDocs(requestDocs).withUrl(false).build()).build();
                BatchQueryMetaResp batchQueryMetaResp = null;

                try {
                    Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder().retryIfException()
                            .retryIfResult((aBoolean) -> Objects.equals(aBoolean, false))
                            .withWaitStrategy(WaitStrategies.incrementingWait(30L, TimeUnit.SECONDS, 60L, TimeUnit.SECONDS)).withStopStrategy(StopStrategies.stopAfterAttempt(3)).build();
                    if (!"folder".equals(folderChild.getType())) {
                        log.info("开始执行方法：获取文档公开属性。文件Token：{}", folderChild.getToken());
                        retryer.call(() -> {
                            log.info("retryer 执行具体方法，当前时间戳：{}，文件Token：{}", System.currentTimeMillis(), folderChild.getToken());
                            GetPermissionPublicResp innerResp = client.drive().permissionPublic().get(req, reqOptions);
                            resp.set(innerResp);
                            return ((GetPermissionPublicResp)resp.get()).success();
                        });
                        log.info("获取文档公开属性结果为：{}，{}", folderChild.getToken(), (new Gson()).toJson(resp.get()));
                        if (((GetPermissionPublicResp)resp.get()).success()) {
                            GetPermissionPublicRespBody data = (GetPermissionPublicRespBody)((GetPermissionPublicResp)resp.get()).getData();
                            docInfo.setOldExternalAccess(data.getPermissionPublic().getExternalAccess() ? "External" : "Internal");
                        }
                    }

                    batchQueryMetaResp = client.drive().meta().batchQuery(reqBatchQueryMetaReq, reqOptions);
                    log.info("获取文档密级：{}，{}", folderChild.getToken(), (new Gson()).toJson(batchQueryMetaResp));
                    if (batchQueryMetaResp.success()) {
                        BatchQueryMetaRespBody dataMetaResp = (BatchQueryMetaRespBody)batchQueryMetaResp.getData();
                        if (ObjectUtils.isEmpty(dataMetaResp.getMetas())) {
                            log.error("类型：{}, 文档：{}，无法获得密级信息，不予处理！原因：{}", new Object[]{folderChild.getType(), folderChild.getToken(), (new Gson()).toJson(dataMetaResp)});
                            nodeList.remove(treeNode);
                            continue;
                        }

                        docInfo.setOldSecureLabel(dataMetaResp.getMetas()[0].getSecLabelName());
                    }

                    if ("folder".equals(folderChild.getType())) {
                        docInfo.setProcStatus("获取到文件夹");
                        docInfo.setProcMsg("获取文件正常");

                        try {
                            String oldFolderToken = docInfo.getOldToken();
                            LinkedList<FolderChildDTO> temp = this.getFolderChildrenFromLark(oldUserOpenId, oldFolderToken);
                            fileCollections.put(oldFolderToken, temp);
                            parentDirTokens.add(oldFolderToken);
                        } catch (Exception var33) {
                            docInfo.setProcMsg("该文件夹下文件有获取失败情况");
                        }
                    } else if ("file".equals(folderChild.getType())) {
                        log.info("类型为：file。");
                    }

                    DocInfo docInfoData = this.getDocInfo(folderChild.getToken(), docInfoListFromDb);
                    if (docInfoData == null) {
                        docInfoList.add(docInfo);
                    } else if (!"success".equalsIgnoreCase(docInfoData.getStatus()) && (!docInfoData.getOldName().equals(docInfo.getOldName()) || !docInfoData.getOldParentToken().equals(docInfo.getOldParentToken()) || !docInfoData.getOldLevel().equals(docInfo.getOldLevel()) || !docInfoData.getOldSecureLabel().equals(docInfo.getOldSecureLabel()) || !docInfoData.getOldExternalAccess().equals(docInfo.getOldExternalAccess()))) {
                        docInfoData.setOldLevel(level);
                        docInfoData.setOldParentToken(docInfo.getOldParentToken());
                        this.docInfoRepository.updateById(docInfoData);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }

            ++count;
            if (count == size) {
                ++level;
            }
        }

        Map<String, Node> nodeMap = new HashMap();

        for(Node node : nodeList) {
            nodeMap.put(node.getToken(), node);
        }

        for(DocInfo docInfo : docInfoList) {
            Node tokenNode = (Node)nodeList.stream().filter((node) -> Objects.equals(node.getToken(), docInfo.getOldToken()) && Objects.equals(node.getParentToken(), docInfo.getOldParentToken())).findFirst().orElse(null);
            docInfo.setOldLevel(this.nodeService.getLevel(rootFolderToken, tokenNode, nodeMap));
        }

        long startMillis = System.currentTimeMillis();
        log.info("批量插入开始。");
        this.docInfoService.batchSaveDocs(docInfoList);
        log.info("批量插入耗时（毫秒）:{}", System.currentTimeMillis() - startMillis);
        userInfo.setProcStatus("扫描完成");
        userInfo.setProcMsg("扫描完成");
        userInfo.setStatus(UserStatusEnZh.SCANNED.getEnglish());
        this.userInfoService.updateUserInfo(userInfo);
        log.info("扫描共计耗时（毫秒）:{}", System.currentTimeMillis() - startTime);
    }

    private DocInfo getDocInfo(String token, List<DocInfo> docInfoListFromDb) {
        return (DocInfo)docInfoListFromDb.stream().filter((doc) -> Objects.equals(doc.getOldToken(), token)).findFirst().orElse(null);
    }

    private LinkedList<FolderChildDTO> getFolderChildrenFromLark(String oldUserOpenId, String folderToken) {
        String userAccessToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        Response<FolderChildrenDTO> response = this.driveApi.queryFolderChildrenInfo(userAccessToken, folderToken);
        FolderChildrenDTO folderChildrenInfo = null;
        LinkedList<FolderChildDTO> folderChildrenList = new LinkedList();
        if (response != null && (folderChildrenInfo = (FolderChildrenDTO)response.getData()) != null) {
            Map<String, FolderChildDTO> childrenMap = folderChildrenInfo.getChildren();
            if (childrenMap != null) {
                for(Map.Entry<String, FolderChildDTO> entry : childrenMap.entrySet()) {
                    if (ObjectUtils.isEmpty(((FolderChildDTO)entry.getValue()).getShortcut_token())) {
                        folderChildrenList.add(entry.getValue());
                    } else {
                        log.warn("快捷方式不予处理：{}", (new Gson()).toJson(entry.getValue()));
                    }
                }
            }
        }

        return folderChildrenList;
    }

    @GetMapping({"/feishu-batch"})
    @ResponseBody
    public void batchTest() {
        List<DocInfo> docs = new ArrayList();

        for(int i = 0; i < 1000; ++i) {
            DocInfo docInfo = DocInfo.builder().oldUserOpenId(Integer.toString(i)).oldToken(Integer.toString(i)).oldName(Integer.toString(i)).oldType(Integer.toString(i)).oldLevel(i).oldParentToken(Integer.toString(i)).newUserOpenId(Integer.toString(i)).procMsg("获取数据正常").procStatus("获取到云文档").status("pending").build();
            docs.add(docInfo);
        }

        long startMillis = System.currentTimeMillis();
        log.info("批量插入开始。");
        this.docInfoService.batchSaveDocs(docs);
        log.info("批量插入耗时（毫秒）:{}", System.currentTimeMillis() - startMillis);
    }

    public String getRedirectUrl(String path) {
        return "redirect:" + this.url + path;
    }

    @GetMapping({"/feishu-trans"})
    @ResponseBody
    public void feishuTrans(HttpSession session) {
        log.info("session: {}", (new Gson()).toJson(session));
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId);
        List<SecureLevel> secureLevelList = this.driveService.getSecureLevelList(userToken);
        if (Objects.isNull(secureLevelList) && !"NA".equalsIgnoreCase(this.oldLowestLevelName)) {
            log.error("无法获得迁出租户的密级列表信息，无密级信息，程序无法实现密级调整与分享。无法执行迁移！");
            log.error("迁移工具中配置的 oldLowestLevelName 为：{}", this.oldLowestLevelName);
            this.messageService.sendMessage(TenantType.OLD, "无法执行迁移！原因：迁移工具配置了密级标签，但系统无法获取！请联系管理员处理！！！", oldUserOpenId);
        } else {
            UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
            String userStatus = userInfo.getStatus();
            if (!UserStatusEnZh.SCANNED.getEnglish().equals(userStatus) && !UserStatusEnZh.TRANSFERRED.getEnglish().equals(userStatus)) {
                log.warn("userStatus：{} 不能执行迁移！", UserStatusEnZh.getChineseByEnglish(userStatus));
            } else {
                userInfo.setStatus(UserStatusEnZh.TRANSFERRING.getEnglish());
                this.userInfoService.updateUserInfo(userInfo);
                boolean transFailureDocs = false;
                if (UserStatusEnZh.TRANSFERRED.getEnglish().equals(userStatus)) {
                    transFailureDocs = true;
                }

                this.docInfoService.transferDocsInfoToNewUser(oldUserOpenId, newUserOpenId, transFailureDocs);
                String userStatusAfter = UserStatusEnZh.TRANSFERRED.getEnglish();
                QueryWrapper<DocInfo> queryWrapper = new QueryWrapper();
                ((QueryWrapper)queryWrapper.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId);
                int totalCount = this.docInfoService.docCount(queryWrapper);
                QueryWrapper<DocInfo> queryWrapperSuccess = new QueryWrapper();
                ((QueryWrapper)((QueryWrapper)queryWrapperSuccess.eq("old_user_open_id", oldUserOpenId)).eq("new_user_open_id", newUserOpenId)).eq("status", DocStatusEnZh.SUCCESS.getEnglish());
                int totalSuccess = this.docInfoService.docCount(queryWrapperSuccess);
                String pStatus = DocStatusEnZh.SUCCESS.getChinese();
                String pMsg = DocStatusEnZh.SUCCESS.getChinese();
                if (totalCount == totalSuccess) {
                    userStatusAfter = UserStatusEnZh.SUCCESS.getEnglish();
                } else {
                    pStatus = DocStatusEnZh.FAILURE.getChinese();
                    pMsg = UserStatusEnZh.TRANSFERRED.getChinese();
                }

                userInfo.setStatus(userStatusAfter);
                userInfo.setProcStatus(pStatus);
                userInfo.setProcMsg(pMsg);
                this.userInfoService.updateUserInfo(userInfo);
                this.messageService.sendTransNotification(oldUserOpenId, newUserOpenId);
            }

        }
    }

    @GetMapping({"/feishu-notification"})
    @ResponseBody
    public void feishuTransNotification(HttpSession session) {
        log.info("session: {}", (new Gson()).toJson(session));
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        this.messageService.sendTransNotification(oldUserOpenId, newUserOpenId);
    }

    @PutMapping({"/feishu-secure"})
    @ResponseBody
    public void feishuPutSecureLevel() {
        this.driveApi.updateDocSecureLevel("u-dpFUYIQXZ2coaMmvtWLMRtklm8uB54hxOU00lggyyw_E", "NYhQdW6tso1gyzxX1GrcryGVnRb", "docx", "7129765546839326722");
    }

    @GetMapping({"/feishu-secure"})
    @ResponseBody
    public void feishuGetSecureLevel() {
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, "ou_7bc3adf6353b355900eaeeb98a82ef0e");
        SecureLevel lowestLevel = this.driveService.getLowestLevel(userToken, this.oldLowestLevelName);
        if (lowestLevel != null) {
            Response<Object> response2 = this.driveApi.updateDocSecureLevel(userToken, "NYhQdW6tso1gyzxX1GrcryGVnRb", "docx", lowestLevel.getId());
            log.info("{}", (new Gson()).toJson(response2));
        }

    }

    @GetMapping({"/feishu-secure-labels"})
    @ResponseBody
    public String feishuGetSecureLevels(HttpSession session) {
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, "ou_7bc3adf6353b355900eaeeb98a82ef0e");
        List<SecureLevel> secureLevelList = this.driveService.getSecureLevelList(userToken);
        if (ObjectUtils.isEmpty(secureLevelList)) {
            log.error("无法获得迁出租户的密级列表信息，无密级信息，程序无法实现密级调整与分享。无法执行迁移！");
            return "canNotTrans";
        } else {
            return "canTrans";
        }
    }

    private SecureLevel getLowestLevel(String userToken, String lowestLevelName) {
        SecureLevel lowestLevel = null;

        try {
            Response<SecureLevelDto> response = this.driveApi.getSecureLevels(userToken);
            SecureLevelDto secureLevelDto = (SecureLevelDto)response.getData();
            List<SecureLevel> items = secureLevelDto.getItems();
            lowestLevel = (SecureLevel)items.stream().filter((level) -> level.getName().equals(lowestLevelName)).findFirst().orElse(null);
            log.info("{}, {}", (new Gson()).toJson(lowestLevel), (new Gson()).toJson(secureLevelDto));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取时间异常：{}", e.getMessage());
        }

        return lowestLevel;
    }

    @GetMapping({"/feishu-access"})
    @ResponseBody
    public void feishuAccess() {
        String userToken = this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, "ou_7bc3adf6353b355900eaeeb98a82ef0e");
        RequestOptions reqOptions = RequestOptions.newBuilder().userAccessToken(userToken).build();
        Client client = Client.newBuilder(this.oldTenantAppId, this.oldTenantAppSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(3L, TimeUnit.SECONDS).logReqAtDebug(true).build();
        PatchPermissionPublicReq req = PatchPermissionPublicReq.newBuilder().token("NYhQdW6tso1gyzxX1GrcryGVnRb").type("docx").permissionPublicRequest(PermissionPublicRequest.newBuilder().externalAccess(false).build()).build();

        try {
            PatchPermissionPublicResp resp = client.drive().permissionPublic().patch(req, reqOptions);
            if (!resp.success()) {
                System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/feishu-session-check"})
    @ResponseBody
    public String feishuSessionCheck(Model model, HttpSession session) {
        log.info("session: {}", (new Gson()).toJson(session));
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        return !ObjectUtils.isEmpty(oldUserOpenId) && !ObjectUtils.isEmpty(newUserOpenId) ? "login" : "notLogin";
    }
}
