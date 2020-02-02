package com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before // 在方法开始前添加一个用户
    public void addUser() {
        simpleAccountRealm.addAccount("wmyskxz", "123456");
    }

    
//    Authentication（认证）：用户身份识别，通常被称为用户“登录”
//    Authorization（授权）：访问控制。比如某个用户是否具有某个操作的使用权限。
//    Session Management（会话管理）：特定于用户的会话管理,甚至在非web 或 EJB 应用程序。
//    Cryptography（加密）：在对数据源使用加密算法加密的同时，保证易于使用。
//    还有其他的功能来支持和加强这些不同应用环境下安全领域的关注点。特别是对以下的功能支持：
//
//    Web支持：Shiro的Web支持API有助于保护Web应用程序。
//    缓存：缓存是Apache Shiro API中的第一级，以确保安全操作保持快速和高效。
//    并发性：Apache Shiro支持具有并发功能的多线程应用程序。
//    测试：存在测试支持，可帮助您编写单元测试和集成测试，并确保代码按预期得到保障。
//    “运行方式”：允许用户承担另一个用户的身份(如果允许)的功能，有时在管理方案中很有用。
//    “记住我”：记住用户在会话中的身份，所以用户只需要强制登录即可。
//    Subject：当前用户，Subject 可以是一个人，但也可以是第三方服务、守护进程帐户、时钟守护任务或者其它–当前和软件交互的任何事件。
//    SecurityManager：管理所有Subject，SecurityManager 是 Shiro 架构的核心，配合内部安全组件共同组成安全伞。
//    Realms：用于进行权限信息的验证，我们自己实现。Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，得到Shiro 所需的相关的数据。
//    在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）
    @Test
    public void testAuthentication() {

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("wmyskxz", "123456");
        subject.login(token); // 登录

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出true

        subject.logout(); // 登出

        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出false
//        运行之后可以看到预想中的效果，先输出isAuthenticated:true表示登录认证成功，然后再输出isAuthenticated:false表示认证失败退出登录
    }
    
    SimpleAccountRealm simpleAccountRoleRealm = new SimpleAccountRealm();

    @Before // 在方法开始前添加一个用户,让它具备admin和user两个角色
    public void addUserwithRole() {
    	simpleAccountRoleRealm.addAccount("wmyskxz", "123456", "admin", "user");
    }

    @Test
    public void testAuthorization() {

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRoleRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("wmyskxz", "123456");
        subject.login(token); // 登录

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出true
        // 判断subject是否具有admin和user两个角色权限,如没有则会报错
        subject.checkRoles("admin","user");
        subject.checkRole("order"); // 报错
    }
    
    @Test
    public void testAuthenticationRealm() {

        MyRealm myRealm = new MyRealm(); // 实现自己的 Realm 实例

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("wmyskxz", "123456");
        subject.login(token); // 登录

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出true
        // 判断subject是否具有admin和user两个角色权限,如没有则会报错
        subject.checkRoles("admin", "user");
//        subject.checkRole("xxx"); // 报错
        // 判断subject是否具有user:add权限
        subject.checkPermission("user:add");
    }
}
