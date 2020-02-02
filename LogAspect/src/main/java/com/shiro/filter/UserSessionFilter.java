package com.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.entity.User;
import com.service.UserService;

public class UserSessionFilter extends AccessControlFilter {

	@Autowired
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		Subject subject = this.getSubject(request, response);
		if (subject == null) {
			return false;
		}

		String username = (String) subject.getPrincipal();

		// HttpSession session = WebUtils.toHttp(request).getSession();
		org.apache.shiro.session.Session session = subject.getSession();
		User sessionUser = (User) session.getAttribute("sessionUser");
		if (sessionUser == null) {
			// 根据用户名到数据库中查询
			sessionUser = userService.getUser(username);
		}
		session.setAttribute("sessionUser", sessionUser);

		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}

}
