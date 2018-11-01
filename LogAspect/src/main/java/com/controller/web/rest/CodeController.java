package com.controller.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.ReturnFormat;
import com.controller.BaseController;
import com.service.CodeService;
@RestController
@RequestMapping(value="/api")
public class CodeController extends BaseController {
	@Autowired
	private CodeService codeService;

	/**
	 * 发送短信
	 * 
	 * @param username
	 *            用户名
	 * @param type
	 *            register/backpwd
	 * @return status: 0 2010 2029 1011 1010 1006 1020
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, produces = "application/json")
	public String sendMessage(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "forType", required = true) String forType,
			@RequestParam(value = "userType", required = true) String userType) {
		if (null == username || "".equals(username)) {
			return retContent(2010, null);
		}
		if (!"user".equals(userType) && !"merchant".equals(userType)) {
			return retContent(2029, null);
		}
		if (!"register".equals(forType) && !"backpwd".equals(forType)) {
			return retContent(2029, null);
		}
		//return codeService.sendMessage(username, forType, userType);
		return retContent(0,codeService.sendMessage(username, forType, userType));
	}
}
