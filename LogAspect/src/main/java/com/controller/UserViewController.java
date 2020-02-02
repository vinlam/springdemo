package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.controller.web.rest.UserController;

@Controller
@RequestMapping("/view/user")
public class UserViewController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

//	@Autowired
//	private IUserService userService;
//
//	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
//	@ResponseBody
//	public ResponseVO<String> checkLogin(@RequestParam("userName") String userName,
//			@RequestParam("password") String password) {
//		ResponseVO<String> response = new ResponseVO<String>();
//		try {
//			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
//
//			Subject subject = SecurityUtils.getSubject();
//
//			subject.login(token);
//		} catch (Exception e) {
//			logger.error("Login Error:", e);
//			response.setStatus(ResponseVO.failCode);
//			Throwable ex = e.getCause();
//			if (ex instanceof BugException) {
//				if (ex.getMessage() != null) {
//					response.setMessage(ex.getMessage());
//				}
//			} else if (e instanceof IncorrectCredentialsException) {
//				response.setMessage("密码错误");
//			} else {
//				response.setMessage("登录失败");
//			}
//		}
//
//		return response;
//	}
//
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public ResponseVO<String> logout() {
//		ResponseVO<String> response = new ResponseVO<String>();
//		Subject subject = SecurityUtils.getSubject();
//		if (subject.isAuthenticated()) {
//			subject.logout();
//		}
//		return response;
//	}
//
//	@RequestMapping(value = "/queryUserInfo", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseVO<UserVO> queryUserInfo() {
//		ResponseVO<UserVO> response = new ResponseVO<UserVO>();
//		try {
//			UserVO user = userService.selectUserById("1");
//			response.setData(user);
//		} catch (Exception e) {
//			logger.error("queryUserInfo error:", e);
//			response.setStatus(ResponseVO.failCode);
//		}
//
//		return response;
//	}

}
