package com.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
	private final static Logger log = LoggerFactory.getLogger(TokenUtil.class);
	@Autowired
	private RedisUtils redisUtils;
	
	//token 过期时间为30秒
	private final static Long TOKEN_EXPRIE = 30L;
	
	private final static String TOKEN_NAME = "token";
	
	public String generateToken() {
		String uuid = UUID.randomUUID().toString();
		String token = null;
		try {
			token = Md5Util.encrypted(uuid);
			redisUtils.set(TOKEN_NAME, token, TOKEN_EXPRIE);
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException",e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException",e.getMessage());
		}
		
		return token;
	}
	
	public boolean verifyToken(HttpServletRequest request) throws Exception {
		String token = request.getHeader(TOKEN_NAME);
		if(StringUtils.isBlank(token)) {
			log.error("token 不存在！");
			throw new Exception("token 不存在！");
		}
		
		if(!redisUtils.haskey(token)) {
			log.error("token 已过期！");
			throw new Exception("token 已过期！");
		}
		String cacheToken = String.valueOf(redisUtils.get(TOKEN_NAME));
		if(!token.equals(cacheToken)) {
			log.error("token 校验失败！");
			throw new Exception("token 校验失败！");
		}
		Boolean del = redisUtils.del(TOKEN_NAME);
		if(!del) {
			log.error("token 删除失败！");
			throw new Exception("token 删除失败！");
		}
		return true;
		
	}
}
