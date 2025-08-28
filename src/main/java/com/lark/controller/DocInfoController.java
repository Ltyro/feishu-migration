package com.lark.controller;

import com.lark.data.dto.doc.DocDTO;
import com.lark.data.pojo.UserInfo;
import com.lark.service.DocInfoService;
import com.lark.service.UserInfoService;
import com.lark.utils.MapUtil;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocInfoController {
    private static final Logger logger = LoggerFactory.getLogger(DocInfoController.class);
    @Autowired
    private DocInfoService docInfoService;
    @Autowired
    private UserInfoService userInfoService;

    public DocInfoController() {
    }

    @PostMapping({"/createDoc"})
    public Map<String, Object> createDoc(HttpSession session) {
        logger.info("文档创建接口");
        String logMsg = "非首次创建文档";
        if (session.getAttribute("doc_token") == null || session.getAttribute("doc_url") == null) {
            logMsg = "首次创建文档";
            String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
            DocDTO doc = this.docInfoService.createDoc(oldUserOpenId);
            session.setAttribute("doc_token", doc.getObjToken());
            session.setAttribute("doc_url", doc.getUrl());
        }

        logger.info(logMsg);
        return MapUtil.instance().put("url", session.getAttribute("doc_url")).build();
    }

    @GetMapping({"/getdocfromlark"})
    @ResponseBody
    public String transferDocs(HttpSession session) {
        String oldUserOpenId = (String)session.getAttribute("old_user_open_id");
        String newUserOpenId = (String)session.getAttribute("new_user_open_id");
        UserInfo userInfo = this.userInfoService.queryUserInfoByOpenId(oldUserOpenId, newUserOpenId);
        String procStatus = userInfo.getProcStatus();
        String procMsg = userInfo.getProcMsg();
        if ("未开始".equals(procStatus) || "备份失败".equals(procStatus) && "可重启".equals(procMsg)) {
            this.docInfoService.startTransferDocs(oldUserOpenId, newUserOpenId);
            userInfo.setProcStatus("排队中");
            userInfo.setProcMsg("进行迁移的用户数较多，正在排队。请留意开始迁移开始时的消息提醒");
            this.userInfoService.updateUserInfo(userInfo);
        }

        if ("复制失败".equals(procStatus) && "可重启".equals(procMsg)) {
            this.docInfoService.restartTransferDocs(oldUserOpenId, newUserOpenId);
            userInfo.setProcStatus("排队中");
            userInfo.setProcMsg("进行迁移的用户数较多，正在排队。请留意开始迁移开始时的消息提醒");
            this.userInfoService.updateUserInfo(userInfo);
        }

        return "开始任务";
    }
}
