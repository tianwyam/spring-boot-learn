package com.tianya.springboot.validation.aop;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tianya.springboot.common.entity.ResultCode;
import com.tianya.springboot.common.entity.ResultUtis;


/**
 * @description
 *	全局异常校验处理
 * @author TianwYam
 * @date 2020年12月25日下午7:22:47
 */
@RestControllerAdvice
public class GlobalValidExceptionHandler {
	
	
	/**
	 * @description
	 *	类绑定异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:14:27
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResultUtis validBeanException(ConstraintViolationException e) {
		
		String errorMsg = "" ;
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errorMsg = constraintViolation.getMessage();
			break;
		}
		
		return ResultUtis.error(6101, errorMsg);
	}
	
	
	/**
	 * @description
	 *	方法参数校验异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:16:02
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultUtis validMethodException(MethodArgumentNotValidException e) {
		
		String errorMsg = "" ;
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		for (ObjectError objectError : allErrors) {
			errorMsg = objectError.getDefaultMessage();
			break;
		}
		
		return ResultUtis.error(6102, errorMsg);
	}
	
	
	/**
	 * @description
	 *	参数绑定异常
	 * @author TianwYam
	 * @date 2020年12月28日下午5:17:11
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResultUtis validBindException(BindException e) {
		
		String errorMsg = "" ;
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		for (ObjectError objectError : allErrors) {
			errorMsg = objectError.getDefaultMessage();
			break;
		}
		
		return ResultUtis.error(6103, errorMsg);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResultUtis validException(Exception e) {
		return ResultUtis.error(ResultCode.SERVER_ERROR);
	}
	
	
	
	
	
	

}
