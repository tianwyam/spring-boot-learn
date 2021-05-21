package com.tianya.springboot.jwt.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tianya.springboot.jwt.annotation.NotAuth;
import com.tianya.springboot.jwt.common.ResponseResult;
import com.tianya.springboot.jwt.entity.TokenReqBean;
import com.tianya.springboot.jwt.utils.JwtUtils;
import com.tianya.springboot.jwt.utils.RequestUtils;


/**
 * @description
 *	做权限TOKEN认证方面的
 * @author TianwYam
 * @date 2021年5月20日上午9:10:51
 */
@Aspect
@Component
public class AuthCheckAop {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthCheckAop.class);

	
	
	public static final String HEADER_AUTH_TOKEN = "token" ;
	
	/**
	 * @description
	 *	切点
	 * @author TianwYam
	 * @date 2021年5月20日上午9:12:53
	 */
	@Pointcut("execution(* com.fh.cd.net..controller..*(..))")
	public void pointCut() {}
	
	
	
	
	@Around("pointCut()")
	public Object auth(ProceedingJoinPoint joinPoint) {
		
		TokenReqBean checkToken = null ;
		
		// 需要认证
		if (!notAuth(joinPoint)) {
			
			// 获取 header中token
			String token = RequestUtils.getHeader(HEADER_AUTH_TOKEN);
			if (StringUtils.isBlank(token)) {
				return ResponseResult.error("请求中token认证为空");
			}
			
			try {
				// 校验TOKEN
				checkToken = JwtUtils.checkToken(token);
			} catch (TokenExpiredException e) {
				LOG.error("token过期(默认有效期2小时)", e);
				return ResponseResult.error("token过期(默认有效期2小时)");
			} catch (JWTVerificationException e) {
				LOG.error("token无效", e);
				return ResponseResult.error("token无效");
			} catch (Exception e) {
				LOG.error("校验token错误", e);
				return ResponseResult.error("校验token错误");
			}
		}
		
		
		try {
			
			
			StringBuilder builder = new StringBuilder();
			builder.append("\n");
			builder.append("########## 请求开始 ############ \n");
			HttpServletRequest request = RequestUtils.getRequest();
			if (request != null) {
				builder.append("请求信息：\n");
				builder.append(request.getServletPath());
				builder.append("\n");
				builder.append(request.getServerPort());
				builder.append("\n");
				builder.append(request.getServerName());
				builder.append("\n");
				builder.append(request.getMethod());
				builder.append("\n");
				builder.append(request.getLocalAddr());
				builder.append("\n");
				builder.append(request.getRemoteAddr());
				builder.append("\n");
				builder.append(JSON.toJSONString(joinPoint.getArgs()));
				builder.append("\n");
			}
			
			if (checkToken != null) {
				builder.append("认证信息：\n");
				builder.append(checkToken.getTenant());
				builder.append("\n");
				builder.append(checkToken.getSystem());
				builder.append("\n");
			}
			
			
			// 执行方法
			Object object = joinPoint.proceed();
			
			builder.append("返回信息：\n");
			builder.append(JSON.toJSONString(object));
			builder.append("\n");
			
			builder.append("########## 请求结束 ############ \n");
			builder.append("\n");
			
			LOG.info("请求日志：{}", builder);
			
			return object ;
		} catch (Throwable e) {
			LOG.error("执行具体接口服务错误", e);
		}
		
		return ResponseResult.error("服务错误");
	}
	
	
	/**
	 * @description
	 *	判断不需要 认证TOKEN
	 * @author TianwYam
	 * @date 2021年5月20日上午9:31:20
	 * @param joinPoint
	 * @return
	 */
	public static boolean notAuth(ProceedingJoinPoint joinPoint) {
		
		// 整个请求controller上放行
		Object target = joinPoint.getTarget();
		if (target != null && target.getClass().isAnnotationPresent(NotAuth.class)) {
			LOG.info("控制器级别-请求都放行-不需要认证");
			return true ;
		}
		
		// 方法接口级别上放行
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method != null && method.isAnnotationPresent(NotAuth.class)) {
			LOG.info("请求级别-都放行-不需要认证");
			return true ;
		}
		
		return false ; 
	}
	
	
	
	
	
	
	
	
	
	

}
