package com.task.two.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.task.two.bean.TMember;
import com.task.two.bean.TMemberExample;
import com.task.two.mapper.TMemberMapper;
import com.task.two.service.LoginByNoteService;
import com.task.two.vo.req.LoginByNoteVo;
import com.task.two.vo.resp.UserRespVo;

@Service
public class LoginByNoteServiceImpl implements LoginByNoteService {

	@Autowired
	TMemberMapper memberMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Override
	public UserRespVo LoginByNote(LoginByNoteVo vo) {

		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(vo.getLoginacct());

		List<TMember> list = memberMapper.selectByExample(example);

		if (list == null || list.size() == 0) {
			throw new RuntimeException("无此用户");
		}

		TMember member = list.get(0);
		String code = stringRedisTemplate.opsForValue().get(vo.getLoginacct());
		if (!vo.getCode().equals(code)) {
			throw new RuntimeException("验证码错误");
		}
		UserRespVo userRespVo = new UserRespVo();
		BeanUtils.copyProperties(vo, userRespVo);

		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		userRespVo.setAccessToken(accessToken);

		stringRedisTemplate.opsForValue().set(accessToken, member.getId().toString());

		return userRespVo;
	}

}
