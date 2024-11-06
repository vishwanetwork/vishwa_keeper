package com.scan.keeper.common.handler;

import com.alibaba.fastjson.JSON;
import com.scan.keeper.common.bean.ResultDO;
import com.scan.keeper.common.interetpor.AppInterceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description: 
 * Author lv bin
 * @date 2018/5/16 10:45
 * version V1.0.0
 */

@ControllerAdvice
public class AppResponseInterceptor implements ResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(AppResponseInterceptor.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 
        if (mediaType != null && o != null
                && mediaType.includes(MediaType.APPLICATION_JSON)
                && o instanceof ResultDO) {

            if (serverHttpRequest instanceof ServletServerHttpRequest) {

                ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;

                HttpServletRequest httpServletRequest = request.getServletRequest();

                Date requestTime = (Date) httpServletRequest.getAttribute(AppInterceptors.REQUEST_TIME);

                long useTime = System.currentTimeMillis() - (requestTime == null ? 0 : requestTime.getTime());

                Method method = methodParameter.getMethod();

                logger.debug("request controller:" + method.getDeclaringClass() + " request method:" + method.getName());

                logger.debug("request link:" + serverHttpRequest.getURI() + " times:" + useTime);
            }


            logger.debug("response content:" + JSON.toJSONString(o));
        }

        return o;
    }
}
