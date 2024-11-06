package com.scan.keeper.common.bean;


/**
 * Description: 
 * User: renjing
 * Date: 2018-05-17
 * Time: 18:44
 */
public class Result {

	final public static Result SUCCESS = new Result(1, "success");
	final public static Result ERROR = new Result(0, "error");
	final public static Result NOT_AUTHED = new Result(403, "not authed");
	final public static Result ERROR_PARAM = new Result(40011, "Request Params error");

	private int code;
	private String msg;
	private Object context;
	private long systemTime = System.currentTimeMillis();

	public Result(int code, String msg) {
		this(code, msg, null);
	}

	public Result(int code, String msg, Object context) {
		this.code = code;
		this.msg = msg;
		this.context = context;
		this.systemTime = System.currentTimeMillis();
	}
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getSystemTime() {
		return systemTime;
	}

	public Result copyThis() {
		return new Result(code, msg, null);
	}

	public static Result error(String error) {
		return new Result(ERROR.code, error);
	}
	
	public static Result error(int code, String error) {
		return new Result(code, error);
	}
	
	public static Result success(Object obj) {
		Result r = new Result(SUCCESS.code, "success");
		r.setContext(obj);
		return r;
		
	}
}
