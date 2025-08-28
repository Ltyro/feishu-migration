package com.lark.service;

import com.lark.data.pojo.DocInfo;
import java.util.List;

public interface SheetService {
    String getResultSheetUrl(String oldUserOpenId, String newUserOpenId, List<DocInfo> docInfoList);

    String getResultSheetOldUrl(String oldUserOpenId, String newUserOpenId, List<DocInfo> docInfoList);
}
