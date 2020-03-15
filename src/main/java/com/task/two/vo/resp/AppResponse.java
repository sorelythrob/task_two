package com.task.two.vo.resp;



import com.task.two.enums.ResponseCodeEnume;

import lombok.Data;
import lombok.Setter;

/**
 * 应用统一返回结果数据封装类
 * @param <T> 返回结果数据类型
 */
@Data
@Setter
public class AppResponse<T> {

	private Integer code;
	private String msg;
	private T data;
	

	/**
	 * 快速响应成功
	 * @param data
	 * @return
	 */
	public static<T> AppResponse<T> ok(T data){
		AppResponse<T> resp = new AppResponse<>();
		resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
		resp.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
		resp.setData(data);
		return resp;
	}
	
	/**
	 * 快速响应失败
	 */
	public static<T> AppResponse<T> fail(T data){
		AppResponse<T> resp = new AppResponse<>();
		resp.setCode(ResponseCodeEnume.FAIL.getCode());
		resp.setMsg(ResponseCodeEnume.FAIL.getMsg());
		resp.setData(data);
		return resp;
	}
}
