package com.task.two.controller;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.two.component.SmsTemplate;
import com.task.two.service.LoginByNoteService;
import com.task.two.vo.req.LoginByNoteVo;
import com.task.two.vo.resp.AppResponse;
import com.task.two.vo.resp.UserRespVo;

import io.swagger.annotations.ApiOperation;

@RestController
public class LoginByNoteController {

	@Autowired
	SmsTemplate smsTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	LoginByNoteService loginByNoteService;

	
	
	@ApiOperation(value = "用户登陆")
	@PostMapping("/login")
	public AppResponse<UserRespVo> LoginByNote(LoginByNoteVo vo) {
		try {
			UserRespVo userRespVo=loginByNoteService.LoginByNote(vo);
			return AppResponse.ok(userRespVo);
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}

	}

	@ApiOperation(value = "发送短信验证码")
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginacct) {

		StringBuilder code = new StringBuilder();

		for (int i = 1; i <= 4; i++) {
			code.append(new Random().nextInt(10));
		}

		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", loginacct);
		querys.put("param", "code:" + code.toString());
		querys.put("tpl_id", "TP1711063");
		smsTemplate.sendSms(querys);

//		放进redis五分钟有效
		stringRedisTemplate.opsForValue().set(loginacct, code.toString(), 5, TimeUnit.MINUTES);

		return AppResponse.ok("ok");
	}

}
