package com.scan.keeper.common.handler;

import com.scan.keeper.common.bean.ResultDO;
import com.scan.keeper.common.constant.ErrorConstant;
import com.scan.keeper.common.exception.AppWebException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.ConnectException;

/**
 * @Description:
 * Author lv bin
 * @date 2018/5/16 9:35
 * version V1.0.0
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    protected static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultDO<String> runtimeExceptionHandler(RuntimeException runtimeException) {
        return result(ErrorConstant.RUNTIME_EXCEPTION.getCode(), ErrorConstant.RUNTIME_EXCEPTION.getMsg(), runtimeException);
    }

    //
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultDO<String> nullPointerExceptionHandler(NullPointerException ex) {
        return result(ErrorConstant.NULL_POINTER_EXCEPTION.getCode(), ErrorConstant.NULL_POINTER_EXCEPTION.getMsg(), ex);
    }

    //
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResultDO<String> classCastExceptionHandler(ClassCastException ex) {
        return result(ErrorConstant.CLASS_CAST_EXCEPTION.getCode(), ErrorConstant.CLASS_CAST_EXCEPTION.getMsg(), ex);
    }

    //IO
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResultDO<String> iOExceptionHandler(IOException ex) {
        return result(ErrorConstant.IO_EXCEPTION.getCode(), ErrorConstant.IO_EXCEPTION.getMsg(), ex);
    }

    //
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResultDO<String> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return result(ErrorConstant.NO_SUCH_METHOD_EXCEPTION.getCode(), ErrorConstant.NO_SUCH_METHOD_EXCEPTION.getMsg(), ex);
    }

    //
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResultDO<String> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return result(ErrorConstant.INDEX_OUT_OF_BOUNDS_EXCEPTION.getCode(), ErrorConstant.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMsg(), ex);
    }

    //
    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    public ResultDO<String> connectException(ConnectException ex) {
        return result(ErrorConstant.CONNECT_EXCEPTION.getCode(), ErrorConstant.CONNECT_EXCEPTION.getMsg(), ex);
    }

    //400
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResultDO<String> requestNotReadable(HttpMessageNotReadableException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    //400
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResultDO<String> requestTypeMismatch(TypeMismatchException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    //400
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResultDO<String> requestMissingServletRequest(MissingServletRequestParameterException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    @ExceptionHandler({ ServletException.class })
    @ResponseBody
    public ResultDO<String> http404(ServletException ex) {
        return result(ErrorConstant.NOT_FOUND_REQUEST.getCode(), ErrorConstant.NOT_FOUND_REQUEST.getMsg(), ex);
    }

    //405
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResultDO<String> request405(HttpRequestMethodNotSupportedException ex) {
        return result(ErrorConstant.METHOD_NOT_ALLOWED.getCode(), ErrorConstant.METHOD_NOT_ALLOWED.getMsg(), ex);
    }

    //406
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResultDO<String> request406(HttpMediaTypeNotAcceptableException ex) {
        return result(ErrorConstant.NOT_ACCEPTABLE.getCode(), ErrorConstant.NOT_ACCEPTABLE.getMsg(), ex);
    }

    //500
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResultDO<String> server500(RuntimeException runtimeException) {
        return result(ErrorConstant.INTERNAL_SERVER_ERROR.getCode(), ErrorConstant.INTERNAL_SERVER_ERROR.getMsg(), runtimeException);
    }

    //app web 
    @ExceptionHandler({AppWebException.class})
    @ResponseBody
    public ResultDO<String> appWebException(AppWebException appWebException) {
        return result(appWebException.getErrCode(), appWebException.getMessage(), appWebException);
    }

    @ExceptionHandler({JsonMappingException.class})
    @ResponseBody
    public ResultDO<String> jsonMappingException(JsonMappingException jsonMappingException) {
        return result(ErrorConstant.ERROR_FORMAT_PARAMETER.getCode(), ErrorConstant.ERROR_FORMAT_PARAMETER.getMsg(), jsonMappingException);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResultDO<String> MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return result(ErrorConstant.ERROR_FORMAT_PARAMETER.getCode(), ErrorConstant.ERROR_FORMAT_PARAMETER.getMsg(), methodArgumentNotValidException);
    }


/**
     * 
     * @param errCode
     * @param errMsg
     * @param e
     * @return
     */


    private ResultDO<String> result(int errCode, String errMsg, Exception e){
        ResultDO<String> resultDO = new ResultDO<String>();
        resultDO.setCode(errCode);
        resultDO.setMessage(errMsg);

        logException(e);

        return resultDO;
    }

/**
     * 
     * @param e
     */


    private void logException(Exception e){
        if(e instanceof AppWebException){
            LOGGER.warn(e.getMessage(), e);
        }else{
            LOGGER.error(e.getMessage(), e);
        }
    }
}
