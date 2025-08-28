package com.lark.service;

import com.lark.data.bean.RetryHandler;

public interface CommonService {
    void retry(RetryHandler handler, int limit, String action);
}
