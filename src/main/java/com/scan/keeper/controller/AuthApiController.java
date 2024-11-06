package com.scan.keeper.controller;

import com.scan.keeper.common.bean.ResponseResult;
import com.scan.keeper.common.constant.ResultEnum;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
@Api("")
@RequestMapping("/auth/api")
public class AuthApiController {
    private static Logger logger = LoggerFactory.getLogger(AuthApiController.class);

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    protected HttpServletRequest request;

    protected HttpServletResponse response;


    @ApiOperation(value = "1. ")
    @RequestMapping(value = "/queryRewardInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseResult<Object> queryRewardInfo(
    ) throws Exception {
        logger.info("：");

        return new ResponseResult<>(ResultEnum.SUCCESS);
    }


}
