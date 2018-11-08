package ru.vladigeras.authorization.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.vladigeras.authorization.model.HttpResponseTemplate;

/**
 * @author vladi_geras on 08/11/2018
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	//	  не работает почему-то
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}


	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.REQUEST_TIMEOUT.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		var apiError = new HttpResponseTemplate(HttpStatus.REQUEST_TIMEOUT.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
	}
}
