package com.common.utils.controller;

import com.common.utils.common.base.entity.ResponseBean;
import com.common.utils.common.constants.ResultEnum;
import com.common.utils.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseBean<Object> controllerTest() {
        commonService.getCode();
        LOGGER.info("[controller: ok!]");
        return new ResponseBean<>(ResultEnum.Success);
    }

}
