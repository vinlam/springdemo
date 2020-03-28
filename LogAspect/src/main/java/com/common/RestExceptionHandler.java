package com.common;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException, 　　　　　　　　
 * NoSuchMethodException,IOException,IndexOutOfBoundsException 　　　　　　　　
 * 以及springmvc自定义异常等，如下： SpringMVC自定义异常对应的status code Exception HTTP Status Code
 * ConversionNotSupportedException 500 (Internal Server Error)
 * HttpMessageNotWritableException 500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException 415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException 406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException 405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException 404 (Not Found) TypeMismatchException
 * 400 (Bad Request) HttpMessageNotReadableException 400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 * 
 */
@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)//值越小，优先级越高
//@RestControllerAdvice // RestControllerAdvice返回不需要加@ResponseBody注解
public class RestExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public String runtimeExceptionHandler(RuntimeException ex) {
		logger.error("RuntimeException",ex);
		return ReturnFormat.retParam(1000, ex.getMessage());
	}

	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public String nullPointerExceptionHandler(NullPointerException ex) {
		//ex.printStackTrace();
		logger.error("NullPointerException",ex);
		return ReturnFormat.retParam(1001, null);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	@ResponseBody
	public String classCastExceptionHandler(ClassCastException ex) {
		//ex.printStackTrace();
		logger.error("ClassCastException",ex);
		return ReturnFormat.retParam(1002, null);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public String iOExceptionHandler(IOException ex) {
		//ex.printStackTrace();
		logger.error("IOExceptionHandler",ex);
		return ReturnFormat.retParam(1003, null);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	@ResponseBody
	public String noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		//ex.printStackTrace();
		logger.error("NoSuchMethodException",ex);
		return ReturnFormat.retParam(1004, null);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseBody
	public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		//ex.printStackTrace();
		logger.error("IndexOutOfBoundsException",ex);
		return ReturnFormat.retParam(1005, null);
	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	public String requestNotReadable(HttpMessageNotReadableException ex) {
		logger.error("400-HttpMessageNotReadableException",ex);
		//ex.printStackTrace();
		return ReturnFormat.retParam(400, null);
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	@ResponseBody
	public String requestTypeMismatch(TypeMismatchException ex) {
		logger.error("400-TypeMismatchException类型不符合",ex);
		//ex.printStackTrace();
		return ReturnFormat.retParam(400, null);
	}
	
	// 2029错误
	@ExceptionHandler({ BindException.class })
	@ResponseBody
	public String bindExceptionHandler(BindException ex) {
		logger.error("2010-bindExceptionHandler参数检验不通过",ex);
		StringBuilder sb = new StringBuilder();
		for(FieldError fieldError:ex.getBindingResult().getFieldErrors()) {
			if(fieldError.getField() != null) {
				sb.append(fieldError.getObjectName());
				sb.append(fieldError.getField());
				sb.append(fieldError.getDefaultMessage());
			}else {
				return ReturnFormat.retParam(2029, "Bind参数检验不通过");
			}
		}
		return ReturnFormat.retParam(2029, sb.toString());
	}

	// 2010错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	@ResponseBody
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		logger.error("400-MissingServletRequestParameter缺少请求参数",ex);
		//ex.printStackTrace();
		return ReturnFormat.retParam(2010, ex.getParameterName()+ex.getMessage());
	}
	//2029拦截非法参数错误
	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseBody
	public String requestConstainViolation(HttpServletRequest request,ConstraintViolationException cv) {
		StringBuffer sb = new StringBuffer();
		logger.error("2029-requestConstainViolation请求参数非法",cv);
		for(@SuppressWarnings("rawtypes") ConstraintViolation constraintViolation:cv.getConstraintViolations()) {
			if(constraintViolation.getMessage() != null) {
				sb.append(constraintViolation.getMessage());
			}else {
				return ReturnFormat.retParam(2029, null);
			}
		}
		//ex.printStackTrace();
		return ReturnFormat.retParam(2029, sb.toString());
		//throw cv;
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public String handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
		logger.error("400-MethodArgumentTypeMismatchException请求参数非法",ex);
	    String error = ex.getCause().getMessage()+ " should be of type " + ex.getLocalizedMessage();
	    return ReturnFormat.retParam(2029, error);
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public String handleMethodArgumentTypeMismatch(MethodArgumentNotValidException ex, WebRequest request) {
		logger.error("400-MethodArgumentNotValidException请求参数非法",ex);
		String error = ex.getCause().getMessage()+ " should be of type " + ex.getLocalizedMessage();
		return error;
	}

	// 405错误 
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	public String request405(HttpRequestMethodNotSupportedException ex) {
		//System.out.println("405...请求方法不允许");
		logger.error("405-RequestMethodNotSupported请求方法不被允许数",ex);
		return ReturnFormat.retParam(405, ex.getMessage());
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	@ResponseBody
	public String request406(HttpMediaTypeNotAcceptableException ex) {
		logger.error("406-MediaTypeNotAcceptable媒体类型不被接受",ex);
		return ReturnFormat.retParam(406, null);
	}
	
	// 415错误
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseBody
	public String request415(HttpMediaTypeNotSupportedException ex) {
		logger.error("415-MediaTypeNotSupported媒体类型不支持",ex);
		return ReturnFormat.retParam(415, null);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class,HttpMessageNotWritableException.class })
	@ResponseBody
	public String server500(RuntimeException ex) {
		logger.error("500-InternalServerError",ex);
		return ReturnFormat.retParam(500, null);
	}
}