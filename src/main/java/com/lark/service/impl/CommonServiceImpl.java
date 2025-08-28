package com.lark.service.impl;

import com.lark.data.bean.RetryHandler;
import com.lark.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    public CommonServiceImpl() {
    }

    public void retry(RetryHandler handler, int limit, String action) {
        int count = 0;

        while(true) {
            try {
                handler.handle();
                return;
            } catch (Exception exception) {
                ++count;
                logger.info(action + " 重试次数: " + count);
                exception.printStackTrace();
                if (count == limit) {
                    throw exception;
                }
            }
        }
    }
}
