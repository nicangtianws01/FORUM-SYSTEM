package com.ws.forum.service.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.forum.dao.UserDao;
import com.ws.forum.pojo.User;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		//加密方式
		matcher.setHashAlgorithmName("MD5");
		//设置加密次数
		matcher.setHashIterations(1);
		
		super.setCredentialsMatcher(matcher);
		
	}

	/**@introduce 负责认证信息的获取及封装
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//获取用户名
		String username = upToken.getUsername();
		//使用用户名查询数据
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User user = userDao.selectOne(queryWrapper);
		//判断用户是否存在,是否可用
		if(user == null) {
			throw new UnknownAccountException();
		}
		if(user.getValid() == 0) {
			throw new LockedAccountException();
		}
		//封装用户信息
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=
				new SimpleAuthenticationInfo(
						user,//principal (身份)
						user.getPassword(),//hashedCredentials
						credentialsSalt, //credentialsSalt
						getName());//realName
		//6.返回封装结果
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

}
