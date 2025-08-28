package com.lark.controller;

import com.lark.data.constants.TenantType;
import com.lark.data.constants.UserStatusEnZh;
import com.lark.data.dto.contact.UserDTO;
import com.lark.data.dto.token.UserAccessTokenDTO;
import com.lark.data.pojo.UserInfo;
import com.lark.service.AccessTokenService;
import com.lark.service.DocInfoService;
import com.lark.service.DriveService;
import com.lark.service.UserInfoService;
import com.lark.utils.MapUtil;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${lark.url}")
    private String url;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DriveService driveService;
    @Autowired
    private DocInfoService docInfoService;

    public UserController() {
    }

    @GetMapping({"/login"})
    public String login(@RequestParam String code, @RequestParam String state, HttpSession session) {
        if (StringUtils.isBlank(code)) {
            return this.getRedirectUrl("/error/login_error.html");
        } else {
            logger.info("用户code：" + code);
            return StringUtils.isBlank(state) ? this.oldUserLogin(code, session) : this.newUserLogin(code, session);
        }
    }

    private String oldUserLogin(String code, HttpSession session) {
        logger.info("老用户登陆");
        UserAccessTokenDTO userAccessToken = this.accessTokenService.getUserAccessTokenByCode(TenantType.OLD, code);
        if (userAccessToken != null) {
            String openId = userAccessToken.getOpenId();
            session.setAttribute("old_user_name", userAccessToken.getName());
            session.setAttribute("old_user_open_id", userAccessToken.getOpenId());
            UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(openId);
            if (userInfo != null && !"未开始".equals(userInfo.getProcStatus())) {
                logger.info("openId为:{}的user已开始迁移，重定向至transferring.html", openId);
                session.setAttribute("new_user_open_id", userInfo.getNewUserOpenId());
                return "redirect:/transferring.html";
            } else {
                return this.getRedirectUrl("/new");
            }
        } else {
            return this.getRedirectUrl("/error/login_error.html");
        }
    }

    private String newUserLogin(String code, HttpSession session) {
        logger.info("新用户登陆");
        if (session.getAttribute("old_user_open_id") != null && session.getAttribute("old_user_name") != null) {
            UserAccessTokenDTO newUserAccessToken = this.accessTokenService.getUserAccessTokenByCode(TenantType.NEW, code);
            if (newUserAccessToken == null) {
                return this.getRedirectUrl("/error/login_error.html");
            } else {
                String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
                String newUserOpenId = newUserAccessToken.getOpenId();
                String oldUserRootFolderToken = this.driveService.getRootFolderToken(TenantType.OLD, oldUserOpenId);
                String newUserRootFolderToken = this.driveService.getRootFolderToken(TenantType.NEW, newUserOpenId);
                if (StringUtils.isAllBlank(new CharSequence[]{oldUserRootFolderToken, newUserRootFolderToken})) {
                    return this.getRedirectUrl("/error/login_error.html");
                } else {
                    UserInfo userInfoData = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
                    session.setAttribute("new_user_open_id", newUserOpenId);
                    if (userInfoData == null) {
                        UserInfo userInfo = UserInfo.builder().oldUserOpenId(oldUserOpenId).oldUserToken(this.userInfoService.queryUserAccessTokenByOpenId(TenantType.OLD, oldUserOpenId)).oldUserRefreshToken(this.userInfoService.queryUserRefreshTokenByOpenId(TenantType.OLD, oldUserOpenId)).oldRootFolderToken(oldUserRootFolderToken).newUserOpenId(newUserOpenId).newUserToken(this.userInfoService.queryUserAccessTokenByOpenId(TenantType.NEW, newUserOpenId)).newUserRefreshToken(this.userInfoService.queryUserRefreshTokenByOpenId(TenantType.NEW, newUserOpenId)).newRootFolderToken(newUserRootFolderToken).procStatus("未开始").procMsg("任务未开始，点击开始").build();
                        this.userInfoService.insertUserInfo(userInfo);
                    }

                    return this.getRedirectUrl("/authorization");
                }
            }
        } else {
            return this.getRedirectUrl("/error/login_error.html");
        }
    }

    @GetMapping({"/userStatus"})
    @ResponseBody
    public Map<String, Object> getUserStatus(HttpSession session) {
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        logger.info("获取openId为:{}的用户状态", oldUserOpenId);
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        if (userInfo == null) {
            logger.error("获取openId为:{}的用户为空，异常", oldUserOpenId);
            return null;
        } else {
            return MapUtil.instance().put("procStatus", userInfo.getProcStatus()).put("procMsg", userInfo.getProcMsg()).put("transStatus", UserStatusEnZh.getChineseByEnglish(userInfo.getStatus())).put("transStatusEn", userInfo.getStatus()).put("map", this.docInfoService.getResult(oldUserOpenId, newUserOpenId)).build();
        }
    }

    @GetMapping({"/getName"})
    @ResponseBody
    public Map<String, Object> getUserName(HttpSession session) {
        String oldUserName = (String)session.getAttribute("old_user_name");
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        String docToken = (String)session.getAttribute("doc_token");
        if (session.getAttribute("new_user_name") != null) {
            return MapUtil.instance().put("isSuccess", 1).put("newName", session.getAttribute("new_user_name")).put("oldName", oldUserName).build();
        } else {
            UserDTO newUser = this.docInfoService.getDocMember(oldUserOpenId, docToken);
            if (newUser == null) {
                return MapUtil.instance().put("isSuccess", 0).build();
            } else {
                String currentNewUserOpenId = newUser.getOpenId();
                session.setAttribute("new_user_open_id", currentNewUserOpenId);
                this.userInfoService.replaceUserOpenId(oldUserOpenId, newUserOpenId, currentNewUserOpenId);
                return MapUtil.instance().put("isSuccess", 1).put("newName", newUser.getName()).put("oldName", oldUserName).build();
            }
        }
    }

    public String getRedirectUrl(String path) {
        return "redirect:" + this.url + path;
    }
}
