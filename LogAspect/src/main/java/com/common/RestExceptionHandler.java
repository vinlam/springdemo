package com.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@ControllerAdvice
public class RestExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public String runtimeExceptionHandler(RuntimeException ex) {
		logger.error("RuntimeException",ex);
		return ReturnFormat.retParam(1000, null);
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

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	@ResponseBody
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		logger.error("400-MissingServletRequestParameter缺少请求参数",ex);
		//ex.printStackTrace();
		return ReturnFormat.retParam(400, null);
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	public String request405(HttpRequestMethodNotSupportedException ex) {
		//System.out.println("405...请求方法不允许");
		logger.error("405-RequestMethodNotSupported请求方法不被允许数",ex);
		return ReturnFormat.retParam(405, null);
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