package com.common.utils.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

    public int getCode() {
        LOGGER.info("[commonService: 执行了]");
        return 1;
    }

}
