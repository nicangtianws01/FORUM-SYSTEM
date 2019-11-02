package com.ws.forum.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfig {
	/**@introduce 配置SecurityManager，通过参数自动注入realm，cacheManager对象
	 * @info CookieRememberMeManager并没有交给spring管理
	 * @param realm
	 * @return SecurityManager
	 */
	@Bean("securityManager")
	public SecurityManager newSecurityManager(
			@Autowired Realm realm,
			@Qualifier("newCacheManager") CacheManager cacheManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(realm);
		manager.setCacheManager(cacheManager);
		manager.setRememberMeManager(newCookieRememberMeManager());
		manager.setSessionManager(newSessionManager());
		return manager;
	}
	/**@introduce 过滤工厂，生成过滤器
	 * @param securityManager
	 * @return ShiroFilterFactoryBean
	 */
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(
			SecurityManager securityManager) {
		//创建过滤器工厂
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		sfBean.setLoginUrl("/doLoginUI");
		//准备访问过滤器的map对象
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//静态资源允许匿名访问:"anon"
		 map.put("/browser-components/**","anon");
		 //登录方法设置匿名访问
		 map.put("/user/doLogin","anon");
		 map.put("/common/page", "anon");
//		 map.put("/index","anon");
//		 map.put("/user/doFindPageObjects", "anon");
//		 map.put("/user/doSaveUser", "anon");
//		 map.put("/post/doFindPageObjects", "anon");
//		 map.put("/post/post_list", "anon");
//		 map.put("/user/user_list", "anon");
//		 map.put("/user/user_edit", "anon");
		 //配置退出登录
		 map.put("/doLogout","logout");
		 //除了匿名访问的资源,其它都要认证("authc")后访问
		 map.put("/**","user");//authc
		 
		 sfBean.setFilterChainDefinitionMap(map);
		 return sfBean;
	}
	/**@introduce 生命周期管理器,按照spring的标准编写。不按照spring默认生命周期管理需要配置该对象。
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor
	    newLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	/**@introduce0 为目标业务对象创建代理对象
	 * 、spring启动时扫描所有advisor对象，基于advisor指定的切入点创建代理对象
	 * @return
	 */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	/**@introduce0 通过此对象的matchs方法判断是否创建代理对象
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor 
	newAuthorizationAttributeSourceAdvisor(
		    		    @Autowired SecurityManager securityManager) {
			        AuthorizationAttributeSourceAdvisor advisor=
					new AuthorizationAttributeSourceAdvisor();
			        advisor.setSecurityManager(securityManager);
			 return advisor;
	}
	/**@introduce 配置缓存对象
	 * @return
	 */
	@Bean("newCacheManager")
	public CacheManager newCacheManager(){
		 return new MemoryConstrainedCacheManager();
	}
	
	public SimpleCookie newCookie() {
		 SimpleCookie c=new SimpleCookie("rememberMe");
		 c.setMaxAge(10*60);//10分钟
		 return c;
	}
	
	/**@info 可以不用讲给spring管理
	 * @return
	 */
	public CookieRememberMeManager newCookieRememberMeManager() {
		CookieRememberMeManager gm = new CookieRememberMeManager();
		gm.setCookie(newCookie());
		return gm;
	}
	/**
	 * @return
	 */
	public DefaultWebSessionManager newSessionManager() {
		 DefaultWebSessionManager sManager=
				 new DefaultWebSessionManager();
		 sManager.setGlobalSessionTimeout(60*60*1000);
		 return sManager;
	 }
	
}
