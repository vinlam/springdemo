package com.shiro.realm;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import com.entity.User;
import com.service.UserService;

public class MyAuthorRealm<userMapper> extends AuthorizingRealm{
	
	@Autowired
	private  UserService userService;
	 
	@Override
	public Class getAuthenticationTokenClass() {
		return super.getAuthenticationTokenClass();
	}
	
	//是否支持该token
	//Realm支持的类可以是token的子类或相同类
	@Override
	public boolean supports(AuthenticationToken token) {
		return token != null && getAuthenticationTokenClass().isAssignableFrom(token.getClass());
	}
	
	@Override
	protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
		return super.isPermitted(permission, info);
	}
	/**
	 * 对当前subject进行权限认证(授权)
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username=(String)principals.getPrimaryPrincipal();
		
		SecurityUtils.getSubject().getSession();
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		Set<String> roleNames=userService.findUserRoleNames(username);
		
		Set<String> permNames=userService.findUserPermNames(username);
		authorizationInfo.setRoles(roleNames);
		authorizationInfo.setStringPermissions(permNames);
		return authorizationInfo;
		  
	}

	/**
	 * 对当前subject进行身份认证
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		  
		String username=(String)token.getPrincipal();
		
		User user =userService.findUserByUsername(username);
		if(user==null) {
			//登陆失败
			return null;
		}
		AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getName(),user.getPassword(),this.getClass().getSimpleName());
		return authcInfo;
		
	}

}
