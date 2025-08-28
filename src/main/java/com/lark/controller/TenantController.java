package com.lark.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lark.data.constants.UserStatusEnZh;
import com.lark.data.pojo.UserInfo;
import com.lark.oapi.Client;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.service.tenant.v2.model.QueryTenantResp;
import com.lark.oapi.service.tenant.v2.model.QueryTenantRespBody;
import com.lark.oapi.service.tenant.v2.model.Tenant;
import com.lark.service.UserInfoService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TenantController {
    private static final Logger log = LoggerFactory.getLogger(TenantController.class);
    @Value("${lark.new.name}")
    private String newTenantName;
    @Value("${lark.new.appId}")
    private String newTenantAppId;
    @Value("${lark.new.appSecret}")
    private String newTenantAppSecret;
    @Value("${lark.old.name}")
    private String oldTenantName;
    @Value("${lark.old.appId}")
    private String oldTenantAppId;
    @Value("${lark.old.appSecret}")
    private String oldTenantAppSecret;
    @Value("${lark.url}")
    private String url;
    @Value("${lark.old.qrAddress}")
    private String oldQrAddress;
    @Value("${lark.new.qrAddress}")
    private String newQrAddress;
    @Value("${lark.maxCountInQueue}")
    private String maxCountInQueue;
    @Autowired
    private UserInfoService userInfoService;

    public TenantController() {
    }

    @GetMapping({"/new"})
    public String newTenant(Model model) {
        model.addAttribute("title", this.newTenantName + "授权").addAttribute("name", this.newTenantName).addAttribute("appId", this.newTenantAppId).addAttribute("url", this.url);
        return "new";
    }

    @GetMapping({"/old"})
    public String oldTenant(Model model) {
        model.addAttribute("title", this.oldTenantName + "授权").addAttribute("name", this.oldTenantName).addAttribute("appId", this.oldTenantAppId).addAttribute("url", this.url);
        return "old";
    }

    @GetMapping({"/begin"})
    public String begin(Model model, HttpSession session) {
        String newTenantName = this.getTenantName(this.newTenantAppId, this.newTenantAppSecret);
        String oldTenantName = this.getTenantName(this.oldTenantAppId, this.oldTenantAppSecret);
        model.addAttribute("oldName", oldTenantName).addAttribute("oldAppId", this.oldTenantAppId).addAttribute("newName", newTenantName).addAttribute("newAppId", this.newTenantAppId);
        return "begin";
    }

    @GetMapping({"/authorization"})
    public String authorization(Model model) {
        model.addAttribute("oldName", this.oldTenantName).addAttribute("newName", this.newTenantName);
        return "authorization";
    }

    @GetMapping({"/begin_transfer"})
    public String beginTransfer(Model model) {
        model.addAttribute("oldName", this.oldTenantName).addAttribute("newName", this.newTenantName);
        return "begin_transfer";
    }

    @GetMapping({"/feishu-begin-scan"})
    public String feishuBeginTransfer(Model model, HttpSession session) {
        model.addAttribute("oldName", this.oldTenantName).addAttribute("newName", this.newTenantName);
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String transferStatus = userInfo.getStatus();
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper();
        ((QueryWrapper)((QueryWrapper)userQueryWrapper.eq("status", UserStatusEnZh.SCANNING.getEnglish())).or()).eq("status", UserStatusEnZh.TRANSFERRING.getEnglish());
        List<UserInfo> users = this.userInfoService.queryUsers(userQueryWrapper);
        int inQueueCount = 0;
        String canTrans = "TRUE";
        if (!ObjectUtils.isEmpty(users)) {
            inQueueCount = users.size();
            int maxCountInQueueInt = Integer.parseInt(this.maxCountInQueue);
            if (maxCountInQueueInt > 10) {
                log.warn("maxCountInQueue 值为：{}, 操作最大值 10，按照10处理。", this.maxCountInQueue);
                maxCountInQueueInt = 10;
            }

            if (maxCountInQueueInt < inQueueCount) {
                canTrans = "FALSE";
            }
        }

        model.addAttribute("inQueueCount", inQueueCount).addAttribute("canTrans", canTrans).addAttribute("transferStatus", transferStatus).addAttribute("transferStatusZh", UserStatusEnZh.getChineseByEnglish(transferStatus));
        return "feishu-begin-scan";
    }

    @GetMapping({"/feishu_begin_scan_refresh"})
    public String feishuBeginTransferRefresh(Model model) {
        model.addAttribute("oldName", this.oldTenantName).addAttribute("newName", this.newTenantName);
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper();
        ((QueryWrapper)((QueryWrapper)userQueryWrapper.eq("status", UserStatusEnZh.SCANNING.getEnglish())).or()).eq("status", UserStatusEnZh.TRANSFERRING.getEnglish());
        List<UserInfo> users = this.userInfoService.queryUsers(userQueryWrapper);
        int inQueueCount = 0;
        String canTrans = "TRUE";
        if (!ObjectUtils.isEmpty(users)) {
            inQueueCount = users.size();
            int maxCountInQueueInt = Integer.parseInt(this.maxCountInQueue);
            if (maxCountInQueueInt > 10) {
                log.warn("maxCountInQueue 值为：{}, 操作最大值 10，按照10处理。", this.maxCountInQueue);
                maxCountInQueueInt = 10;
            }

            if (maxCountInQueueInt < inQueueCount) {
                canTrans = "FALSE";
            }
        }

        model.addAttribute("inQueueCount", inQueueCount).addAttribute("canTrans", canTrans);
        return "feishu-begin-scan";
    }

    @GetMapping({"/feishu-new-tenant"})
    @ResponseBody
    public String newTenantInfo(Model model) {
        return this.getTenantName(this.newTenantAppId, this.newTenantAppSecret);
    }

    @GetMapping({"/feishu-old-tenant"})
    @ResponseBody
    public String oldTenantInfo(Model model) {
        return this.getTenantName(this.oldTenantAppId, this.oldTenantAppSecret);
    }

    public String getTenantName(String appId, String appSecret) {
        Client client = Client.newBuilder(appId, appSecret).openBaseUrl(BaseUrlEnum.FeiShu).requestTimeout(3L, TimeUnit.SECONDS).logReqAtDebug(true).build();
        QueryTenantResp tenantResp = null;

        try {
            tenantResp = client.tenant().tenant().query();
            if (tenantResp.getCode() != 0) {
                log.error("无法获取企业信息，错误码：{}，错误信息：{}", tenantResp.getCode(), tenantResp.getMsg());
                return tenantResp.getMsg();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QueryTenantRespBody queryTenant = (QueryTenantRespBody)tenantResp.getData();
        Tenant tenant = queryTenant.getTenant();
        return tenant.getName();
    }

    @GetMapping({"/feishu-new"})
    public String newFeishuTenant(Model model) {
        String newTenantName = this.getTenantName(this.newTenantAppId, this.newTenantAppSecret);
        model.addAttribute("title", newTenantName + "授权").addAttribute("name", newTenantName).addAttribute("appId", this.newTenantAppId).addAttribute("url", this.url).addAttribute("newQrAddress", this.newQrAddress).addAttribute("newTenantName", newTenantName);
        return "feishu-new";
    }

    @GetMapping({"/feishu-old"})
    public String oldFeishuTenant(Model model, HttpSession session) {
        String oldTenantName = this.getTenantName(this.oldTenantAppId, this.oldTenantAppSecret);
        model.addAttribute("title", oldTenantName + "授权").addAttribute("name", oldTenantName).addAttribute("appId", this.oldTenantAppId).addAttribute("url", this.url).addAttribute("oldQrAddress", this.oldQrAddress).addAttribute("oldTenantName", oldTenantName);
        return "feishu-old";
    }

    public String getRedirectUrl(String path) {
        return "redirect:" + this.url + path;
    }
}
