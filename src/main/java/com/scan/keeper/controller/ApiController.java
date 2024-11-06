package com.scan.keeper.controller;

import com.scan.keeper.common.bean.ResponseResult;
import com.scan.keeper.common.constant.ResultEnum;
import com.scan.keeper.utils.BtcUtils2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@RestController
@Api("")
@RequestMapping("/api")
public class ApiController {
    private static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    protected HttpServletRequest request;

    protected HttpServletResponse response;


    @ModelAttribute
    public void initParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.request = request;
        this.response = response;
    }

    @ApiOperation(value = "1. ")
    @RequestMapping(value = "/registUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseResult<Object> registUser(
    ) throws Exception {
        logger.info("1. " );

        BtcUtils2.processTransactionsForBlockHeight("3196184");

        return new ResponseResult<>(ResultEnum.SUCCESS);
    }
}
